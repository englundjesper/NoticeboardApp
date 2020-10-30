import * as requests from "/js/requests.js"
import * as util from "/js/util.js"

let location = window.location.href;
let postID = location.split("post/")[1];

const renderComment = async () => {
    const request = await requests.isUserOnline();
    const response = await request.json();

    if (response == true) {
        let commentDiv = `
            <div id="textarea-div" class="post-add-reply"><textarea id="reply-content" rows="3" placeholder="Write a reply..."></textarea>
                        <button id="target" class="post-replies-login-btn" href="/post">Post</button>
            </div>
                                    `
        $("#amount-replies").after(commentDiv);

        $("#target").click(async function () {


            const createdComment = await requests.createComment(JSON.stringify(
                {
                    "postId": postID,
                    "description": $("#reply-content").val()
                }
            ));

            if (createdComment.status == requests.statusCodes.CREATED) {
                $("#replies").prepend(`<div class="alert alert-success" role="alert">
                    Reply was added!
            </div>`);

                await util.removeAlert();
                util.changeRoute("/post/" + postID);

            }

        });
    } else {
        let loginDiv = `
                <div id="sign-in-div" class="post-replies-sign-in">
                            <h4>You must be signed in to reply.</h4>
                            <a class="post-replies-login-btn" href=${"/login"}>Login</a>
                </div>
                                   `;
        $("#amount-replies").after(loginDiv);

    }
}

async function editPostListener(){
    const request = await requests.checkIfLoggedInUserIsPostOwner(postID);

    if(request.status == requests.statusCodes.OK){
        util.changeRoute("/post/"+postID+"/edit");
    } else {
        util.changeRoute("/post/"+postID);
    }
}

async function removePostListener (){

    const request = await requests.deletePost(postID);
    const response = await request.json();

    if(request.status == requests.statusCodes.OK){
        $(".main-container").prepend(`
        <div class="alert alert-success" role="alert">
            Post was deleted!
        </div>`);

        await util.removeAlert();
        util.changeRoute("/");
    } else {

        $(".main-container").prepend(`
        <div class="alert alert-danger" role="alert">
            ${response.message}
        </div>`);
        await util.removeAlert();
    }

}

async function checkIfUserIsLoggedIn() {
    const request = await requests.isUserOnline();
    const response = await request.json();

    if (response == true) {
        $(`
          <i id="remove-post" class="fas fa-trash-alt"></i>
          <i id="edit-post" class="fas fa-edit"></i>
        `).appendTo(".edit-delete-icons");

    }
    $("#remove-post").click(removePostListener);
    $("#edit-post").click(editPostListener);
}

async function handleEditDescription() {

    $(".save-comment").click(async function () {
        let commentId = $(this).parent().prev().attr("data-comment-id");
        let parentDiv = $(this).parent().parent();

        const request = await requests.patchComment(commentId,JSON.stringify(
            {
                "postId": postID,
                "description": $(this).parent().prev().text()
            }
        ));

        if(request.status == requests.statusCodes.OK){
            $(parentDiv).prepend(`<div class="alert alert-success" role="alert">
                Reply was updated!
            </div>`);
        }

    })
    $(".remove-comment").click(async function () {
        console.log("CLICKED..")
        let commentId = $(this).parent().prev().attr("data-comment-id");
        let parentDiv = $(this).parent().parent();

        const request = await requests.deleteComment(commentId);

        if(request.status == requests.statusCodes.OK){
            $(parentDiv).prepend(`<div class="alert alert-success" role="alert">
                Comment was deleted!
            </div>`);

            await util.changeRoute("/post/" + postID);
        }

    })
}


function displayComments(comments, loggedInUserId) {
    for (let i = 0; i < comments.length; i++) {
        let commentDescription = "";
        console.log(comments[i].user.id)
        if (loggedInUserId && comments[i].user.id == loggedInUserId) {
            commentDescription = `
    <div class="comment-edit-container">
        <p class="comment-edit"
        data-comment-id="${comments[i].id}" contenteditable="true">${comments[i].description}</p>
        <div>
            <i  class="save-comment fas fa-save fa-1x"></i>
            <i class="remove-comment fas fa-trash-alt fa-1x"></i>
        </div>
        
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

const renderPost = async () => {
    const request = await requests.getPost(postID);

    if (request.status == requests.statusCodes.OK) {

        const response = await request.json();

        let post = response.data;
        let comments = response.data.comments;


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

        const loggedInUserId = await requests.getLoggedInUserId();
        const valueId = await loggedInUserId.json();

        if (loggedInUserId.status == requests.statusCodes.OK) {
            displayComments(comments, valueId);

        }

        checkIfUserIsLoggedIn();

    }

}


renderComment();
renderPost();
