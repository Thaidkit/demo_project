package com.tn.Service;

import com.tn.Entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AccountService extends UserDetailsService {
    List<Account> getAll();

    boolean save(Account account);

    boolean delete(int id);

    Account getById(int id);

    List<Account> search(String keyword);
}
