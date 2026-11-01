
package com.dinesh.LibraryManagementSystem.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.Fine;
import com.dinesh.LibraryManagementSystem.payload.dto.FineDTO;

@Component
public class FineMapper {

    public FineDTO toDTO(Fine fine) {
        if (fine == null) {
            return null;
        }

        FineDTO dto = new FineDTO();

        dto.setId(fine.getId());

        if (fine.getBookLoan() != null) {
            dto.setBookLoanId(fine.getBookLoan().getId());

            if (fine.getBookLoan().getBook() != null) {
                dto.setBookTitle(fine.getBookLoan().getBook().getTitle());
                dto.setBookIsbn(fine.getBookLoan().getBook().getIsbn());
            }
        }

        if (fine.getUser() != null) {
            dto.setUserId(fine.getUser().getId());
            dto.setUserName(fine.getUser().getFullName());
            dto.setUserEmail(fine.getUser().getEmail());
        }

        dto.setType(fine.getType());
        dto.setAmount(fine.getAmount());
        dto.setStatus(fine.getStatus());
        dto.setReason(fine.getReason());
        dto.setNote(fine.getNotes());

        if (fine.getWaivedBy() != null) {
            dto.setWaivedByUserId(fine.getWaivedBy().getId());
            dto.setWaivedByUserName(fine.getWaivedBy().getFullName());
        }
        dto.setWaivedAt(fine.getWaivedAt());
        dto.setWaiverReason(fine.getWaiverReason());
        dto.setPaidAt(fine.getPaidAt());

        if (fine.getProcessedId() != null) {
            dto.setProcessedByUserId(fine.getProcessedId().getId());
            dto.setProcessedByUserName(fine.getProcessedId().getFullName());
        }

        dto.setTransactionId(fine.getTransactionId());
        dto.setCreatedAt(fine.getCreatedAt());
        dto.setUpdatedAt(fine.getUpdatedAt());

        return dto;
    }

    public List<FineDTO> toDTOList(List<Fine> fines) {
        if (fines == null) {
            return null;
        }

        return fines.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}