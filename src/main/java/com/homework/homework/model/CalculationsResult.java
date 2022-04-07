package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CALCULATIONS_RESULT")
public class CalculationsResult {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "result")
    Double result;
}
