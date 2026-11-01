package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;
import com.dinesh.LibraryManagementSystem.domain.PaymentGateway;
import com.dinesh.LibraryManagementSystem.domain.PaymentType;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.FineMapper;
import com.dinesh.LibraryManagementSystem.model.BookLoan;
import com.dinesh.LibraryManagementSystem.model.Fine;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.FineDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateFineRequest;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentInitiateRequest;
import com.dinesh.LibraryManagementSystem.payload.request.WaiveFineRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;
import com.dinesh.LibraryManagementSystem.repository.BookLoanRepository;
import com.dinesh.LibraryManagementSystem.repository.FineRepository;
import com.dinesh.LibraryManagementSystem.service.FineService;
import com.dinesh.LibraryManagementSystem.service.PaymentService;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

	private final BookLoanRepository bookLoanRepository;
	private final FineRepository fineRepository;
	private final FineMapper fineMapper;
	private final UserService userService;
	private final PaymentService paymentService;

	@Override
	public FineDTO createFine(CreateFineRequest createFineRequest) throws Exception {
		BookLoan bookLoan = bookLoanRepository.findById(createFineRequest.getBookLoanId())
				.orElseThrow(() -> new Exception("Book loan doesnot exist"));
		Fine fine = Fine.builder().bookLoan(bookLoan).user(bookLoan.getUser()).type(createFineRequest.getType())
				.amount(createFineRequest.getAmount()).status(FineStatus.PENDING).reason(createFineRequest.getReason())
				.notes(createFineRequest.getNote()).build();
		Fine savedFine = fineRepository.save(fine);
		return fineMapper.toDTO(savedFine);
	}

	@Override
	public PaymentInitiateResponse payFIne(Long fineId, String transactionId) throws Exception, UserException {
		Fine fine = fineRepository.findById(fineId).orElseThrow(() -> new Exception("Fine id doesnot exist"));
		if (fine.getStatus().equals(FineStatus.PAID)) {
			throw new Exception("Fine ALready paid");
		}
		if (fine.getStatus().equals(FineStatus.WAIVED)) {
			throw new Exception("Fine Waived");
		}
		User user = userService.getCurrentUser();
		PaymentInitiateRequest request = PaymentInitiateRequest.builder().userId(user.getId()).fineId(fine.getId())
				.paymentType(PaymentType.FINE).gateway(PaymentGateway.RAZORPAY).amount(fine.getAmount())
				.description("Library fine payment").build();
		return paymentService.initiatePayment(request);
	}

	@Override
	public void markFineAsPaid(Long fineId, Long amount, String transactionId) throws Exception {
		Fine fine = fineRepository.findById(fineId)
				.orElseThrow(() -> new Exception("Fine not found with fine id " + fineId));
		fine.applyPayment(amount);
		fine.setTransactionId(transactionId);
		fine.setStatus(FineStatus.PAID);
		fine.setUpdatedAt(LocalDateTime.now());
		fineRepository.save(fine);

	}

	@Override
	public FineDTO waiveFine(WaiveFineRequest waiveFineRequest) throws Exception, UserException {
		Fine fine = fineRepository.findById(waiveFineRequest.getFineId())
				.orElseThrow(() -> new Exception("Fine not found with id:" + waiveFineRequest.getFineId()));
		if (fine.getStatus() == FineStatus.WAIVED) {
			throw new Exception("Fine has already waived.");
		}

		if (fine.getStatus() == FineStatus.PAID) {
			throw new Exception("Fine has already paid and cannot be waived.");
		}

		User currentAdmin = userService.getCurrentUser();
		fine.waive(currentAdmin, waiveFineRequest.getReason());
		Fine savedFine = fineRepository.save(fine);
		return fineMapper.toDTO(savedFine);
	}

	@Override
	public List<FineDTO> getMyFines(FineStatus status, FineType type) throws UserException {
		User currentUser = userService.getCurrentUser();
		List<Fine> fines;

		if (status != null && type != null) {
			fines = fineRepository.findByUserId(currentUser.getId()).stream()
					.filter(f -> f.getStatus() == status && f.getType() == type).collect(Collectors.toList());
		} else if (status != null) {
			fines = fineRepository.findByUserId(currentUser.getId()).stream().filter(f -> f.getStatus() == status)
					.collect(Collectors.toList());
		} else if (type != null) {
			fines = fineRepository.findByUserIdAndType(currentUser.getId(), type);
		} else {
			fines = fineRepository.findByUserId(currentUser.getId());
		}
		return fines.stream().map(fineMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Fine> finePage = fineRepository.findAllWithFilters(userId, status, type, pageable);
		return convertToPageResponse(finePage);
	}

	private PageResponse<FineDTO> convertToPageResponse(Page<Fine> finePage) {
		List<FineDTO> fineDTOs = finePage.getContent().stream().map(fineMapper::toDTO).collect(Collectors.toList());
		return new PageResponse<>(fineDTOs, finePage.getNumber(), finePage.getSize(), finePage.getTotalElements(),
				finePage.getTotalPages(), finePage.isLast(), finePage.isFirst(), finePage.isEmpty());
	}

}
