const basePath = '/api/v1'

export const statusCodes = {
    "OK": 200,
    "NOT_FOUND": 404,
    "METHOD_NOT_ALLOWED": 405,
    "CREATED": 201,
    "CONFLICT": 409,
    "ACCEPTED": 202,
    "UNAUTHORIZED":401

}

export const isUserOnline = async()=>{
    const request = await fetch(basePath + "/auth/status");
    return request;
}

export const userData = async()=>{
    const request = await fetch(basePath + "/auth/user");
    return request;
}

export const logout = async()=>{
    console.log("HERE")
    return await fetch(basePath + "/auth/logout", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "dataType": 'json'
        }
    })
}
export const createComment = async (body)=>{
    return await fetch(basePath + "/comment/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "dataType": 'json'
        },
        body:body
    })
}

export const deleteComment = async (id)=>{
    console.log(basePath + "/comment/" +id);
    return await fetch(basePath + "/comment/" +id, {
        method: 'DELETE'
    })
}

export const login = async (body)=>{
    return await fetch(basePath + "/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "dataType": 'json'
        },
        body:body
    })
}

export const register = async (body)=>{
    return await fetch(basePath + "/user/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "dataType": 'json'
        },
        body:body
    })
}

export const patchComment = async (id,body)=>{
    return await fetch(basePath + "/comment/"+ id, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "dataType": 'json'
        },
        body:body
    })
}
export const patchPost = async (id,body)=>{
    return await fetch(basePath + "/post/update/"+ id, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "dataType": 'json'
        },
        body:body
    })
}

export const getPost = async (id)=>{
    const request = await fetch(basePath + "/post/" +id);
    return request;
}

export const deletePost = async (id)=>{
    return await fetch(basePath + "/post/" +id, {
        method: 'DELETE'
    })
}

export const checkIfLoggedInUserIsPostOwner = async (id)=>{
    const request = await fetch(basePath + "/post/" +id + "/session");
    return request;
}

export const getLoggedInUserId = async ()=>{
    const request = await fetch(basePath + "/auth/userId");
    return request;

}

