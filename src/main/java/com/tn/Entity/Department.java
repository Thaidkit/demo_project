package com.tn.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table
@NoArgsConstructor
@RequiredArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String departmentName;

    @NonNull
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Account> accounts;

}
