package com.homework.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.homework.model.Applicant;
import com.homework.homework.model.CarLoan;
import com.homework.homework.service.CreditApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CreditApplicationControllerTest {

    @MockBean
    CreditApplicationService creditApplicationService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void calculateAffordability() throws Exception {
        when(creditApplicationService.calculateAffordability(any(Applicant.class))).thenReturn(1L);
        MvcResult result = mockMvc.perform(post("/api/calculateAffordability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new Applicant())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals("1", result.getResponse().getContentAsString());
    }

    @Test
    void calculateAffordabilityThrowsException() throws Exception {
        doThrow(new Exception("test")).when(creditApplicationService).calculateAffordability(any(Applicant.class));
        MvcResult result = mockMvc.perform(post("/api/calculateAffordability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new Applicant())))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", result.getResponse().getContentAsString());
    }

    @Test
    void calculateQuote() throws Exception {
        HashMap<Long, Double> map = new HashMap<>();
        map.put(1L,0.5);
        when(creditApplicationService.calculateQuote(any(CarLoan.class))).thenReturn(map);
        MvcResult result = mockMvc.perform(post("/api/calculateQuote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CarLoan())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals("{\"1\":0.5}", result.getResponse().getContentAsString());
    }

    @Test
    void calculateQuoteThrowsException() throws Exception {
        HashMap<Long, Double> map = new HashMap<>();
        map.put(1L,0.5);
        doThrow(new Exception("test")).when(creditApplicationService).calculateQuote(any(CarLoan.class));
        MvcResult result = mockMvc.perform(post("/api/calculateQuote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CarLoan())))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals("test", result.getResponse().getContentAsString());
    }

    @Test
    void getCreditDecision() throws Exception {
        HashMap<Long, Double> map = new HashMap<>();
        map.put(1L,0.5);
        when(creditApplicationService.getDecision(anyLong(), anyLong())).thenReturn("test");
        MvcResult result = mockMvc.perform(get("/api/getCreditDecision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("affordabilityId", "0")
                        .param("quoteId", "1"))
                .andExpect(status().isOk()).andReturn();
        assertEquals("test", result.getResponse().getContentAsString());
    }
}