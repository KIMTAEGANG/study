let xhr;
const callAjax = (method, url, headers, data, callback) => {
    xhr = new XMLHttpRequest();
    xhr.timeout = 1500;
    xhr.open(method, url, true);
    for(let header in headers) {
        xhr.setRequestHeader(header, headers[header]);
    }
    data = data != null ? JSON.stringify(data) : null;
    xhr.send(data);

    xhr.onreadystatechange = () => {
        if(xhr.status === 200 || xhr.status === 201) {
            callback(xhr);
        }
    }

}


const ajax = {
    get: (url, headers, data, callback) => {
        callAjax('GET', url, headers, data, (xhr) => {
            callback(xhr);
        });
    },

    post: (url, headers, data, callback) => {
        // headers['Content-Type'] = 'application/json';
        callAjax('POST', url, headers, data, (xhr) => {
            callback(xhr);
        });
    },

    patch: (url, headers, data, callback) => {
        headers['Content-Type'] = 'application/json';
        callAjax('PATCH', url, headers, data, (xhr) => {
            callback(xhr);
        });
    },

    delete: (url, headers, data, callback) => {
        headers['Content-Type'] = 'application/json';
        callAjax('DELETE', url, headers, data, (xhr) => {
            callback(xhr);
        });
    },
}

