package com.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/postLogin")
    public String sendOTP(@RequestParam("email") String email, Model model) {
        userService.generateAndStoreOTP(email);

        String otp = userService.getStoredOTP(email);

        String subject = "Your OTP for Authentication";
        String message = "Your OTP is: " + otp;

        emailService.sendOtpEmail(email, subject, message);

        model.addAttribute("email", email);

        return "verify-otp";
    }

    @PostMapping("/verify")
    public String verifyOTP(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
        String storedOTP = userService.getStoredOTP(email);

        if (storedOTP != null && storedOTP.equals(otp)) {
            userService.removeOTP(email);
            return "login-success";
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "verify-otp";
        }
    }
}
