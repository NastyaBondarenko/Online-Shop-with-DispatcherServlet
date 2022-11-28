package com.bondarenko.onlineshop.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.bondarenko.onlineshop.web.security.ApplicationUserPermission.USER_GET;
import static com.bondarenko.onlineshop.web.security.ApplicationUserRole.ADMIN;
import static com.bondarenko.onlineshop.web.security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/", "/products","/js/*").permitAll()
////                .antMatchers("/products/cart/**").hasRole(USER.name())
//                .antMatchers("/products/**").hasRole(ADMIN.name())
//                .antMatchers("/cart","product_cart").hasRole(USER.name())
//                .antMatchers(HttpMethod.POST,"/cart/{id}").hasAuthority(USER_GET.getPermission())
//                .antMatchers(HttpMethod.DELETE,"/cart/delete").hasAuthority(USER_GET.getPermission())
//                .antMatchers(HttpMethod.GET,"/cart").hasAuthority(USER_GET.getPermission())
//                .antMatchers(HttpMethod.GET,"/cart/**").hasAnyRole(USER.name())
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin();
//                .defaultSuccessUrl("/cart",true);
//                .loginPage("/login").permitAll();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass"))
                .roles(USER.name())
//                .authorities(USER.getGrantedAuthorities())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
//                .authorities(ADMIN.getGrantedAuthorities())

                .roles(ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
