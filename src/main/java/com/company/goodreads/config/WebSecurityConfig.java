package com.company.goodreads.config;

import com.company.goodreads.service.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //spring securityni aktivlesdirir.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // bu classin subclasini yaradiriq
    private MyUserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(MyUserDetailsService userDetailsService,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // WEbSecurityConfigureAdapter classindan gelir bu method
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder); // bu configure-a bir UserDetailService birde passvordencoder gonderirik
    }

    protected void configure(HttpSecurity security) throws Exception {
        security.
                authorizeRequests()
                .antMatchers("/books/**","/users/login","/users/registration").permitAll()//books url-e her kes muraciet ede biler. ROle-dan asili olmayaraq
                .antMatchers("\"/buckets/**\", \"/orders/**\", \"/cards/**\"").hasAuthority("USER") // USER rolu olanlar gore biler ancaq
                .antMatchers("/admin/**").hasAuthority("ADMIN")  // Ancaq ADMIN userler gore biler
                .anyRequest().authenticated().and() // yuxaridakalara esasen tenzimle deyirik springsecuritye
                .formLogin() // login hissesinin nece bas vermesi burdan baslayir.
                .loginPage("/users/login") // Spring security bura gelen sorgu esasinda userin login ve shifresini yoxluyub sisteme buraxir
                .defaultSuccessUrl("/books", true) // Login olannan sonra hansi sehife achillsin
                .usernameParameter("username") //Html-de userin login paramteri hansi adda olacaq -->login.htmlde yazmisam
                .passwordParameter("password")  //Html-de userin password paramteri hansi adda olacaq
                .and()
                .logout()// logout nece bash versin
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))// logout hansi url-e sorgu gonderende bash versin
                .logoutSuccessUrl("/users/login") // logout olannan sonra hansi sehife achilsin
                .and().exceptionHandling().accessDeniedPage("/users/access-denied"); // role a uygun olmayan sorgu gelse bu sehifeye yonlendir
    }

    public void configure(WebSecurity security) { //spring securitye deyirik ki hansi hisseleri seciruty yoxlanisdan kecirme
        security.ignoring()
                .antMatchers("/static/**", "/images/**");
    }

}
