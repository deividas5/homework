package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAR_LOAN")
public class CarLoan {

    @GeneratedValue
    @Id
    Long id;

    @Column(name = "brand")
    String brand;

    @Column(name = "model")
    String model;

    @Column(name = "registrationNo")
    Long registrationNo;

    @Column(name = "totalPrice")
    Double totalPrice;

    @Column(name = "loanLength")
    Long loanLength;
}
