package com.MattyDubs.MovieProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * CustomUser entity, represents a user and their relationships with movies, posts, replies.
 * UserDetails will hold an instance of a CustomUser upon authentication success.
 */
@Entity
@Table(name = "customusers")
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @NotEmpty
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @JsonIgnore
    @NotNull
    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;

    @Column(name = "verification_token")
    private String verificationToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserMovies> movies;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reply> replies;

    public CustomUser() {

    }

    public CustomUser(String username) {
        this.username = username;
    }

    public CustomUser(String username, String password, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<UserMovies> getMovies() {
        return movies;
    }

    public void setMovies(List<UserMovies> movies) {
        this.movies = movies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addReply(Reply reply) {
        if (replies == null) {
            replies = new ArrayList<>();
        }
        replies.add(reply);
    }

    public void addPost(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        post.setUser(this);
        posts.add(post);
    }

    public String getTokenUrl() {
        return String.format("Welcome to the movie application, %s!\n Please click" +
                "the link below to verify your email: \n" +
                "http://%s:8080/register/verify?token=%s", this.username, "localhost", this.verificationToken);
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
