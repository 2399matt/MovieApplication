package com.MattyDubs.MovieProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotNull
    @Size(min = 1, max = 90, message = "title cannot be empty or exceed 90 characters.")
    private String title;

    @Column(name = "content")
    @NotNull
    @Size(min = 1, max = 2000, message = "Content of post cannot be empty, and cannot exceed 2000 characters.")
    private String content;

    @Column(name = "upvotes")
    private Integer upvotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Reply> replies;

    public Post() {

    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.upvotes = 0;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    //TODO Synchronization, not thread safe right now.-------------------------------------
    public void addVote() {
        this.upvotes++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public void addReply(Reply reply) {
        if (replies == null) {
            replies = new ArrayList<>();
        }
        reply.setPost(this);
        replies.add(reply);
    }
}
