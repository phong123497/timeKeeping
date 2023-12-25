import axios from "axios";

async function apiRequest(baseURL, url, method, data) {
    let token = sessionStorage.getItem("TOKEN_AUTH");
    let headers = {
        "Authorization": "",
        "Content-Type": "application/json"
    };

    if (token ) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    try {
        const dataResponse = await axios({
            baseURL,
            url,
            method,
            headers,
            data
        })
        return dataResponse;
    } catch (error) {
        console.log(error.response);
        return error.response;
    }

}

export default apiRequest;

