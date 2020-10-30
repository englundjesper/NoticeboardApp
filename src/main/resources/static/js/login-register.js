import * as requests from "/js/requests.js"
import * as util from "/js/util.js"

$("#targetLogin").submit(async function (e) {

    e.preventDefault();


    const request = await requests.login(JSON.stringify(
        {"userName": $("#username").val(), "password": $("#password").val()}
    ));

    if (request.status == requests.statusCodes.CREATED) {
        $(".main-container").prepend(`<div class="alert alert-success" role="alert">
                Logged In!
        </div>`);

        await util.removeAlert();
        util.changeRoute("/");
    } else if (request.status == requests.statusCodes.UNAUTHORIZED) {
        $(".main-container").prepend(`<div class="alert alert-danger" role="alert">
            Wrong Password
            </div>`);
        $("#password").val("");

    } else {
        $(".main-container").prepend(`<div class="alert alert-danger" role="alert">
            Could not log in!
            </div>`);
        $("#password").val("");
    }

});

$("#targetRegister").submit(async function (e) {

    e.preventDefault();

    const request = await requests.register(JSON.stringify(
        {
            "firstName": $("#firstname").val(),
            "lastName": $("#lastname").val(),
            "userName": $("#username").val(),
            "password": $("#password").val()
        }
    ));

    console.log(request.status);
    if (request.status == requests.statusCodes.CREATED) {
        $(".main-container").prepend(`
            <div class="alert alert-success" role="alert">
                 Registered!
            </div>`);

        await util.removeAlert();
        util.changeRoute("/login");
    } else {
        $(".main-container").prepend(`
            <div class="alert alert-danger" role="alert">
                Registration failed!
            </div>`);
    }

});