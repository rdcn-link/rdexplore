import axios from 'axios'
import Vue from 'vue'

import {getToken} from "./token";


let request = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_API_BASE_URL:process.env.VUE_APP_URL,
    timeout: 30000,
    headers: {
        "Content-Type": "application/json;"
    }
});

Vue.config.productionTip = false;


request.interceptors.request.use(
    config => {
        let accessToken = getToken('accessToken');
        if (accessToken) {
            config.headers.Authorization = accessToken
        } else {
            config.headers.Authorization = ''
        }
        return config
    },
    err => {
        return Promise.reject(err)
    }
);

request.interceptors.response.use(
    response => {
        const res = response.data;
        if (res.code !== 200) {
            // message({
            //     message: res.message || '接口返回错误',
            //     type: 'error',
            //     duration: 2 * 1000
            // })
            return res
        } else {
            return res
        }
    },
    error => {
        return Promise.reject(error)
    }
);


export default {
    setFormData(address, body) {
        return request({
            method: 'POST',
            url: address,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            transformRequest: [
                function () {
                    let ret = '';
                    for (let it in body) {
                        ret +=
                            encodeURIComponent(it) + '=' + encodeURIComponent(body[it]) + '&'
                    }
                    return ret
                }
            ]
        })
    },
    setGetType(address, body,responseType) {
        return request({
            method: 'GET',
            url: `${address}`,
            params: body,
            responseType
        })
    },
    setPostType(address, body) {
        return request({
            method: 'POST',
            url: `${address}`,
            data: body
        })
    },
}
