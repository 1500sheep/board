import {$, $all} from './lib/utils.js';
import {postTableTemplate, pageLinkTemplate} from './template/PostTemplate.js';

let size = 5;
let page = 1;
const pageSize = 3;
let searchType = 'title';

function loadPost(postSize, postPage) {
    fetch(`/api/posts?size=${postSize}&page=${postPage - 1}&sort=id,desc`)
        .then(response => {
            if (!response.ok) {
                throw '게시판 불러오기에 실패하였습니다.';
            }
            return response.json();
        })
        .then(({data}) => {
            console.log(data);
            const tbody = $('tbody')
            tbody.innerHTML = '';
            $('.pagination').innerHTML = '';
            // chrome 에서 개발자 도구에서 하는 것과 동일
            // debugger;
            data.content.forEach(post => {
                console.log(post);
                tbody.innerHTML += postTableTemplate(post);
            })

            $all('.post').forEach(post => attachPostEventListener(post));

            $('.pagination').innerHTML = pageLinkTemplate(data.first, data.last, data.number, data.totalPages, pageSize);
            attachPageEventListener(data.first, data.last);
        })
        .catch(error => {
            console.error(error);
        })
}

function attachPostEventListener(data) {
    const postId = data.dataset.column;
    data && data.addEventListener('click', () => {
        console.log("postId : " + postId);
        location.href = `/post/${postId}`;
    });
}

function attachPageEventListener(first, last) {

    $('.pagination').addEventListener("click", function (e) {
        if (e.target && e.target.nodeName == 'A') {
            changeLoadPostByPage(e.target.dataset.column);
        }
    })
}

function changeLoadPostByNum() {
    size = $('.post-num').value;
    page = 0;
    loadPost(size, page);
}

function changeLoadPostByPage(index) {
    page = index;
    console.log(page, index);
    loadPost(size, page);
}

function searchHandler() {
    searchType = $('.search-box').value;
    const keyword = $('input[name=search-input]').value;
    if(!keyword){
        alert("검색을 입력하세요");
        return;
    }

    loadSearch(keyword);
}

function loadSearch(keyword) {
    // 여기 미완성
    fetch(`/api/posts/search?searchType=${searchType}&keyword=${keyword}&size=${size}&page=0&sort=id,desc`)
        .then(response => {
            if (!response.ok) {
                throw '게시판 불러오기에 실패하였습니다.';
            }
            return response.json();
        })
        .then(({data}) => {
            console.log(data);
            const tbody = $('tbody')
            tbody.innerHTML = '';
            $('.pagination').innerHTML = '';
            // chrome 에서 개발자 도구에서 하는 것과 동일
            // debugger;
            data.content.forEach(post => {
                console.log(post);
                tbody.innerHTML += postTableTemplate(post);
            })

            $all('.post').forEach(post => attachPostEventListener(post));

            $('.pagination').innerHTML = pageLinkTemplate(data.first, data.last, data.number, data.totalPages, pageSize);
            attachPageEventListener(data.first, data.last);
        })
        .catch(error => {
            console.error(error);
        })
}

$('.search-button').addEventListener('click', searchHandler);
$('.post-num').addEventListener('change', changeLoadPostByNum);
document.addEventListener("DOMContentLoaded", loadPost(size, page));