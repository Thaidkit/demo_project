package com.tn.Service;

import com.tn.Entity.Account;
import com.tn.Repository.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @Override
    public boolean save(Account account){
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean delete(int id){
        accountRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        if (account == null){
            throw new UsernameNotFoundException("Not find username");
        }
        return new User(username, account.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + account.getRole()));
    }
}
