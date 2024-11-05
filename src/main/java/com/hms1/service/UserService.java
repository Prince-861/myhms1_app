package com.hms1.service;

import com.hms1.entity.AppUser;
import com.hms1.payload.LoginDto;
import com.hms1.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    //    This method will verify the username and password and it will return true or false
    public  String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());//it will go to the database and it can get(fetch) the user object based on the username.
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();//If optional object is present then we are converting that optional object into the entity object.//we have got appUser but we don't know it's password, so how we will check now??
            // since in the database, the password is encrypted and whatever password is in dto is not encrypted, so the comparison is with original password entered(in the JSON format) and with the encrypted password in the database
           if(BCrypt.checkpw(dto.getPassword(),appUser.getPassword())){
               //generate token
               String token = jwtService.generateToken(appUser.getUsername());
               return token;
           }
        }else{
            return null;
        }
        return null;
    }
}
