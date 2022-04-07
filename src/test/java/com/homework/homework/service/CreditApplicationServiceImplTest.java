package com.homework.homework.service;

import com.homework.homework.model.Applicant;
import com.homework.homework.model.CalculationsResult;
import com.homework.homework.model.CarLoan;
import com.homework.homework.model.MaritalStatus;
import com.homework.homework.repository.ApplicantRepository;
import com.homework.homework.repository.CalculationsResultRepository;
import com.homework.homework.repository.CarLoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceImplTest {

    @InjectMocks
    private CreditApplicationServiceImpl creditApplicationServiceImpl;

    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private CalculationsResultRepository calculationsResultRepository;

    @Mock
    private CarLoanRepository carLoanRepository;

    @Test
    void calculateAffordability() throws Exception {
        when(calculationsResultRepository.save(any(CalculationsResult.class))).thenReturn(new CalculationsResult(0L, 0.0));
        creditApplicationServiceImpl.calculateAffordability(new Applicant(0L, MaritalStatus.DIVORCED, 1L, 1L, 1L));
        verify(applicantRepository, times(1)).save(any(Applicant.class));
        verify(calculationsResultRepository, times(1)).save(any(CalculationsResult.class));
    }

    @Test
    void calculateQuote() throws Exception {
        CarLoan carLoan = new CarLoan(1L, "testBrand", "testModel", 0L, 5.0, 12L);
        when(calculationsResultRepository.save(any(CalculationsResult.class))).thenReturn(new CalculationsResult(0L, 5.0));
        when(carLoanRepository.save(any(CarLoan.class))).thenReturn(carLoan);
        when(calculationsResultRepository.loadBankMargin()).thenReturn(1.0);
        creditApplicationServiceImpl.calculateQuote(carLoan);
        verify(calculationsResultRepository, times(1)).loadBankMargin();
        verify(calculationsResultRepository, times(1)).save(any(CalculationsResult.class));
    }

    @Test
    void getDecision() {
        when(calculationsResultRepository.findById(anyLong())).thenReturn(Optional.of(new CalculationsResult(0L,0.0)));
        assertEquals("NO",creditApplicationServiceImpl.getDecision(0L,0L));
        verify(calculationsResultRepository,times(2)).findById(anyLong());

    }

    @Test
    void maritalCoefficient() {
        assertEquals(1, creditApplicationServiceImpl.maritalCoefficient(new Applicant(0L, MaritalStatus.SINGLE, 0L, 0L, 0L)));
        assertEquals(0.7, creditApplicationServiceImpl.maritalCoefficient(new Applicant(0L, MaritalStatus.MARRIED, 0L, 0L, 0L)));
    }
}