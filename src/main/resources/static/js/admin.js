import {$, $all} from './lib/utils.js';
import {postTableTemplate, pageLinkTemplate} from './template/PostTemplate.js';
import {dataTableTemplate} from './template/AdminTemplate.js'

let size = 10;
let page = 1;
const pageSize = 3;

let selectedType = 'user';

function loadData(selectedType, postPage) {

    fetch(`/api/admin?selectedType=${selectedType}&size=${size}&page=${postPage - 1}&sort=id,desc`)
        .then(response => {
            if (!response.ok) {
                throw selectedType + ' data 불러오기에 실패하였습니다.';
            }
            return response.json();
        })
        .then(({data}) => {
            console.log(data);
            const tbody = $('tbody');
            tbody.innerHTML = '';
            $('.pagination').innerHTML = '';

            data.content.forEach(data => {
                console.log(data);
                tbody.innerHTML += dataTableTemplate(selectedType, data);
            })

            $all('.close').forEach(post => attachDeleteEventListener(post));

            $('.pagination').innerHTML = pageLinkTemplate(data.first, data.last, data.number, data.totalPages, pageSize);
            attachPageEventListener(data.first, data.last);
        })
        .catch(error => {
            console.error(error);
        })
}

function attachDeleteEventListener(data) {
    data && data.addEventListener('click', function (e) {
        if (e.target && e.target.nodeName == 'SPAN') {
            deleteData(e.target.dataset.column);
        }
    });
}

function deleteData(dataId) {
    if (!confirm('정말로 삭제하시겠습니까?')) {
        return;
    }

    fetch(`/api/admin/${dataId}?selectedType=${selectedType}`, {
        method : 'DELETE',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (!response.ok) {
                throw "데이터 삭제에 실패 하였습니다."
            }
            $(`#${selectedType}-${dataId}`).remove();
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

function changeLoadDataByType() {
    selectedType = $('.select-type').value;
    const thData = $('.th-data');

    if (selectedType === 'user') {
        thData.innerText = '이름';
    }
    else if (selectedType === 'post') {
        thData.innerText = '제목';
    }
    else if (selectedType === 'reply') {
        thData.innerText = '댓글';
    }

    page = 0;

    loadData(selectedType, page);
}

function changeLoadPostByPage(index) {
    page = index;
    console.log(page, index);
    loadData(selectedType, page);
}

$('.select-type').addEventListener('change', changeLoadDataByType);
document.addEventListener("DOMContentLoaded", loadData(selectedType, page));