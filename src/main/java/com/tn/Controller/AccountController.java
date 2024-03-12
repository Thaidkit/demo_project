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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") //lay gia tri mail tu properties truyen vao object sender
    private String sender;


    public AccountController(AccountService accountService,
                             DepartmentService departmentService,
                             JavaMailSender javaMailSender){
        this.accountService = accountService;
        this.departmentService = departmentService;
        this.javaMailSender = javaMailSender;
    }

    private void sendEmail(String subject, String content) {
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo("sktt1thai2003@gmail.com");
            mailMessage.setSubject(subject);
            mailMessage.setText(content);

            // Sending the mail
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Send email fail");
        }
    }

    @GetMapping("")
    public String clientIndex(){
        return "client_index";
    }

    @GetMapping("/reset_password")
    public String resetPassword(){
        return "reset_password";
    }

    @PostMapping("reset_password")
    public String resetPassword(@RequestParam String username){
        sendEmail("Reset Password: ", "http://localhost:8080/change-password/" + username);
        return "client_index";
    }

    @GetMapping("change-password/{username}")
    public String changePassword(String username){
        return "change-password";
    }

    @GetMapping("account")
    public String getAll(Model model){

        //vi du ve log - app.log
        log.info("Get all account");
        log.error("account id to delete not found");

        //sendEmail();

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
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();

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

    @GetMapping("account/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Account account = accountService.getById(id);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setRole(account.getRole());
        accountDTO.setActive(account.isActive());

        if (account.getDepartment() != null) {
            accountDTO.setDepartmentName(account.getDepartment().getDepartmentName());
        };

        model.addAttribute("accountDTO", accountDTO);
        return "edit_account";
    }

    @PostMapping("account/update")
    public String update(@RequestParam Integer id,
                         @RequestParam String fullName,
                         @RequestParam String username,
                         @RequestParam String role) {

        Account account = accountService.getById(id);
        account.setFullName(fullName);
        account.setUsername(username);

        if (role.toString().equals("USER")){
            account.setRole(AccountRole.USER);
        }
        if (role.toString().equals("ADMIN")){
            account.setRole(AccountRole.ADMIN);
        }

        accountService.save(account);

        return "redirect:/account";
    }

    // Search
    @GetMapping("account/search_account")
    public String search(@RequestParam String keyword, Model model){

        List<Account> accounts;

        if (keyword != null && !keyword.isEmpty()) {
            accounts = accountService.search(keyword);

            List<AccountDTO> accountDTOS = new ArrayList<>();

            accounts.forEach(account -> {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setId(account.getId());
                accountDTO.setFullName(account.getFullName());
                accountDTO.setUsername(account.getUsername());
                accountDTO.setRole(account.getRole());
                accountDTO.setActive(account.isActive());

                if (account.getDepartment() != null) {
                    accountDTO.setDepartmentName(account.getDepartment().getDepartmentName());
                }

                accountDTOS.add(accountDTO);
            });
            model.addAttribute("accountDTOS", accountDTOS);
            //truyen gia tri tim kiem ra o input (view)
            // khi nhap gia tri tim kiem thi gia tri van hien thi, chu khong mat di
            model.addAttribute("keyword", keyword);
            return "account_list";
        } else {
            return "redirect:/account";
        }


    }
}
