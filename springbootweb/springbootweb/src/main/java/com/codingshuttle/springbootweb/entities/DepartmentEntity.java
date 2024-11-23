package com.codingshuttle.springbootweb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Boolean isActive;
    private LocalDate createdOn;
}
