package com.example.blog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ホームページに投稿一覧を表示
    @GetMapping("/")
    public String home(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "index"; // index.htmlを返す
    }

    // 投稿一覧を取得するAPI
    @GetMapping("/api")
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    // 投稿を作成するAPI
    @PostMapping("/api")
    public Post createPostApi(@RequestBody Post post) {
        return postService.save(post);
    }

    // IDで特定の投稿を取得するAPI
    @GetMapping("/api/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 投稿フォームを表示
    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "newPost"; // newPost.htmlを返す
    }

    // 投稿を保存
    @PostMapping("/new")
    public String createPost(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts/"; // 投稿後に一覧ページへリダイレクト
    }

    // 投稿の詳細ページを表示
    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        model.addAttribute("post", post);
        return "post_detail"; // post_detail.htmlを返す
    }
}
