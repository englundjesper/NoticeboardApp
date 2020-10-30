
export const changeRoute = (theLocation) =>{
    window.location.href = theLocation;
}

export const removeAlert = async ()=> {
    return new Promise(function (resolve) {
        setTimeout(function () {
            $('.alert').remove();
            resolve();
        }, 2000);

    });
}