export function registerReplyTemplate() {
    return `
        <div class="panel-body">
            <form id="reply-form" method="post">
                <fieldset>
                    <div class="form-group">
                        <textarea class="form-control comment" rows="3" placeholder="Write in your wall" autofocus=""></textarea>                            
                    </div>
                    <!--<button type="submit" class="[ btn btn-info]" data-loading-text="Loading...">댓글 등록</button>-->
                </fieldset>
            </form>
        </div>
    `;
}

export function replyTemplate({writer, comment}) {
    const profileFileName = writer.profile ? writer.profile.fileName : '';

    return `<li class='post-reply'>
               <div class='post-reply-wrap'>
                   <div class='post-reply-profile'>
                   <img alt='${writer.name}-profile' src='./images/${profileFileName}'>
                   </div>
                   <div class='post-reply-name'>
                       <p>${writer.name}</p>
                   </div>
    
                   <div class='post-reply-text'>${comment}</div>
                   
               </div>
           </li>`;
}