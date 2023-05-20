package com.in28minutes.webservices.restfulwebservices.user.jpa;

import com.in28minutes.webservices.restfulwebservices.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
