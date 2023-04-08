(function (win) {
  axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
  const service = axios.create({
    baseURL: '/',
    timeout: 1000000
  })
  service.interceptors.request.use(config => {

    if (config.method === 'get' && config.params) {
      let url = config.url + '?';
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName];
        var part = encodeURIComponent(propName) + "=";
        if (value !== null && typeof(value) !== "undefined") {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              let params = propName + '[' + key + ']';
              var subPart = encodeURIComponent(params) + "=";
              url += subPart + encodeURIComponent(value[key]) + "&";
            }
          } else {
            url += part + encodeURIComponent(value) + "&";
          }
        }
      }
      url = url.slice(0, -1);
      config.params = {};
      config.url = url;
    }
    return config
  }, error => {
      console.log(error)
      Promise.reject(error)
  })

  service.interceptors.response.use(res => {
      console.log('---响应拦截器---',res)
      const code = res.data.code;
      const msg = res.data.msg
      console.log('---code---',code)
      if (res.data.code === 0 && res.data.msg === 'NOTLOGIN') {// 返回登录页面
        console.log('---/backend/page/login/login.html---',code)
        localStorage.removeItem('userInfo')
        window.top.location.href = '/backend/page/login/login.html'
      } else {
        return res.data
      }
    },
    error => {
      console.log('err' + error)
      let { message } = error;
      if (message == "Network Error") {
        message = "Backend interface connection exception";
      }
      else if (message. includes("timeout")) {
        message = "System interface request timed out";
      }
      else if (message. includes("Request failed with status code")) {
        message = "system interface" + message.substr(message.length - 3) + "exception";
      }
      window.ELEMENT.Message({
        message: message,
        type: 'error',
        duration: 5*1000
      })
      return Promise. reject(error)
    }
  )
  win.$axios = service
})(window);
