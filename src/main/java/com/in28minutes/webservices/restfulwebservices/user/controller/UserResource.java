package com.in28minutes.webservices.restfulwebservices.user.controller;

import com.in28minutes.webservices.restfulwebservices.user.entity.User;
import com.in28minutes.webservices.restfulwebservices.user.exception.UserNotFoundException;
import com.in28minutes.webservices.restfulwebservices.user.service.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    private UserDaoService userDaoService;

    @Autowired
    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {

        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {

        User user = userDaoService.findOne(id);

        if (user == null) throw new UserNotFoundException("id: " + id);

        EntityModel<User> entityModel = EntityModel.of(user);       // create an entity model from the existing user
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());      // create a link that maps to the '/users' endpoint
        entityModel.add(link.withRel("all-users"));     // inject the link with name 'all-users' into the entity model of the current user

        return entityModel;
    }

    // @Valid will apply the validations defined in our entity 'User' before bind and save it
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User  user) {
        User savedUser = userDaoService.save(user);

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
    public String deleteUser(@PathVariable int id) {

        User user = userDaoService.findOne(id);
        boolean isDeleted;

        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        } else {
            isDeleted = userDaoService.deleteById(id);
        }

        return "the user has been deleted? " + isDeleted;
    }
}
