package com.hms1;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class B {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("testing",BCrypt.gensalt(5)));
    }
}
