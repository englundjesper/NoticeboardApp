package se.experis.academy.noticeboard.models.web;

public class PostWeb {
    private String description,title;

    public PostWeb(){
    }

    public String getDescription() { return description; }
    public String getTitle() { return title; }

    public void setDescription(String description) { this.description = description; }
    public void setTitle(String title) { this.title = title; }
}
