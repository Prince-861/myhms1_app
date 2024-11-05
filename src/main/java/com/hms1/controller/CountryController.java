package com.hms1.controller;

import com.hms1.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//When we login, this gives us the JWT token, so now we have to take this token and we have to verify this at the backend for the subsequent request
//which means the url's that are supposed to be accessed only after we login(certain url's are supposed to accessed only after we login). Those url's should be
//submitted with the JWT token.

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    //http://localhost:8080/api/v1/country
    //The above particular url, when we want to access it should be accessed only when the token is sent with this url and the token is valid otherwise this url is not accessible.
    @PostMapping("/addCountry")
    public AppUser addCountry(@AuthenticationPrincipal AppUser user){//@Authentication AppUser user, after writing this line automatically the token that belongs to which user will come to this object.
        return user;
    }//This method when we supply the token, it is going to return me back which user has logged-in

//    In the postman, with this url(http://localhost:8080/api/v1/country) we are sending the JWT token ("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoic3RhbGxpbiIsImV4cCI6MTczMDUyODg1MCwiaXNzIjoicHJpbmNlIn0.-CgjxdIqNo7fY2MnYKVtyzhDn1bKfxwgwPmFsyeSTdg")
//    From the postman, when we click on send, this token will go to the backend. Now the backend code has to extract the token from this url and verify the token
//    and only when the token is valid, it should give the response back.

//    -------------So, the question is how should we write the code in the backend such that from this incoming url, a token extraction can happen.----------
//    --> Whenever we are making http REQUEST, that http request has a section called as Headers and in that Headers there are many parameters, so when we click on send
//    so inside the Headers we are sending the Authorization token.
//    Inside this url, against this authorization, there is a token that is getting saved and going with the url in the backend which we have to extract now(from that url).

//    Now, we have to analyze which is the incoming url that happens to have a token. Any url that comes with the token, the backend code should
//    automatically extract the token from the url, verify the token and then decide --> whether this url I can accept or reject
//    So, how do we do it???---------> In order to do it, we will go to the configuration file and we will create a filter class named as JWTFilter class

//    Filtering means if the incoming request coming with the token is valid then accept and if not valid then filter.
}
