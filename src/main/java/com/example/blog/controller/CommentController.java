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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService,
                             PostService postService,
                             UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        Post post = postService.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не знайдено"));

        User author = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(author);
        commentService.save(comment);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        Comment comment = commentService.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Коментар не знайдено"));

        boolean isCommentAuthor = comment.getAuthor().getEmail().equals(userDetails.getUsername());
        boolean isPostAuthor = comment.getPost().getAuthor().getEmail().equals(userDetails.getUsername());

        if (!isCommentAuthor && !isPostAuthor) {
            return "redirect:/posts/" + postId;
        }

        commentService.delete(commentId);
        redirectAttributes.addFlashAttribute("success", "Коментар видалено.");
        return "redirect:/posts/" + postId;
    }
}