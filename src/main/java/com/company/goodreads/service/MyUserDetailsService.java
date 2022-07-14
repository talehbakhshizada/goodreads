package com.company.goodreads.service;

import com.company.goodreads.dao.entity.RolesEntity;
import com.company.goodreads.dao.entity.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService { // spring securitynin interfaceidir biz bunun subclasini yaradib oz istediyimiz kimi istifade edeciyik , mes useri username e gore find edib rollarini teyin edirik
    private UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //userin usernamei ve rollari bu methodda teyin olunur
        UsersEntity user = userService.getUserByLogin(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RolesEntity role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole())); // her bir role-u GrantedAuthority obyekti kimi add edirik liste. SimpleGrantedAuthority  GrantedAuthority obyekti yaradir ve GrantedAuthoritynin subclassidir.
        }

        User springUser = new User(
                user.getLogin(), user.getPassword(), user.getActive(), true, // istifade etmediklerime true yazdim
                true, true, authorities
        );
        return springUser;
    }


}
