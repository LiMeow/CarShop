 import * as urls from '/js/urls.js';

 var formEl = document.getElementById('form');

 formEl.addEventListener('submit', function(event) {
    var headers = new Headers();
    headers.set('Accept', 'application/json');
    var formData = new FormData();
    for (var i = 0; i < formEl.length; ++i) {
       formData.append(formEl[i].name, formEl[i].value);
    }

    var cardNumber = document.getElementById('cardNumber').value;
    var expiryDate = document.getElementById('expiryDate').value;
    var cvv = document.getElementById('cvv').value;
    var cardHolderName = document.getElementById('cardHolderName').value;
    var money = document.getElementById('money').value;
    var transactionId = document.getElementById('transactionId').value;

    formData.append('json', JSON.stringify({
    cardNumber: 'cardNumber',
    expiryDate: 'expiryDate',
    cvv: 'cvv',
    cardHolderName: 'cardHolderName',
    money: 'money'}));

    var fetchOptions = {
        method: 'POST',
        headers,
        body: formData
    };
    var responsePromise = fetch(urls.BANK_TAKE_MONEY, fetchOptions);

     responsePromise
     .then(function(response) {
        if(response.status !==200) {
            return Promise.reject(new Error(response.statusText))
        }
        return response.json();
      })
     .then(function(jsonData) {
        location.href = urls.BANK_SUCCESS_OPERATION.replace('{transactionId}',transactionId);
     })
     .catch(function (error) {
        location.href = urls.BANK_FAILURE_OPERATION;
     });
     event.preventDefault();
});