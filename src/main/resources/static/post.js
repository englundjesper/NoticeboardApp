$(window).on('load', function () {

    let location = window.location.href;
    let postID = location.split("post/")[1];

    $.ajax({
        url: "/api/v1/auth/status",
        type: 'GET',
        contentType: "application/json",
        dataType: 'json',
        success: function (res) {

            let loginDiv = `<div id="sign-in-div" class="post-replies-sign-in">
                                   <div>
                                   <h4>You must be signed in to reply.</h4>
                                   <a class="post-replies-login-btn" href=${"/login"}>Login</a>
                                   </div>
                                   `;

            let commentDiv = `<div id="textarea-div" class="post-add-reply">
                        <textarea id="reply-content" rows="3" placeholder="Write a reply...">

                        </textarea>

                        <button id="target" class="post-replies-login-btn" href=${"/post"}>Post</button>
                        </div>
                                    `


            if (res == true) {


                $("#amount-replies").after(commentDiv);

                $("#target").click(function () {

                    let location = window.location.href;
                    let postID = location.split("post/")[1];

                    $.ajax({
                        url: "/api/v1/comment/create",
                        type: 'POST',
                        contentType: "application/json",
                        dataType: 'json',
                        data: JSON.stringify(
                            {
                                "postId": postID,
                                "description": $("#reply-content").val()
                            }
                        ),
                        success: function (res) {

                            console.log(res)

                            $("#replies").prepend(`<div class="alert alert-success" role="alert">
  Reply was added!
</div>`);
                            removeAlert().then(() => {
                                window.location.href = "/post/" + postID;
                            })

                        }
                    });


                });

            } else {
                $("#amount-replies").after(loginDiv);

            }
        }
    });


    $.ajax({
        url: "/api/v1/post/" + postID,
        type: 'GET',
        contentType: "application/json",
        dataType: 'json',
        success: function (res) {

            let post = res.data;
            let comments = res.data.comments;

            console.log(post)

            $('#amount-replies').text(comments.length + " Replies");

            $("#post-contain").append(
                `
            <div class="post-card">
            <div class="post-card-header">
                <h2>${post.title}</h2>
                <p>by: <span>${post.user.userName} </span>| date: <span>${post.createdAt}</span></span>
            </div>

            <div class="post-card-body">
                <p>${post.description}</p>
            </div>
            
            <div class="edit-delete-icons">
    
            </div>
                        `
            );

            let areCommentsDiplayed = false;

            $.ajax({
                url: "/api/v1/auth/userId",
                type: 'GET',
                contentType: "application/json",
                dataType: 'json',
                success: function (loggedInUserId) {

                    displayComments(comments, loggedInUserId);

                }

            })


            checkIfUserIsLoggedIn();


        }
    });

    function handleEditDescription() {

        $(".save-comment").click(function () {
            // Holds the product ID of the clicked element
            let commentId = $(this).prev().attr("data-comment-id");

            let parentDiv = $(this).parent();

            $.ajax({
                url: "/api/v1/comment/" + commentId,
                type: 'PATCH',
                contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify(
                    {
                        "postId": postID,
                        "description": $(this).prev().text()
                    }
                ),
                success: function (res) {
                    $(parentDiv).prepend(`<div class="alert alert-success" role="alert">
  Reply was updated!
</div>`);

                }
            });

        });
    }

    function displayComments(comments, loggedInUserId) {

        for (let i = 0; i < comments.length; i++) {
            let commentDescription = "";
            if (loggedInUserId && comments[i].user.id == loggedInUserId) {
                commentDescription = `
<div class="comment-edit-container">
<p class="comment-edit"
        data-comment-id="${comments[i].id}" contenteditable="true">${comments[i].description}</p>
        <i  class="save-comment fas fa-save fa-2x"></i>
</div>`;

            } else {
                commentDescription = `<p >${comments[i].description}</p>`;
            }

            $(".post-replies-list").append(
                `<div class="post-replies-reply">
                    <h3 style="color: #0b6e4f">${comments[i].user.userName}</h3>

                    ${commentDescription}

                </div>
                        `
            );
        }

        handleEditDescription();

    }


    function checkIfUserIsLoggedIn() {

        $.ajax({
            url: "/api/v1/auth/status",
            type: 'GET',
            contentType: "application/json",
            dataType: 'json',
            success: function (res) {

                if (res == true) {
                    $(`
          <i id="remove-post" class="fas fa-trash-alt"></i>
          <i id="edit-post" class="fas fa-edit"></i>
        `).appendTo(".edit-delete-icons");
                }


                $("#remove-post").click(removePostListener);
                $("#edit-post").click(editPostListener);

            }
        });


    }


    function removePostListener() {

        $.ajax({
            url: "/api/v1/post/" + postID,
            type: 'DELETE',
            contentType: "application/json",
            dataType: 'json',
            success: function (res) {
                $(".main-container").prepend(`<div class="alert alert-success" role="alert">
  Post was deleted!
</div>`);
                removeAlert().then(() => {
                    window.location.href = "/";
                })


            },
            error: function (err) {
                console.log(err.status + " - " + err.responseJSON.message);

                $(".main-container").prepend(`<div class="alert alert-danger" role="alert">
  ${err.responseJSON.message}
</div>`);
                removeAlert();
            }
        });
    }

    function editPostListener() {

        console.log("HÄÄÄr")

        $.ajax({
            url: "/api/v1/post/" + postID + "/session",
            type: 'GET',
            contentType: "application/json",
            dataType: 'json',
            success: function (res) {
                console.log("HÄÄR ÄGAREN!")
                window.location.href = '/post/' + postID + "/edit";

            },
            error: function (err) {

                //visa error meddelande, typ "not your
                window.location.href = '/';

            }
        });


    }


    function removeAlert() {

        return new Promise(function (resolve) {

            setTimeout(function () {
                $('.alert').remove();
                resolve();
            }, 2000);

        });

    }

})