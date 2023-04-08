function trim (str) {
  return str == undefined ? "" : str.replace(/(^\s*)|(\s*$)/g, "")
}

function requestUrlParam(argname){
  var url = location.href
  var arrStr = url.substring(url.indexOf("?")+1).split("&")
  for(var i =0;i<arrStr.length;i++)
  {
      var loc = arrStr[i].indexOf(argname+"=")
      if(loc!=-1){
          return arrStr[i].replace(argname+"=","").replace("?","")
      }
  }
  return ""
}
