package id.co.bri.dce.blog.controller;

import id.co.bri.dce.blog.entity.User;
import id.co.bri.dce.blog.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignInController {
    @Autowired
    UserDao userDao;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signin")
    public String signIn(Model model) {
        model.addAttribute("user", new User());
        return "sign_in";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "sign_up";
    }

    @PostMapping("/signup-action")
    public String signUpAction(@ModelAttribute User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.save(user);
        return "redirect:/signin";
    }

    @GetMapping("/blog_post")
    public String blogPost() {
        return "blog_post";
    }

    @PostMapping("/signin-action")
    public String signInAction(@ModelAttribute User user) {
        String email = user.getEmail();
        User user1 = userDao.findByEmail(email);
        String url="";

        boolean password_verified = BCrypt.checkpw(user.getPassword(), user1.getPassword());

        if(user1.getEmail().equals(user.getEmail())
            && password_verified==true) {
                url = "redirect:/blog_post";
        } else {
            url = "redirect:/homepage";
        }

        return url;

    }

    @GetMapping("/setting_password")
    public String settingPassword(Model model) {
        model.addAttribute("user", new User());
        return "change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(@ModelAttribute User user) {
        user = userDao.findByEmail(user.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.save(user);

        return "redirect:/homepage";
    }

}
