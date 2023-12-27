import axios from 'axios';

// const axios = Axios.create();
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
// Message.config({
//     top: 0
// });
axios.cancel = {};
axios.defaults.withCredentials = true;
axios.interceptors.request.use((config) => {

    // config.headers['DS_TOKEN'] = getCookie('DS_TOKEN');
    // config.headers['spaceId'] = config.headers['spaceId'] || window.sessionStorage.getItem('spaceId');

    // console.log(taken);


    // let reqid = config.headers['reqid']
    // if (reqid) {
    //     config.cancelToken = new axios.CancelToken(cancel => {
    //         axios.cancel[reqid] = cancel
    //     })
    // }

    // let userInfo = getItem('userInfo')
    // // if (userInfo) {
    // config.headers['Authorization'] = userInfo.accessToken || getCookie("DS_TOKEN");
    // // } else {
    //     config.headers['Authorization'] = getCookie("DS_TOKEN");
    // }

    // let user = sessionStorage.getItem("user");
    // // console.log(user);
    // if (user) {
    //     user = JSON.parse(user);
    //     config.headers['token'] = `${user.token}`;
    // }


    // if (config.method === 'post') {
    //     config.data = qs.stringify(config.data);
    // }
    // if (config.method === 'get') {
    //     config.data = { params: config.data };
    // }
    // config.headers.Authorization = " ";
    // config.headers.token = ' ';
    return config;
});
// Add a response interceptor
// let time = null
axios.interceptors.response.use(
    (response) => {
        // console.log(response);
        // if (response.message == 'file-uplod-stop') {
        //     return response;
        // }
        // Message.destroy()
        // LoadingBar.finish();
        // Do something with response data
        // let data = response.data;
        // response.data = response.data
        // if (response.data.code !== 200) {
        //     // if (response.data.type == 'application/octet-stream') {

        //     // }
        //     if (response.data.code === 401) {
        //         router.replace({ path: '/login' });
        //     } else {
        // if (response.data.code == 401 || response.data.code == 5) {

        // }
        // else {
        //     mitt.emit("confirm-modal", {
        //         type: "info",
        //         title: '系统提示',
        //         content: response.data.message || '系统发生未知错误，请重试！'
        //     });
        // }

        // if (response.data.code == 500 || response.data.code == -1) {
        //     router.replace({ path: "/space" });
        // }
        return response;
    },
    (error) => {
        console.log(error);
        // if (error.message == 'file-uplod-stop') {
        //     return Promise.reject(error);
        // }
        // // console.log(error);
        // // Do something with response error
        // // Message.destroy()
        // // LoadingBar.error();
        // // Notice.error({
        // //     title: "系统提示",
        // //     desc: "数据请求失败，请检查网络是否连接正常！",
        // // });
        // if (error.message && error.message.type == 'upload-download') {
        //     Message.success("取消成功！");
        // } else {
        //     mitt.emit("confirm-modal", {
        //         type: "info",
        //         title: '系统提示',
        //         content: "系统发生错误或网络链接超时，请重试！",
        //     });
        // }

        return Promise.reject(error);
    }
);
axios.defaults.baseURL = process.env.VUE_APP_URL;
export default axios;
