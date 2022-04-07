package com.homework.homework.controller;

import com.homework.homework.model.Applicant;
import com.homework.homework.model.CarLoan;
import com.homework.homework.service.CreditApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @Autowired
    public CreditApplicationController(CreditApplicationService creditApplicationService) {
        this.creditApplicationService = creditApplicationService;
    }

    @PostMapping("/calculateAffordability")
    public ResponseEntity<?> calculateAffordability(@RequestBody Applicant applicant) {
        try {
            return ResponseEntity.ok(creditApplicationService.calculateAffordability(applicant));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/calculateQuote")
    public ResponseEntity<?> calculateQuote(@RequestBody CarLoan carLoan) {
        try {
            return ResponseEntity.ok(creditApplicationService.calculateQuote(carLoan));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getCreditDecision")
    public String getCreditDecision(@RequestParam("affordabilityId") Long affordabilityId, @RequestParam("quoteId") Long quoteId) {
        return creditApplicationService.getDecision(affordabilityId, quoteId);
    }

}
