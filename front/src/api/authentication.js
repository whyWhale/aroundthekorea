import axios from 'axios'
import {AuthenticationStore} from "@/stores/authentication.js";

const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL_HTTPS,
    timeout: 2500,
    validateStatus: status => status < 500,
})

const authentication = AuthenticationStore();
instance.interceptors.request.use(config => {
    const certification = authentication.certify;
    if (certification) {
        config.headers.Authorization = `Basic ${certification}`
    }

    return config
});

instance.interceptors.response.use((response) => {
    const newToken = response.headers[import.meta.env.VITE_TOKEN_HEADER];
    if (newToken) {
        authentication.replace(newToken);
    }

    return response;
}, (error) => {
    console.error(`error => ${error}`)

    return Promise.reject(error);
});

export default instance;