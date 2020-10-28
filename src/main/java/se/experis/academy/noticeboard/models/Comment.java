package se.experis.academy.noticeboard.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String description;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    public Integer getId() { return id; }
    public String getDescription() { return description; }
    public Post getPost() { return post; }
    public User getUser() { return user; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDescription(String description) { this.description = description; }
    public void setPost(Post post) { this.post = post; }
    public void setUser(User user) { this.user = user; }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
