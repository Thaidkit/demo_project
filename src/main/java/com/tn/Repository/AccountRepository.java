package com.tn.Repository;

import com.tn.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String username);

    //@Query(value = " from account where data like concat ('%', :data%, '%')")
    List<Account> findByFullNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String fullName, String username);
}
