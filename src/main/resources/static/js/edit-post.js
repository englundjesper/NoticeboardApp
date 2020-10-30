import * as requests from "/js/requests.js"
import * as util from "/js/util.js"

let location = window.location.href;
let postID = location.split("post/")[1].split("/")[0];

async function handleEdit(){
    const updatedPost = await requests.patchPost(postID,JSON.stringify(
        {
            "title": $("#title").val(),
            "description": $("#post-content").val()
        }
    ));

    if(updatedPost.status == requests.statusCodes.OK){
        console.log("HERER")
        $(".main-container").prepend(`<div class="alert alert-success" role="alert">
                Post updated!
            </div>`)

        await util.removeAlert();
        util.changeRoute("/post/" + postID)

    }

}

async function renderEditPost(){
    const request = await requests.checkIfLoggedInUserIsPostOwner(postID);

    if(request.status == requests.statusCodes.OK){

        const postRequest = await requests.getPost(postID);
        const response = await postRequest.json();

        let post = response.data;

        $('#title').val(post.title);

        $('#post-content').val(post.description);

        $("#target").click(handleEdit);

    } else {
        util.changeRoute("/post/" + postID);
    }

}

renderEditPost();