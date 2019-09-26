package id.co.bri.dce.blog.controller;

import id.co.bri.dce.blog.entity.User;
import id.co.bri.dce.blog.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.Root;
import javax.servlet.HttpConstraintElement;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Boolean.TRUE;

class RootController {
    public ModelAndView getPage(String s) {
        ModelAndView mv = new ModelAndView(s);
//        User user = (User) HttpConstraintElement
//        mv.addObject("user", user);
        return mv;
    }
}

@Controller
public class SignInController extends RootController {
    @Autowired
    UserDao userDao;

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
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

    @PostMapping("/signin-action")
    public String signInAction(@ModelAttribute User user, HttpSession session) {
        String email = user.getEmail();
        User user1 = userDao.findByEmail(email);
        String url="redirect:/signin";

        boolean password_verified = BCrypt.checkpw(user.getPassword(), user1.getPassword());

        if(user1.getEmail().equals(user.getEmail())
            && password_verified==true) {
            user1.setLogin(TRUE);
            userDao.save(user1);
            session.setAttribute("user", user1);
            url = "redirect:/homepage";
        }

        return url;
    }

    @GetMapping("/settings")
    public ModelAndView settingPassword(HttpSession session) {
        ModelAndView mv = getPage("reset_password");
        User user = (User) session.getAttribute("user");
        mv.addObject("user", user);
        return mv;
    }

    @PostMapping("/change_password")
    public String changePassword(HttpSession session, @ModelAttribute("user") User userForm) {
        User user = (User) session.getAttribute("user");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(hashedPassword);
        userDao.save(user);

        return "redirect:/homepage";
    }

}
