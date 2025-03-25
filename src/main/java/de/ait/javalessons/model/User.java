package de.ait.javalessons.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user entity in the system. This class is mapped to the "users" table in the database.
 * It stores information about a user, including their username, password, and assigned roles.
 * Designed for integration with JPA and uses Lombok annotations for boilerplate code reduction.
 *
 * Key Features:
 * - Each user has a unique identifier (id) generated automatically.
 * - Usernames are unique and cannot be null.
 * - Passwords cannot be null.
 * - A user can have multiple roles, which are stored as a collection and fetched eagerly.
 *
 * Note:
 * This class is designed to be used in conjunction with JPA repositories and custom user details services
 * for authentication and authorization purposes.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private Set<String> roles = new HashSet<>();


}