package com.tn.DTO;

import com.tn.Entity.AccountRole;
import lombok.Data;

@Data
public class AccountDTO {
    private Integer id;

    private String fullName;

    private String username;

    private AccountRole role;

    private boolean isActive;

    private String departmentName;

}
