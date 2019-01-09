export function userTemplate({email, name, profileImageUrl, phoneNumber}) {
    return `
      <div class="user-detail" style="display: inline-block; text-align: center">
                <form id="user-form" enctype="multipart/form-data">
                    <h1 class="user-page">사용자 정보</h1>
                    <div class="user-info">
                        <div id="user-profile">
                            <label class="control-label">프로필</label>
                            <input type="file" name="user-image" multiple accept="image/*">
                            <img src="${profileImageUrl}" alt="">
                        </div>  
                        <div class="form-group">
                            <label class="control-label">이메일</label>
                            <input type="text" class="form-control" name="email" id="user-email" value="${email}" readonly>
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">이름</label>
                            <input type="text" class="form-control" name="name" id="user-name" value="${name}">
                        </div>
    
                        <div class="form-group">
                            <label class="control-label">이동전화</label>
                            <input type="text" class="form-control" name="phoneNumber"
                               placeholder="전화번호 입력 형식(010-0000-0000)" value="${phoneNumber}"/>
                        </div>
                        
                   </div> 
                </form>
        </div>
    `;
}

export function reservationTemplate({reservationName, endTime, startTime, reservationGroup, repeatNum, currentRepeatNum}) {
    const startHeight = 3 + parseInt(startTime.split(":")[0]) * 4 + parseInt(startTime.split(":")[1]) / 15;
    const endHeight = 3 + parseInt(endTime.split(":")[0]) * 4 + parseInt(endTime.split(":")[1]) / 15;

    return `
    <div class="${reservationGroup ? "repeat-many" : "repeat-one"}" style="top:${startHeight}em; height:${endHeight - startHeight}em;">
            <div class="title">${reservationGroup ? "총반복횟수(" + repeatNum + ")" : ""}</div>
            <div class="title">${reservationGroup ? "남은반복횟수(" + currentRepeatNum + ")" : ""}</div>
            <div>${reservationName}</div>
        </div>
    `;
}