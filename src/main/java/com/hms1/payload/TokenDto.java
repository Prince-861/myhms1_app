package com.hms1.payload;

import lombok.Getter;
import lombok.Setter;

//We can use @Data annotation as well to avoid writing @Setter and @Getter or we can explicitly write @Setter and @Getter
@Setter
@Getter

public class TokenDto {
//    When we login, we get the JWT token(like this: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoic3RhbGxpbiIsImV4cCI6MTczMDE5OTEwOSwiaXNzIjoicHJpbmNlIn0.rLMQT-Fw3Hlqiqt6sywCVxdI4HvBSbP1ciWwqbDHQY4) as response in the postman.
//    But we should never send the jwt token like this in the response in the postman.
//    whatever response, we give to the postman, we ensure that response is given as the JSON object.So how? --> For that we have created this payload:-TokenDto
//    So, we will take this token we will put that in the json object and return back
//    So,whatever information we give it back to the postman as a response, we have to always make a practice that we should
//    send it as a json object using ResponseEntity as a return type of the method.
    private String token;
    private String type;
}
