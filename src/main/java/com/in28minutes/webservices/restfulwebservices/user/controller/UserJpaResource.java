package com.in28minutes.webservices.restfulwebservices.user.controller;

import com.in28minutes.webservices.restfulwebservices.user.entity.Post;
import com.in28minutes.webservices.restfulwebservices.user.entity.User;
import com.in28minutes.webservices.restfulwebservices.user.exception.UserNotFoundException;
import com.in28minutes.webservices.restfulwebservices.user.jpa.PostRepository;
import com.in28minutes.webservices.restfulwebservices.user.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {

    private UserRepository userRepository;

    private PostRepository postRepository;

    @Autowired
    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);

        EntityModel<User> entityModel = EntityModel.of(user.get());       // create an entity model from the existing user
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());      // create a link that maps to the '/users' endpoint
        entityModel.add(link.withRel("all-users"));     // inject the link with name 'all-users' into the entity model of the current user

        return entityModel;
    }

    // @Valid will apply the validations defined in our entity 'User' before bind and save it
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User  user) {
        User savedUser = userRepository.save(user);

        /**
         * it's a best practice when the service consumer is creating a resource,
         *      return to him the URI of his created resource.
         */

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()     // from the current request URI '/users'
                .path("/{id}")      // append to it this parameter '/{id}'
                .buildAndExpand(savedUser.getId())      // and replace it with this value 'savedUser.getId()'
                .toUri();

        /**
         * it's a best practice when the service consumer is creating a resource,
         *      return a status code of created 201 instead of ok 200.
         */

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        userRepository.deleteById(id);
    }

    /**
     *
     *
     * *************************************** RETRIEVE POSTS FOR USERS ********************************
     *
     */

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post  post) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        /**
         * it's a best practice when the service consumer is creating a resource,
         *      return to him the URI of his created resource.
         */

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()     // from the current request URI '/users'
                .path("/{id}")      // append to it this parameter '/{id}'
                .buildAndExpand(savedPost.getId())      // and replace it with this value 'savedUser.getId()'
                .toUri();

        /**
         * it's a best practice when the service consumer is creating a resource,
         *      return a status code of created 201 instead of ok 200.
         */
        return ResponseEntity.created(location).build();
    }
}
