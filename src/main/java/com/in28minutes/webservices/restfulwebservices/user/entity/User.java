package com.in28minutes.webservices.restfulwebservices.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_details")
public class User {

    /**
     * - Serialization:
     *      it's about to convert an object to a stream. (ex: JSON)
     * - Most popular JSON serialization in Java is Jackson.
     * - To customize the field name of the object in the JSON returned, we use '@JSONProperty'.
     *
     */

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Size(min = 2, message = "Name should have at least 2 characters.")
//    @JsonProperty("user-name")
    @Column(name = "user_name")
    private String name;

    @Past(message = "Birthdate should not be in the past.")
//    @JsonProperty("birth-date")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Post> posts;
}
