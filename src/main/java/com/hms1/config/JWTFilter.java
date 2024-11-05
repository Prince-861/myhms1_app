package com.hms1.config;

import com.hms1.entity.AppUser;
import com.hms1.repository.AppUserRepository;
import com.hms1.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

//Filtering means if the incoming request coming with the token is valid then accept and if not valid then filter.
//Here, OncePerRequestFilter is an abstract class which has an incomplete method in it. So, we have to inherit that method and override it.
@Component //Here we have marked this class with the @Component so that we can do the dependency injection
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository userRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    //  This doFilterInternal method is designed in a way, that any incoming url with the token comes, it will automatically come to this method.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        The incoming url with the token will automatically come to this request object. This is all happening internally behind the scene.
//        If any url comes with the token, that url will automatically go to the request object.So, the incoming urls has headers and in headers there are so
//        many key-value pairs, one of the key-value pair is authorization and token.Now, from that header, we have to get the value that is stored inside the
//        authorization key.
        String token = request.getHeader("Authorization");//from the incoming url, it will go to the Header and from the header it will read the Authorization and the Authorization has the token.
        System.out.println(token);//here when we do not get the token and debug will not stop at this line then we will have to add one line in the configuration file telling that whenever any incoming http request
        //comes with the JWT token, we have to tell that we want our JWTFilter filter to run first and not the built-in filters(because by-default built-in filters runs)
        //So,we want our JWTFilter runs first and then the built-n Authorization filters.
        if(token != null && token.startsWith("Bearer ")) {
            String tokenVal = token.substring(8, token.length() - 1);//from this token we have to get the username. So,for username in JWTService we will create one method called as getUsername()
            String username = jwtService.getUsername(tokenVal);
//            System.out.println(username);//Now, whatever username we are getting here, we don't want to print here.
            //Token has the username, so verify this username in the database whether this username exists in the database or not and if exist then we will ask spring
            //security to give permission to process this url.
            Optional<AppUser> opUsername = userRepository.findByUsername(username);//what this line will do--> it will go to the database now based on whatever username we got from the token, it will again verify that username in the database.
            if(opUsername.isPresent()) {//opUsername will avoid NullPointerException here.
//                Now further code will tell the spring security that this is a valid token
//                please process the request and send the response back.

                AppUser appUser = opUsername.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser, null, Collections.singleton(new SimpleGrantedAuthority(appUser.getRole()))); //if in the authorities part we simply give the appUser.getRole() then it will not work, So here we use Collections(group of object )
                                                                                                                                                           //but we want to create a Collections such that it has only one object in that.So, we will use Collections.singleton() --> this will restrict my Collection to having only one object in that.
                                                                                                                                                           //So this Collection is a collection of one single object of the type SimpleGrantedAuthority and in that we are storing the role of the user
                                                                                                                                                           //Now, this authentication token will have user as well as the role details

                //the above class has three parameters:- principal, credentials, authorities
                //principal: it tells us actually who has logged-in in the application.
                //credentials: //here we do not need credentials because our token are being protected by the secret key, we are not using credentials to protect that, where we usually pass the user's password, here it is not needed by the way.
                //authorities: it is used to specify the authorities or roles granted to the principal. It's null value means that the authorities are not specified at this point.

                authenticationToken.setDetails(new WebAuthenticationDetails(request)); //Based on the user and role details, this request can be processed or not, it will check with the help of the configuration files.

                //the authenticationToken object consists of the user details and the url details(because the incoming request comes to the request object) and now that information we are passing to the SecurityContextHolder(an area which decides for this user I have to open the filter to let the processing further and grant him access.)
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//                Now, we have to add the roles part in our project
//                that means if we login as the property owner or admin then we can access this url
//                but if we login as the user then this url should not be accessible.
//                So, we will go to the database and we will drop the table (because we want one more column that is "role" in our database).
            }
        }
//        After the JWTFilter runs, it will then further run the internal filter methods.There are lots of internal filter methods spring security has to run which is not our job.
//        So, after we invoke our filter method, we have to add this line at the end of the method

        filterChain.doFilter(request, response);//by adding this line we are telling that the url with the token send it here and the url without token please do not send it here.

//        doFilterInternal() method should run after login, because once we login, a token is generated, the subsequent urls that we are now
//        sending the token with that it should go to doFilterInternal() method, but there are certain urls that should not go here
//        like the login url, the login url should not come to doFilterInternal() method, the login url basically now supposed to happened without
//        coming to this method. We should come to this method after we have logged in, generated token , we are taking that token and the subsequent urls when we are sending to the backend
//        that should come to this method.
//        So, what was happening unfortunately, our login url was also coming here only and hence it was returning null value when we were sending the request from the login api from the postman.
//        So, by doing this (filterChain.doFilter(request, response);) at the end, it very clearly understands, which url has to come to this method and which url should not come to this method.
    }
}
