package com.tn.Configure;

import com.tn.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {

    @Autowired
    private AccountService accountService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        UserDetails admin = User
//                .withUsername("thai1")
//                .password(passwordEncoder.encode("123"))
//                .build();
//        return new InMemoryUserDetailsManager(admin);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();

        httpSecurity.authorizeRequests().requestMatchers("/").permitAll();
        httpSecurity.authorizeRequests().requestMatchers("/home").permitAll();
        httpSecurity.authorizeRequests().requestMatchers("/reset_password").permitAll();
        httpSecurity.authorizeRequests().requestMatchers("/change-password/save").permitAll();
        httpSecurity.authorizeRequests().requestMatchers("/change-password/**").permitAll();

        httpSecurity.authorizeRequests().requestMatchers("account/add").permitAll();
        httpSecurity.authorizeRequests().requestMatchers(HttpMethod.POST,"account/save").permitAll();
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.formLogin();

        return httpSecurity.build();
    }
}
