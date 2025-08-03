package spring.security.security.Security.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/secure")
public class SecurityController {


    @GetMapping(value="/data")
    @ResponseBody
    public String secureData(Model model){

        System.out.println("Reached Controller level");
        return "Hitting Secure";
    }

    @PreAuthorize("hasAuthority='READ_USER'")
    @GetMapping(value="/auth")
    @ResponseBody
    public String SecureAuth(Model model){

        System.out.println("Reached Controller level : Excluded Intercepting level");
        return "Authentication setup";
    }

    @GetMapping(value="/intercept/modify")
    @ResponseBody
    public String interceptingDataAndModify(@RequestParam String data, Model model){

        System.out.println("Reached Controller level : Data Modified at Intercepting Level : "+data);
        return "Hitting Secure";
    }
}
