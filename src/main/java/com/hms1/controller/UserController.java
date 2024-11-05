package com.hms1.controller;

import com.hms1.entity.AppUser;
import com.hms1.payload.LoginDto;
import com.hms1.payload.TokenDto;
import com.hms1.repository.AppUserRepository;
import com.hms1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
//    here we have to build the Service layer and the Dto class.
//    Actually this appUserRepository should come in the service layer.
    private AppUserRepository appUserRepository;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }

    @PostMapping("/signup")//Here we have the url for signup for the user but we will have to create more signup for admin, property owner for different-different roles.
    public ResponseEntity<?> createUser(@RequestBody AppUser user){//so we will send the data to the user and it will be saved into the database, but before saving the data into the database we will have to check whether the data is in the Database or not.
//        Here the return type should be a dto
//        We will have to check, whether we have the user with this email id or we have email id with this username
//        If the email id and the username exist then we cannot create a user and we cannot sign-up.
        Optional<AppUser> opUser = appUserRepository.findByUsername(user.getUsername());
        if(opUser.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        when all the above conditions failed then we will move to this condition.
        String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));//here before saving the data to the database, we are actually encrypting the password.
        user.setPassword(encryptedPassword);//here we are saving the encrypted password to the database and SpringBoot knows how to take the password from the database and decrypt it internally. Till here we are able to send the data to the database..... but but but we are getting the password in response which should not happen.
        user.setRole("ROLE_USER");//This is the standard way of defining the role in the database. First give "ROLE_" and then the USER OR ADMIN like that.
                                  //We do not create url for signing-up for the admin we simply hardcode admin part in the database, otherwise anybody can sign-up as the admin.
        AppUser savedUser = appUserRepository.save(user);//here password is also getting saved as raw password in the database which is a threat for the customer or the Bank Account Holders
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);

//     <dependency>
//      <groupId>org.springframework.boot</groupId>
//      <artifactId>spring-boot-starter-security</artifactId>
//    </dependency>

//    After adding above dependency, our all urls are by-default getting secured. So, the basic level of authentication can be seen here by securing all the urls.
//    After adding Spring Security in dependency, we will be able to see the encrypted password in the console
//    and when we will send the request from the postman then we will see the error as (401 Unauthorized--> It tells that this url is safe and secured and we cannot access this url.)

//    Everytime we restart the application, we get new encrypted password in the console.
    }

//    In Browser, we use @GetMapping annotation
    @GetMapping("/message")
    public String getMessage(){
        return "Hello";
    }
//    --> From here, create config package so that we can make our urls more secured

    //Below method is for verifying the username and password
    //SEARCH---- Otp based login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){//@RequestBody will copy the data from the Json to Dto
        //Above data(dto) needs to be verified,for that we will create a service layer and we will create a java class UserService inside that.
        String token = userService.verifyLogin(dto);//whatever we see in the dto, they are plain text and the original data are already stored in the database.
        if(token != null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);//UNAUTHORIZED means certain features we can't use, FORBIDDEN means we can't use at all.
        }
    }

    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createPropertyOwnerUser(@RequestBody AppUser user){
//        Here the return type should be a dto
//        We will have to check, whether we have the user with this email id or we have email id with this username
//        If the email id and the username exist then we cannot create a user and we cannot sign-up.
        Optional<AppUser> opUser = appUserRepository.findByUsername(user.getUsername());
        if(opUser.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        when all the above conditions failed then we will move to this condition.
        String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));//here before saving the data to the database, we are actually encrypting the password.
        user.setPassword(encryptedPassword);//here we are saving the encrypted password to the database and SpringBoot knows how to take the password from the database and decrypt it internally. Till here we are able to send the data to the database..... but but but we are getting the password in response which should not happen.
        user.setRole("ROLE_OWNER");//This is the standard way of defining the role in the database. First give "ROLE_" and then the USER OR ADMIN like that.
        //We do not create url for signing-up for the admin we simply hardcode admin part in the database, otherwise anybody can sign-up as the admin.
        AppUser savedUser = appUserRepository.save(user);//here password is also getting saved as raw password in the database which is a threat for the customer or the Bank Account Holders
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);

    }
//    Advantages of JWT TOKEN:--

//    JWT is all about stateless communication.
//    It acts as an alternate way of login.
//    We can set the session time out of the token.


}
