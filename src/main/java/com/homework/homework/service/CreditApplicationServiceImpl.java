package com.homework.homework.service;

import com.homework.homework.model.Applicant;
import com.homework.homework.model.CalculationsResult;
import com.homework.homework.model.CarLoan;
import com.homework.homework.repository.ApplicantRepository;
import com.homework.homework.repository.CalculationsResultRepository;
import com.homework.homework.repository.CarLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {

    private final ApplicantRepository applicantRepository;
    private final CalculationsResultRepository calculationsResultRepository;
    private final CarLoanRepository carLoanRepository;

    @Autowired
    public CreditApplicationServiceImpl(ApplicantRepository applicantRepository, CalculationsResultRepository calculationsResultRepository, CarLoanRepository carLoanRepository) {
        this.applicantRepository = applicantRepository;
        this.calculationsResultRepository = calculationsResultRepository;
        this.carLoanRepository = carLoanRepository;
    }

    @Override
    public Long calculateAffordability(Applicant applicant) throws Exception {
        applicantRepository.save(applicant);
        if (applicant.getNoOfAdults() > 2) {
            throw new Exception("You entered too much adults");
        }
        return calculationsResultRepository.save(new CalculationsResult(null, applicant.getIncomeAfterTax()
                - (applicant.getNoOfChildren() * 400)
                - (applicant.getNoOfAdults() * 600)
                - maritalCoefficient(applicant))).getId();
    }

    @Override
    public HashMap<Long, Double> calculateQuote(CarLoan carLoan) throws Exception {
        HashMap<Long, Double> result = new HashMap<>();
        Double bankMargin = calculationsResultRepository.loadBankMargin();
        carLoanRepository.save(carLoan);
        if(carLoan.getLoanLength() <= 0){
            throw new Exception("Loan length must be > 0");
        }
        CalculationsResult calculationsResult = calculationsResultRepository.save(new CalculationsResult(null,
                (carLoan.getTotalPrice() / carLoan.getLoanLength()) * (1 + bankMargin)));
        result.put(calculationsResult.getId(), calculationsResult.getResult());
        return result;
    }

    @Override
    public String getDecision(Long affordabilityId, Long quoteId) {
        return calculationsResultRepository.findById(affordabilityId).get().getResult() > calculationsResultRepository.findById(quoteId).get().getResult() ? "YES" : "NO";
    }

    Double maritalCoefficient(Applicant applicant) {
        switch (applicant.getMaritalStatus()) {
            case MARRIED:
                return 0.7;
            case DIVORCED:
                return applicant.getNoOfAdults() == 2 ? 0.8 : 1.0;
            default:
                return 1.0;
        }
    }
}
