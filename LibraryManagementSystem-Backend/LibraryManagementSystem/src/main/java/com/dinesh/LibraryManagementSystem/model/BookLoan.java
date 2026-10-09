package com.dinesh.LibraryManagementSystem.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.domain.BookLoanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookLoan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JoinColumn(nullable = false)
	@ManyToOne
	private User user;
	
	@JoinColumn(nullable = false)
	@ManyToOne
	private Book book;
	
	private BookLoanType type;
	
	@Enumerated
	@Column(nullable = false,length = 20)
	private BookLoanStatus status;
	
	@Column(nullable = false)
	private LocalDate checkoutDate;
	
	@Column(nullable = false)
	private LocalDate returnDate;
	
	@Column(nullable = false)
	private Integer renewalCount=0;
	
	@Column(nullable = false)
	private Integer maxRenewal=2;
	
	@Column(nullable = false)
	private Boolean isOverdue=false;
	
	@Column(nullable = false)
	private Integer overdueDays=0;
	
	@Column(nullable = false,updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
