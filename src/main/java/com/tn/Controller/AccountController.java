package com.tn.Controller;

import com.tn.DTO.AccountDTO;
import com.tn.DTO.DepartmentDTO;
import com.tn.Entity.Account;
import com.tn.Entity.AccountRole;
import com.tn.Entity.Department;
import com.tn.Repository.AccountRepository;
import com.tn.Service.AccountService;
import com.tn.Service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

// @RestController: chi dung cho Restful API
// @Controller: dung cho giao dien Thymeleaf
@Controller
@Slf4j //annotation ghi tat ca console vao log
public class AccountController {

    private AccountService accountService;
    private DepartmentService departmentService;

    public AccountController(AccountService accountService,
                             DepartmentService departmentService){
        this.accountService = accountService;
        this.departmentService = departmentService;
    }


    @GetMapping("account")
    public String getAll(Model model){

        //vi du ve log - app.og
        log.info("Get all account");
        log.error("account id to delete not found");

        List<Account> accounts = accountService.getAll();

        List<AccountDTO> accountDTOS = new ArrayList<>();

        accounts.forEach(account -> {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(account.getId());
            accountDTO.setFullName(account.getFullName());
            accountDTO.setUsername(account.getUsername());
            accountDTO.setRole(account.getRole());
            accountDTO.setActive(account.isActive());

            if(account.getDepartment() != null){
                accountDTO.setDepartmentName(account.getDepartment().getDepartmentName());
            }

            accountDTOS.add(accountDTO);
        });
        model.addAttribute("accountDTOS", accountDTOS);
        return "account_list";
    }

    @GetMapping("account/add")
    public String add(Model model){
        List<Department> departments = departmentService.getAll();
        List< DepartmentDTO> departmentDTOS = new ArrayList<>();

        departments.forEach(department -> {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartmentName(department.getDepartmentName());
            departmentDTO.setDescription(department.getDescription());

            departmentDTOS.add(departmentDTO);
        });
        model.addAttribute("departmentDTOS", departmentDTOS);
        return "add_account";
    }

    @PostMapping("account/save")
    public String save(@RequestParam String fullName,
                       @RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String role,
                       @RequestParam Integer departmentId){
        System.out.println("username" + username);

        AccountRole accountRole = null;
        if (role.equals("2")){
            accountRole = AccountRole.USER;
        }
        if (role.equals("1")){
            accountRole = AccountRole.ADMIN;
        }

        password = new BCryptPasswordEncoder().encode(password);
        Account account = new Account(fullName, username, password, accountRole);

        if (departmentId != 0){
            Department department = departmentService.getById(departmentId);
            account.setDepartment(department);
        }
        accountService.save(account);

        return "redirect:/account";
    }

    @GetMapping("account/delete/{id}")
    public String delete(@PathVariable int id){
        accountService.delete(id);
        return "redirect:/account";
    }
}
