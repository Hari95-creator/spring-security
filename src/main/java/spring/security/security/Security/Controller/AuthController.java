package spring.security.security.Security.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import spring.security.security.Pojo.AuthRequest;
import spring.security.security.Utility.JwtUtil;

@RestController
@RequestMapping("/jwt")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value="/authenticate",method = RequestMethod.POST)
    public String generateToken(@RequestBody AuthRequest authRequest){

        //here we are avoiding the security filter chain in case of jwt token authentication
        // after that we directly pass the  username and password to authentication manager to generate usernamePasswordAuthentcationToken to authenticate
        // verifying through userdetails Service( Custom User Details )

        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));

        //if authentication is success then it will genrate jwt token
        return jwtUtil.generateJwtToken(authRequest.getUserName());
    }
}
