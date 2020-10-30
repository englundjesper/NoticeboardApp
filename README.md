# NoticeboardApp

This project is created by [Emil Oja](https://github.com/xtrmil), [Aman Zadran](https://github.com/zadama) and [Johnny Hoang](https://github.com/flaakan).

Link to [Heroku](https://noticeboard2000.herokuapp.com/).

## Information
This project is a noticeboard application where you can see notices.  
To post and comment and edit, you will need to create an account and be logged in.  
As a logged in user you can post a notice, edit your own notices and comment on notices.

### Requirements
The requirements to be able to run this project:
* Java 15
* PosgreSQL
* Redis

## How to run 
* Clone the project, `git clone https://github.com/flaakan/NoticeboardApp.git`
* Start up redis.
* Open the project in your IDE.
* Configure `applications.properties` to connect to your local redis and posgreSQL database.
* Build the project.
* Run the project.

## Web page
The web page has several endpoints:
* `/` : This is the home page. It shows all the notices in a list.
* `/login`: The login page.
* `/register`: The register page.
* `/post/{postId}`: Shows a specific post based on the postId.
