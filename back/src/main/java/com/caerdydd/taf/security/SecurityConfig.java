package com.caerdydd.taf.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.services.UserService;


@Configuration
@EnableGlobalMethodSecurity(
        //securedEnabled = true,
        //jsr250Enabled = true,
        prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select login, password, enabled from user where login=?")
                .authoritiesByUsernameQuery("select login, role from user where login=?");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers("**/error").permitAll()
                
                // .antMatchers("/api/student/**").hasAuthority("student")
                // .antMatchers("/api/team_member/**").hasAuthority("team_member")
                // .antMatchers("/api/teaching_staff/**").hasAuthority("teaching_staff")
                // .antMatchers("/api/option_leader/**").hasAuthority("option_leader")

                // TO ENABLE AUTHENTICATION : UNCOMMENT THE LINES ABOVE AND COMMENT THE LINE BELOW

                .anyRequest().permitAll()

                .and()
                .formLogin()
                .permitAll()
                .and().build();
    }

    public Boolean checkCurrentUser(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();
        UserEntity user = userService.getUserById(id);

        return user.getLogin().equals(currentLogin);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogin = authentication.getName();
        return userService.getUserByLogin(currentLogin);
    }
    
}
