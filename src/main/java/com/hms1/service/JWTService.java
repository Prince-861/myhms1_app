package com.hms1.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;



@Service
public class JWTService {
//    @Value annotation will automatically go to the properties file and it will search for this key name and whatever key value is there it will automatically fetch that value and initialize the below variable.
    @Value("${jwt.algorithm.key}") //algorithmKey is signature here
    private String algorithmKey; //why we are not writing like this private String algorithmKey=fdjfkdjfkljd-fdfkdklkd-fjdkfjdkjf; because when we write in the application.properties file then our job becomes easier rather than initializing here,because  it will make code lengthier if we write here then.

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;
//    This method is very useful when we want to automatically run this method when our project starts.
//    @PostConstruct annotation helps us to start this method automatically when the project starts.
    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expiryTime);
        algorithm = Algorithm.HMAC256(algorithmKey);//One who completely wants to decode this token should have this key and without this key the decoding of the token is not allowed.
//      That's why when we are generating the token itself, we are applying this key and generating it.
//      This algorithm variable consists of two parts 1) which Algorithm we are using and  2) with this algorithm, what is the signature??
    }
    //Now we will have to generate the token.
    //Computer Engineer is Unemployed
    public String generateToken(String username){
//        This token we have to return when the login is successful.
       return  JWT.create()
                .withClaim("name",username)//for which user we are actually creating the token
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))//System.currentTimeMillis() will convert our current time in millisecond and in that we are adding the expiryTime(which tells us the duration of the validity of this token).
                .withIssuer(issuer)
                .sign(algorithm);
    }
//    This method will take a token and from that token it will extract the username. The username is called as claim here.
//    But before extracting the username, we will have to verify the token.
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT
                                .require(algorithm) //to the token, we will apply the algorithm, because we will use same algorithm to encode and same algorithm to decode it.
                                .withIssuer(issuer) //after decoding, we will check here the issuer, so the issuer should match with the issuer whatever has been kept in the properties file.
                                .build()
                                .verify(token);//here along with the token, signature(secret key of the properties file) is also being verified with this and only when the incoming token signature and the signature in the properties file is verified, the token is decoded. //then this line will verify the token, and upon verification if everything is correct, then this line will decrypt the token

        return decodedJWT.getClaim("name").asString();

    }


}
