package com.tn.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table
// tao constructor khong tham so
@NoArgsConstructor
// tao constructor tham so
@AllArgsConstructor
// tao 1 constructor voi tham so tuy chon thi + @NonNull
@RequiredArgsConstructor
public class Account extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false)
    private String fullName;

    @NonNull
    @Column(nullable = false)
    private String username;

    @NonNull
    @Column(nullable = false)
    private String password;

    // enumtype.string   db: admin, manager, user
    // enumtype.ordinal : 0, 1, 2
    @NonNull
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
