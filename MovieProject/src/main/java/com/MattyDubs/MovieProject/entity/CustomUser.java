package com.MattyDubs.MovieProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * CustomUser entity, uses the webUser username to provide a corresponding customuser object.
 * Mapped with the movie class to allow users to have their own personal list of movies.
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

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private boolean enabled;

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

    public boolean isEnabled() {
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
}
