package com.jaiylonbabb.uprightcleaningservices.controller;

import com.jaiylonbabb.uprightcleaningservices.entity.BlogPost;
import com.jaiylonbabb.uprightcleaningservices.entity.User;
import com.jaiylonbabb.uprightcleaningservices.repository.UserRepository;
import com.jaiylonbabb.uprightcleaningservices.service.BlogPostService;
import com.jaiylonbabb.uprightcleaningservices.service.EmailService;
import com.jaiylonbabb.uprightcleaningservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("blog")
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "createBlog";
    }

    @PostMapping("/save")
    public String createBlogPost(@ModelAttribute BlogPost blogPost, @RequestParam("imageFile") MultipartFile imageFile) {
        System.out.println("Inside createBlogPost");
        // Handle file upload
        if (!imageFile.isEmpty()) {
            try {
                String imagePath = "src/main/resources/static/uploads" + imageFile.getOriginalFilename();
                File dest = new File(imagePath);
                imageFile.transferTo(dest);
                blogPost.setImageUrl(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BlogPost createdPost = blogPostService.createBlogPost(blogPost);

        // Send email notification to all users
        List<User> users = userRepository.findAll();
        List<String> userEmails = users.stream().map(User::getEmail).collect(Collectors.toList());
        String subject = "New Blog Post: " + createdPost.getTitle();
        String body = "Hello,\n\nA new blog post has been created:\n\nTitle: " + createdPost.getTitle() + "\n\n" + createdPost.getBody() + "\n\nBest Regards,\nYour Company";
        emailService.sendBlogPostNotification(userEmails, subject, body);

        return "redirect:/index";
    }
}
