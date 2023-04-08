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
    Promise.reject(error)
  })

  service.interceptors.response.use(res => {
        console.log('---response interceptor---', res)
        if (res.data.code === 0 && res.data.msg === 'NOTLOGIN') {// Return to the login page
          window.top.location.href = '/front/page/login.html'
        } else {
          return res.data
        }
      },
      error => {
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
        window.vant.Notify({
          message: message,
          type: 'warning',
          duration: 5*1000
        })
        //window.top.location.href = '/front/page/no-wify.html'
        return Promise. reject(error)
      }
  )
  win.$axios = service
})(window);