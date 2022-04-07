package com.homework.homework.service;


import com.homework.homework.model.Applicant;
import com.homework.homework.model.CarLoan;

import java.util.HashMap;

public interface CreditApplicationService {
    Long calculateAffordability(Applicant applicant) throws Exception;

    HashMap<Long, Double> calculateQuote(CarLoan carLoan) throws Exception;

    String getDecision(Long affordabilityId, Long quoteId);
}
