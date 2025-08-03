package com.hotelsystem.controller;

import com.hotelsystem.dto.PaymentRequestDTO;
import com.hotelsystem.dto.PaymentResponseDTO;
import com.hotelsystem.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // create payment
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody @Valid PaymentRequestDTO requestDTO) {
        PaymentResponseDTO createdPayment = paymentService.createPayment(requestDTO);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    // get payment by id
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        PaymentResponseDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    // get all payments
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<PaymentResponseDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // Delete payment by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
