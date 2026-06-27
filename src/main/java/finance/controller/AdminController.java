package finance.controller;
import finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping(value = "/admin/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userRepository.findAll());

        return "admin";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied(){
        return "access-denied";
    }
}
