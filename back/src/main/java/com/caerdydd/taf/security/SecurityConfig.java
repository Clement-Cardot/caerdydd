package com.caerdydd.taf.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.services.UserService;


@Configuration
@EnableGlobalMethodSecurity(
        //securedEnabled = true,
        //jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig{

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authProvider());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // .authenticationProvider(authProvider())
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers("**/error").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                
                // .antMatchers("/api/student/**").hasAuthority("STUDENT_ROLE")
                // .antMatchers("/api/team_member/**").hasAuthority("TEAM_MEMBER_ROLE")
                // .antMatchers("/api/teaching_staff/**").hasAuthority("TEACHING_STAFF_ROLE")
                // .antMatchers("/api/option_leader/**").hasAuthority("OPTION_LEADER_ROLE"

                .anyRequest().permitAll()

                .and()
                .formLogin().loginPage("/api/auth").permitAll()
                .and().httpBasic()
                .and().build();
    }

    public Boolean checkCurrentUser(Integer id) throws CustomRuntimeException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();
        UserDTO user = userService.getUserById(id);

        return user.getLogin().equals(currentLogin);
    }

    public UserDTO getCurrentUser() throws CustomRuntimeException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();
        return userService.getUserByLogin(currentLogin);
    }
    
}
