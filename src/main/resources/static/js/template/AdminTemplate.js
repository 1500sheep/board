export function dataTableTemplate(selectedType, data) {
    if (selectedType === 'user') {
        return userAdminTemplate(data);
    }

    if (selectedType === 'post') {
        return postAdminTemplate(data);
    }

    if (selectedType === 'reply') {
        return replyAdminTemplate(data);
    }

    return;
}

function userAdminTemplate(data) {
    return `
        <tr class="user" align="center" id="user-${data.id}">
            <td>${data.id}</td>
            <td>${data.email}</td>
            <td>${data.name}</td>
            <td><button type="button" class="close" aria-label="Close">
  <span aria-hidden="true" data-column="${data.id}" data-type="user">&times;</span>
</button></td>
</tr>
    `;
}

function postAdminTemplate(data) {
    return `
        <tr class="user"  align="center" id="post-${data.id}">
            <td>${data.id}</td>
            <td>${data.writer.email}</td>
            <td>${data.title}</td>
            <td><button type="button" class="close" aria-label="Close">
  <span aria-hidden="true" data-column="${data.id}" data-type="post">&times;</span>
</button></td>
</tr>
    `;
}

function replyAdminTemplate(data) {
    return `
        <tr class="user"  align="center" id="reply-${data.id}">
            <td>${data.id}</td>
            <td>${data.writer.email}</td>
            <td>${data.comment}</td>
            <td><button type="button" class="close" aria-label="Close">
  <span aria-hidden="true" data-column="${data.id}" data-type="reply">&times;</span>
</button></td>
</tr>
    `;
}