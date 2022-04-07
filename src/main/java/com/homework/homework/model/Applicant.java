package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "APPLICANT")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {

    @GeneratedValue
    @Id
    Long id;

    @Column(name = "marital_status")
    MaritalStatus maritalStatus;

    @Column(name = "no_of_adults")
    Long noOfAdults;

    @Column(name = "no_of_children")
    Long noOfChildren;

    @Column(name = "income_after_tax")
    Long incomeAfterTax;
}
