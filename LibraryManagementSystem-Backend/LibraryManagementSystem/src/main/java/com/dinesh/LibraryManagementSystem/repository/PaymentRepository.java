package com.dinesh.LibraryManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.LibraryManagementSystem.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
