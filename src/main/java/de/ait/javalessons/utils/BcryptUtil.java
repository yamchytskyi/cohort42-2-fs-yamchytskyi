package de.ait.javalessons.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "adminpass";
        String encodetPassword = encoder.encode(password);
        System.out.println(password + "-->" + encodetPassword);
    }

    //userpass-->$2a$10$UZ1uo5EBZtZbexuzaIo.0.E1sM93abeE1l2XNAHt5vajfYQ62I/3O
    //adminpass-->$2a$10$mwLv0WUNcbyBArXq8dgQD.zsvZB0GpbvcQNi8CAeWs0fY7er3ATru
}
