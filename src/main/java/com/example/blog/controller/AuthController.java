package com.example.blog.controller;

import com.example.blog.dto.RegisterDto;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterDto registerDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.register(registerDto);
            redirectAttributes.addFlashAttribute("success", "Реєстрація успішна! Увійдіть в аккаунт.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.email", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email,
                                 RedirectAttributes redirectAttributes) {
        userService.sendResetToken(email);
        redirectAttributes.addFlashAttribute("success",
                "Якщо такий email зареєстрований, лист відправлено.");
        return "redirect:/forgot-password";
    }


    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {
        boolean success = userService.resetPassword(token, password);
        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Посилання недійсне або застаріле.");
            return "redirect:/forgot-password";
        }
        redirectAttributes.addFlashAttribute("success", "Пароль змінено. Увійдіть в аккаунт.");
        return "redirect:/login";
    }
}