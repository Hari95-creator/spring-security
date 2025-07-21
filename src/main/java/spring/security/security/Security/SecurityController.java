package spring.security.security.Security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/secure")
public class SecurityController {


    @GetMapping(value="/data")
    @ResponseBody
    public String secureData(Model model){

        return "Hitting Secure";
    }
}
