package com.MattyDubs.MovieProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlikes")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Likes() {

    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public CustomUser getUser() {
        return this.user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

}
