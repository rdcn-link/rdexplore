import axios from 'axios'
import Vue from 'vue'


let request = axios.create()

request.defaults.timeout = 300000
request.defaults.headers.post['Content-Type'] = 'application/json'
request.defaults.headers.get['Content-Type'] = 'application/json'
// 部署注释4
request.defaults.baseURL = process.env.VUE_APP_URL;

Vue.config.productionTip = false
request.interceptors.request.use(
    config => {

        return config
    },
    err => {
        return Promise.reject(err)
    }
)
// 添加响应拦截器
request.interceptors.response.use(
    response => {
        const res = response.data;


        return res
    },
    error => {

        return Promise.reject(error)
    }
)
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
                    let ret = ''
                    for (let it in body) {
                        ret +=
                            encodeURIComponent(it) + '=' + encodeURIComponent(body[it]) + '&'
                    }
                    return ret
                }
            ]
        })
    },
    setGetData(address, body) {
        return request({
            method: 'GET',
            url: address,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            transformRequest: [
                function () {
                    let ret = ''
                    for (let it in body) {
                        ret +=
                            encodeURIComponent(it) + '=' + encodeURIComponent(body[it]) + '&'
                    }
                    return ret
                }
            ]
        })
    },
    setGetType(address, body, responseType) {
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
    setDeleteType(address, body) {
        return request({
            method: 'DELETE',
            url: `${address}`,
            data: body
        })
    },

}
