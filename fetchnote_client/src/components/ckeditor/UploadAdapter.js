class UploadAdapter {
  constructor(loader,uploader) {
      this.loader = loader;
      this.uploader = uploader;
  }

  upload() {
      return this.loader.file.then( file => new Promise(((resolve, reject) => {
          this._initRequest();
          this._initListeners( resolve, reject, file );
          this._sendRequest( file );
      })))
  }

  _initRequest() {
      const uploader = this.uploader;
      const xhr = this.xhr = new XMLHttpRequest();
      xhr.open('POST', 'https://localhost:8080/image', true);
      xhr.setRequestHeader("patchId" , String(uploader));
      xhr.responseType = 'json';
  }

  _initListeners(resolve, reject, file) {
      const xhr = this.xhr;
      const loader = this.loader;
      const genericErrorText = 'can not upload file';

      xhr.addEventListener('error', () => {reject(genericErrorText)});
      xhr.addEventListener('abort', () => reject());
      xhr.addEventListener('load', () => {
          const response = xhr.response;
          if(!response || response.error) {
              return reject( response && response.error ? response.error.message : genericErrorText );
          }

          console.log(response);

          resolve({
              default: response.URL
          });
      });
  }

  _sendRequest(file) {
      const data = new FormData();
      data.append('upload',file);
      this.xhr.send(data);
  }
}

export default UploadAdapter;