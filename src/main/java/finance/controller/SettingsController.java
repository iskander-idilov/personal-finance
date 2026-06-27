package finance.controller;

import finance.entity.User;
import finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class SettingsController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/settings")
    public String getSettings(){
        return "settings";
    }

    @PostMapping(value = "/settings/password")
    public String editPassword(Principal principal,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("confirmPassword") String confirmPassword){

        User user = userRepository.findByName(principal.getName()).orElseThrow();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/settings?passwordError=true";
        }

        if(newPassword.equals(confirmPassword)){
            user.setPassword(passwordEncoder.encode(confirmPassword));
        } else{
            return "redirect:/settings?passwordError=true";
        }

        userRepository.save(user);

        return "redirect:/settings?passwordSuccess=true";
    }

    @PostMapping(value = "/settings/email")
    public String editEmail(Principal principal,
                            @RequestParam("newEmail") String newEmail,
                            @RequestParam("password") String password){

        User user = userRepository.findByName(principal.getName()).orElseThrow();

        if (!passwordEncoder.matches(password, user.getPassword())){
            return "redirect:/settings?emailError=true";
        } else{
            user.setEmail(newEmail);
            userRepository.save(user);
            return "redirect:/settings?emailSuccess=true";
        }
    }
}
