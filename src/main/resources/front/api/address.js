function addressListApi() {
    return $axios({
      'url': '/addressBook/list',
      'method': 'get',
    })
  }

function addressLastUpdateApi() {
    return $axios({
      'url': '/addressBook/lastUpdate',
      'method': 'get',
    })
}

function  addAddressApi(data){
    return $axios({
        'url': '/addressBook',
        'method': 'post',
        data
      })
}

function  updateAddressApi(data){
    return $axios({
        'url': '/addressBook',
        'method': 'put',
        data
      })
}

function deleteAddressApi(params) {
    return $axios({
        'url': '/addressBook',
        'method': 'delete',
        params
    })
}

function addressFindOneApi(id) {
  return $axios({
    'url': `/addressBook/${id}`,
    'method': 'get',
  })
}

function  setDefaultAddressApi(data){
  return $axios({
      'url': '/addressBook/default',
      'method': 'put',
      data
    })
}

function getDefaultAddressApi() {
  return $axios({
    'url': `/addressBook/default`,
    'method': 'get',
  })
}