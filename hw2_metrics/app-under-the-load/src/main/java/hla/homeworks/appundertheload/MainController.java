package hla.homeworks.appundertheload;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
@AllArgsConstructor
public class MainController {

    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam String name, @RequestParam String email) {
        UserEntity n = new UserEntity();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @PostMapping(path = "/add2")
    public @ResponseBody
    UserEntity addNewUser2(@RequestBody UserEntity userEntity) {
        UserEntity saved = userRepository.save(userEntity);
        return saved;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}