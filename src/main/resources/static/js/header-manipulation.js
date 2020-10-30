import * as requests from "/js/requests.js"
import * as util from "/js/util.js"


async function updateHeader () {
    const request = await requests.userData();

    if (request.status == requests.statusCodes.ACCEPTED) {
        console.log("SUCCESS")
        const onlineUser = await request.json();

        $(".main-header").append(`
            <div class="nav-list-container">
                    <p>${onlineUser.data.userName}</p>
                    <div style="background-color: green; cursor: pointer" id="logout" class="add-post">Logout</div>
                    <a href="/addpost" class="add-post">Add Post</a>
            </div>
                    `)

    } else {
        console.log("NOT SUCCESS")

        $(".main-header").append(`
        <div class="nav-list-container">
                    <a style="background-color: #148cb8;" href="/login" class="add-post">Login</a>
                    <a style="background-color: #148cb8;" href="/register" class="add-post">Register</a>
        </div>
                    `)

    }

    $("#logout").click( async function () {

        const request = await requests.logout();
        // check response kod istället, fail när logout misslyckas och ok när den gör

        const response = await request.json();

        console.log(response)

        if(response.message = "Deleted user session"){
            util.changeRoute("/");

        }

    })

    //console.log(JSON.stringify(onlineUser))
}

updateHeader();


