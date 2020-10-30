package se.experis.academy.noticeboard.models.web;

public class CommentWeb {
    private Integer postId;
    private String description;

    public CommentWeb() {
    }

    public Integer getPostId() {
        return postId;
    }
    public String getDescription() {
        return description;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
