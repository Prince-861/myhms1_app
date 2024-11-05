package com.hms1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

//Configuration files are very special files.Now, what this @Configuration annotation does is :- Moment we start the application, this particular class automatically gets loaded into the springboot.
//We need not call this class, we need not call the methods of this class.We need not call the methods that we create in this class.
//The specialty of the config files are these are the files that will run first in our project like properties file is config file only.
//Earlier we do not have concept of properties file in the spring but in the SpringBoot we have this concept so that we can reduce the load of working.
//So, when we start the project, properties file has to load first.If the properties file does not load, then the configuration details, SpringBoot does not know
//and further code will not run. Same way, since we have used @Configuration annotation, so when project will start then this class will load first(and when this loads
//we will do all the configuration here which we will make spring security to understand, that look here -->this is the configuration that I am doing it-->and
//follow this annotation and allow this url to be open and this to be secured.

//SO,CONFIGURATION FILES ARE FILES --> WHEN THE PROJECTS ARE STARTED, IT LOADS FIRST EVEN BEFORE ANY OF OUR CLASSES RUNS(LIKE @CONTROLLER, @SERVICE etc.)
//Spring Security is developed by SpringBoot, but it is a sub-framework of SpringBoot.So,this does not come as a SpringBoot framework.This is sub-library of SpringBoot.when we will use @Bean annotation in our further classes then there will be so much confusion(we will see further).

@Configuration
public class SecurityConfig {
//    Below method is the one where we will configure which url can be accessed by whom?
//    Since we want the below method to return an object with the configuration details.That's why we will use @Bean annotation.
//    @Bean works only in the Configuration class.
//    The below method is going to return an http object with the configuration details present in it and this configuration details is what SpringBoot has to study and analyze the incoming method and automatically take the required action.
    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        SecurityFilterChain is the built-in class that comes from the security
//        By mentioning HttpSecurity http, whatever incoming http request is there, that will be captured in the http object.
//        So, the job of the http object is to take the incoming http request and based on the http request it has to take the required action.

//        whenever we are making any request, there are chances that my application can be attacked by some hacker.
//        The first attack he can do basically is using csrf(Cross-Site Request Forgery) attack. It is an attack that tricks the user into executing unwanted actions on a different website where they're authenticated.To prevent
//        this, Spring Security provides built-in CSRF protection.But here instead of enabling it, we are disabling it because
//        we are in development phase right now.

//        whenever we are developing any api, from where this api will be accessed?--> Obviously, it will be accessed from some frontend framework.
//        whatever api we developed, this api has to be integrated with the frontend frameworks like angular,or react or any other framework.
//        This cors() policy if we are enabling that then we can tell that only an api from a particular domain can be accessed and apart from that client(angular or postman is client for us)this cannot be accessed by any other client or some specific IP Address.
//        csrf() method gives compile-time exception, that's why in the above method we have used throws Exception.
        //h(cd)2
        http.csrf().disable().cors().disable();//This will work with the java version 8 and above and 2.x version of SpringBoot and with the 3.0.0 version(we will have to change this version in pom.xml file).
//        401(Unauthorized access) is the status code where we can't access without credentials.
//        now we want to configure here telling that keep all the urls open and don't secure any url

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);//Adding this line will help us to run the doFilterInternal() method. When an incoming request comes, this line helps us to run the jwtFilter first and then the built-in filter.

        //haap
        http.authorizeHttpRequests().anyRequest().permitAll();//this line tells that all the urls is open right now. //this line of code will configure our spring security telling that for now keep all the urls open and don't secure any url for time being, later I will take the decision,which url to be kept open and which to be secured.

        //now we want to configure in such a way that only those urls should be accessible who has a valid token and a valid role with it.

//        http.authorizeHttpRequests().
//                requestMatchers("/api/v1/users/login","/api/v1/users/signup","/api/v1/users/signup-property-owner")
//                .permitAll()
//                .requestMatchers("/api/v1/country/addCountry").hasAnyRole("OWNER","ADMIN")//if we use .hasRole() then we can give only one value inside like OWNER or ADMIN etc. but if we use .hasAnyRole() then we can give multiple value like Admin and Owner as well. //since we want only property owner can access the add country url.
//                .anyRequest().authenticated();//apart from login and signup any url from our project is authenticated.

//        Since we are in development phase right now so it will become very tedious work for us to mentioning which url is open and which is authorized for now. So, we are commenting the above line.
        return http.build();//this line manufactures an http object with the above configuration details and since we are using @Bean annotation so this will hand over this object to the Spring IOC and now Spring IOC will manage that object.
    }
}
