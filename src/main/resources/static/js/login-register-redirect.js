import * as requests from "/js/requests.js"
import * as util from "/js/util.js"

const redirectIfUserIsOnline = async ()=>{
    const request = await requests.isUserOnline();
    const response = await request.json();

    if(response ==true){
        util.changeRoute("/");
    }

}

redirectIfUserIsOnline();