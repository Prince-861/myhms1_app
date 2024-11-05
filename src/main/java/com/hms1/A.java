package com.hms1;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class A {
    public static void main(String[] args) {
//        There are two ways of encryption:-

//        Below we are creating the object of BCryptPasswordEncoder() and we are storing it in the reference variable of the PasswordEncoder interface.
//        Here, class upcasting is happening where child object address is getting stored in the parent class reference variable.

//        Method-1(This is basic technique of encryption, which is not that strong).
//        PasswordEncoder en = new BCryptPasswordEncoder();//Here PasswordEncoder is an interface and BCryptPasswordEncoder is a class.
//        System.out.println(en.encode("testing"));//here we can give the raw password

//        Method-2(If we want to make encryption slightly stronger but not very strong, because more we encrypt--> the more time it will take to decrypt).

        String enPwd = BCrypt.hashpw("testing", BCrypt.gensalt(5));//when we will call BCrypt.gensalt(5), it indicates that we want to use 2^5=32 rounds for hashing.
        System.out.println(enPwd);

    }
}
