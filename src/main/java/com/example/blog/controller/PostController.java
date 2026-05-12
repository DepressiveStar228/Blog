package com.example.blog.controller;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    public PostController(PostService postService,
                          CommentService commentService,
                          UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model,
                       @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("posts", postService.findAll());

        if (userDetails != null) {
            userService.findByEmail(userDetails.getUsername())
                    .ifPresent(u -> model.addAttribute("currentUsername", u.getUsername()));
        }

        return "posts/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пост не знайдено"));
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.findByPost(post));
        model.addAttribute("newComment", new Comment());
        return "posts/detail";
    }

    @GetMapping("/new")
    public String newPostPage(Model model) {
        model.addAttribute("post", new Post());
        return "posts/form";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute Post post,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        User author = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));
        post.setAuthor(author);
        postService.save(post);
        redirectAttributes.addFlashAttribute("success", "Пост створено!");
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пост не знайдено"));

        if (!post.getAuthor().getEmail().equals(userDetails.getUsername())) {
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "posts/form";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute Post updatedPost,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пост не знайдено"));

        if (!post.getAuthor().getEmail().equals(userDetails.getUsername())) {
            return "redirect:/posts";
        }

        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        postService.save(post);
        redirectAttributes.addFlashAttribute("success", "Пост оновлено!");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пост не знайдено"));

        if (!post.getAuthor().getEmail().equals(userDetails.getUsername())) {
            return "redirect:/posts";
        }

        postService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Пост видалено.");
        return "redirect:/posts";
    }
}