export function postTableTemplate({id, writer, title, content, createdDate}) {
    return `
        <tr class="post" data-column="${id}">
            <td>${id}</td>
            <td>${title}</td>
            <td>${writer.name}</td>
            <td>${content}</td>
            <td>${createdDate}</td>
</tr>
    `;
}

export function pageLinkTemplate(first, last, currentPage, totalPages, pageSize) {
    let html = '';

    html += `
            <li class="page-item previous ${first === true ? 'disabled' : ''}">
                <a class="page-link" href="#" data-column="${currentPage}">Previous</a>
            </li>
            `;

    let pageLastNum = (parseInt(currentPage / pageSize) + 1) * pageSize;
    let i;

    for (i = pageLastNum - pageSize + 1; i <= pageLastNum; i++) {
        if (totalPages >= i) {
            html +=
                `<li class="page-item num ${currentPage + 1 === i ? 'active' : ''}" data-column="${i}">
                    <a class="page-link" href="#" data-column="${i}">${i}</a>
                 </li>               
            `;
        }
    }

    html += `<li class="page-item next ${last === true ? 'disabled' : ''}">
            <a class="page-link" href="#" data-column="${currentPage + 2}">Next</a>
        </li>`;

    return html;
}

export function postDetailTemplate({createdDate, title, writer, content, fileInfos}) {
    let html = '';
    html += `
        <input type="hidden" name="writer-id" data-column="${writer.id}"/>
        <dl class="row">
            <dt>작성일</dt>
            <dd class="create-date">${createdDate}</dd>
        </dl>
        <dl class="row">
            <dt>제목</dt>
            <dd class="title">${title}</dd>
        </dl>
        <dl class="row">
            <dt>작성자</dt>
            <dd class="writer">${writer.name}</dd>
        </dl>
        <dl class="row">
            <dt>내용</dt>
            <dd class="content">${content}</dd>
        </dl>
        <dl class="row">
        <dt>파일</dt>
        `;
    for (let i in fileInfos) {
        html += `<dd><a href="/downloadFile/${fileInfos[i].id}">${fileInfos[i].fileOriginName}</a></dd>`;
    }

    html += `</dl>`;

    return html;
}

export function postUpdateTemplate(title, content) {
    let html = '';
    html += `
        <form id="upload-form" method="post" enctype="multipart/form-data">
            <!--<input type="hidden" name="_method" value="put" />-->
            <div class="form-group">
                <label class="control-label">제목</label>
                <div class="input-group">
                    <input type="text" class="form-control" name="title"
                           placeholder="제목 입력하세요" value="${title}" required/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label">내용</label>
                <div class="input-group">
                    <textarea name="content" id="content" class="form-control" cols="30" rows="10">${content}</textarea>
                </div>
            </div>
    `;

    html += `
    <input type="file" name="files" multiple="multiple">
            <input type="submit" value="업로드">
        </form>`;
    return html;
}