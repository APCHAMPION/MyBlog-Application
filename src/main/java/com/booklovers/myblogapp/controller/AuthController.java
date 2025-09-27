package com.booklovers.myblogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booklovers.myblogapp.model.User;
import com.booklovers.myblogapp.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class AuthController {

	private final UserService userService;

	/**
	 * @param userService
	 */
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/register")
    public String showRegistrationForm(Model model) {
        
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Processes the submission of the registration form.
     *
     * @param user The User object populated with data from the form.
     * @param redirectAttributes Used to add flash attributes for the redirect.
     * @return A redirect instruction to the login page.
     */
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
            // Add a success message that will be displayed on the login page after redirect.
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (IllegalStateException e) {
            // If the username already exists, add an error message and return to the form.
            redirectAttributes.addFlashAttribute("registrationError", e.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
   
	
	
}
