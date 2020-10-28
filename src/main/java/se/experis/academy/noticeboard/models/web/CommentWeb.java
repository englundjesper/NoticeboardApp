package se.experis.academy.noticeboard.models.web;

public class CommentWeb {
    private Integer userId;
    private Integer postId;
    private String description;

    public CommentWeb() {
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getDescription() {
        return description;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
