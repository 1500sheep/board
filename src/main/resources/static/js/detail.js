import {$, $all} from './lib/utils.js';
import {postDetailTemplate, postUpdateTemplate} from './template/PostTemplate.js';
import {registerReplyTemplate, replyTemplate} from './template/ReplyTemplate.js';

const pathname = window.location.pathname;
const postId = pathname.substring(pathname.lastIndexOf('/') + 1);

let title = '';
let content = '';

function loadPostHandler() {
    fetch(`/api/posts/${postId}`)
        .then(response => {
            if (!response.ok) {
                throw "상세 페이지 불러오기에 실패 하였습니다."
            }
            return response.json();
        })
        .then(({data}) => {
            $('.card-body').innerHTML = postDetailTemplate(data);
            title = data.title;
            content = data.content;
            if (checkLoginedUserIsWriter()) {
                $('button[name=update]').style.display = 'block';
                $('button[name=delete]').style.display = 'block';
            } else {
                $('button[name=update]').style.display = 'none';
                $('button[name=delete]').style.display = 'none';
            }

        })
        .catch(error => {
            console.error(error)
        })
}

function checkLoginedUserIsWriter() {
    const loginedUserId = $('input[name=user-id]');
    const writerUserId = $('input[name=writer-id]').dataset.column;

    if (loginedUserId) {

        $('button[name=register-reply]').style.display = 'block';
        $('.reply').innerHTML = registerReplyTemplate();

        if (loginedUserId.dataset.column == writerUserId) {
            return true;
        }
        return false;
    }
    return false;
}

function updatePostHandler() {

    $('.card-body').innerHTML = postUpdateTemplate(title, content);
    $('#upload-form').addEventListener('submit', updatePost);
}

function updatePost() {
    event.preventDefault();

    const title = $('input[name=title]').value;
    const content = $('textarea[name=content]').value;

    const formData = new FormData($('#upload-form'));
    console.log(formData);
    fetch(`/api/posts/${postId}`, {
        method     : 'PUT',
        credentials: 'same-origin',
        body       : formData
    })
        .then(response => {
            console.log(response.status);
            if (response.status >= 400 && response.status <= 404) {
                throw "게시글 수정에 실패 하였습니다."
            } else if (response.status === 200) {
                location.reload();
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function deletePostHandler() {
    fetch(`/api/posts/${postId}`, {
        method : 'DELETE',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (!response.ok) {
                throw "게시글 삭제 실패 하였습니다."
            }
            location.href = '/';
        })
        .catch(error => {
            console.error(error);
        })
}

function registerReplyHandler() {
    const comment = $('.comment').value;
    fetch(`/api/posts/${postId}/reply`, {
        method     : 'POST',
        headers    : {'Content-Type': 'application/json'},
        credentials: 'same-origin',
        body       : JSON.stringify({
            comment: comment
        })
    })
        .then(response => {
            if (!response.ok) {
                throw "댓글 등록에 실패하였습니다."
            }
            return response.json();
        })
        .then(({data}) => {
            $('.post-reply-list').innerHTML += replyTemplate(data);
            $('.comment').value = '';
        })
        .catch(error => {
            console.error(error);
        })
}

function loadReplyHandler() {
    fetch(`/api/posts/${postId}/reply`)
        .then(response => {
            if (!response.ok) {
                throw "댓글 불러오기에 실패 하였습니다."
            }
            return response.json();
        })
        .then(({data}) => {
            data.forEach(reply => {
                $('.post-reply-list').innerHTML += replyTemplate(reply);
            })

        })
        .catch(error => {
            console.error(error)
        })
}

$('button[name=delete]').addEventListener('click', deletePostHandler);
$('button[name=update]').addEventListener('click', updatePostHandler);
// $('button[name=save]').addEventListener('click', updatePost);
$('button[name=register-reply]').addEventListener('click', registerReplyHandler);

document.addEventListener("DOMContentLoaded", () => {
    loadPostHandler();
    loadReplyHandler();
});
