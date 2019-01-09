import {$} from './lib/utils.js';

$('#user-image').addEventListener('change', changeImage);

function changeImage(){
    const fileInput = $('input[name=file]');
    const fileReader = new FileReader();

    fileReader.addEventListener('load', ({target: {result}}) => {
        $('#user-image-preview').src = result;
    });
    fileReader.readAsDataURL(fileInput.files.item(0));
}

$('#user-form').addEventListener('submit',uploadHandler);

function uploadHandler(e){
    e.preventDefault();
    const formData = new FormData($('#user-form'));
    console.log(formData);
    const userId = $('#user-id').value;
    console.log(userId);
    fetch('/api/users/'+userId,{
        method : 'post',
        credentials: 'same-origin',
        body: formData
    }).then(response=>{
        if(!response.ok){
            throw '수정 오류가 생겼습니다.';
        }
        location.href = '/';
    })
        .catch(error=>{
            console.error(error);
        })

}