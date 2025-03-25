package com.prepnest.Controller;

import com.prepnest.Entity.Questions;
import com.prepnest.Entity.User;
import com.prepnest.Repository.UserRepository;
import com.prepnest.Service.QuestionsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionsService questionsService;

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration"; // registration.html
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // login.html
    }

    // Handle login
    @PostMapping("/loginuser")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            // If login is successful, store userId in session
            session.setAttribute("userId", existingUser.getId());
            return "redirect:/home"; // Redirect to home after successful login
        } else {
            // If login fails, return to login page with an error
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Handle registration
    @PostMapping("/registeruser")
    public String registerUser(@ModelAttribute User user, HttpSession session) {
        boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) {
            session.setAttribute("message", "User already exists");
            return "registration"; // Stay on the registration page
        } else {
            userRepository.save(user);
            session.setAttribute("message", "User registered successfully");
            return "redirect:/home"; // Redirect to home after registration
        }
    }

    // Show home page
    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Questions> questions = questionsService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "home"; // home.html
    }

    // Show index page
    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // index.html
    }

    // Logout user and invalidate session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate session on logout
        return "redirect:/login"; // Redirect to login after logout
    }

    // Display all users (for testing purposes)
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList"; // userList.html
    }
}
