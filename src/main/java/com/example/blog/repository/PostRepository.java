package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 必要に応じてカスタムメソッドを追加できます
}
