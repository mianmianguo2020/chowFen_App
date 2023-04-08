
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}

// check account
function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter the account number"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("Account length should be 3-20"))
  } else {
    callback()
  }
}

// check name
function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter your name"))
  } else if (value. length > 12) {
    callback(new Error("Account length should be 1-12"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
  if (value == "") {
    callback(new Error("Please enter your phone number"))
  } else if (!isCellPhone(value)) {//Introduce the method of checking the cell phone format encapsulated in methods
    callback(new Error("Please enter the correct phone number!"))
  } else {
    callback()
  }
}


function validID (rule, value, callback) {
  // The ID card number is 15 or 18 digits. When 15 digits are all numbers, the first 17 digits of the 18 digits are numbers, and the last digit is a check digit, which may be a number or character X
  let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if(value == '') {
    callback(new Error('Please enter your ID card number'))
  } else if (reg. test(value)) {
    callback()
  } else {
    callback(new Error('ID card number is incorrect'))
  }
}