package se.experis.academy.noticeboard.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name ="Users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String userName;

    @Column
    private String password;

    @OneToMany(mappedBy ="user",fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Post> posts;

    @OneToMany(mappedBy = "user",fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Comment> comments;

    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public List<Post> getPosts() { return posts; }
    public List<Comment> getComments() { return comments; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setPosts(List<Post> posts) { this.posts = posts; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
