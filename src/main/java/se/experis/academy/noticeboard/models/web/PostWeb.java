package se.experis.academy.noticeboard.models.web;

public class PostWeb {
    private Integer userId;
    private String description,title;
   public PostWeb(){

   }

    public Integer getUserId() { return userId; }
    public String getDescription() { return description; }
    public String getTitle() { return title; }

    public void setUserId(Integer userId) { this.userId = userId; }
    public void setDescription(String description) { this.description = description; }
    public void setTitle(String title) { this.title = title; }
}
