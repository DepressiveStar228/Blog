package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

    public ProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/{username}")
    public String publicProfile(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        model.addAttribute("profileUser", user);
        model.addAttribute("posts", postService.findByAuthor(user));
        return "profile/view";
    }

    @GetMapping("/edit")
    public String editPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        model.addAttribute("user", user);
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String username,
                                @RequestParam String bio,
                                RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        userService.findByUsername(username).ifPresent(existing -> {
            if (!existing.getId().equals(user.getId())) {
                throw new IllegalArgumentException("Username вже зайнятий");
            }
        });

        user.setUsername(username);
        user.setBio(bio);
        userService.save(user);

        redirectAttributes.addFlashAttribute("success", "Профіль оновлено!");
        return "redirect:/profile/" + username;
    }

    @GetMapping("/me")
    public String myProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));
        return "redirect:/profile/" + user.getUsername();
    }
}