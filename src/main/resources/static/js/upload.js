import {$, $all} from './lib/utils.js';


function uploadHandler(event) {
    event.preventDefault();

    const title = $('input[name=title]').value;
    const content = $('textarea[name=content]').value;

    const formData = new FormData($('#upload-form'));
    console.log(formData);
    fetch('/api/posts', {
        method     : 'post',
        credentials: 'same-origin',
        body       : formData
    })
        .then(response => {
            console.log(response.status);
            if (response.status >= 400 && response.status <= 404) {
                throw "게시글 등록에 실패하였습니다.";
            } else if (response.status === 201) {
                location.href = '/';
            }
        })
        .catch(error => {
            location.reload();
        });
}

$('#upload-form').addEventListener('submit', uploadHandler);
