var web_prefix = '/front'

function imgPath(path){
    return '/common/download?name=' + path
}

function parseUrl(url) {
    var parse = url.substring(url.indexOf("?") + 1),
        params = parse.split("&"),
        len = params.length,
        item = [],
        param = {};

    for (var i = 0; i < len; i++) {
        item = params[i].split("=");
        param[item[0]] = item[1];
    }

    return param;
}

