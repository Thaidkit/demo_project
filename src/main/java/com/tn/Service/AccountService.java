package com.tn.Service;

import com.tn.Entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {
    List<Account> getAll();

    boolean save(Account account);

    boolean delete(int id);


}
