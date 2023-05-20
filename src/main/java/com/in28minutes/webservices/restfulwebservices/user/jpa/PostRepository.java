package com.in28minutes.webservices.restfulwebservices.user.jpa;

import com.in28minutes.webservices.restfulwebservices.user.entity.Post;
import com.in28minutes.webservices.restfulwebservices.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
