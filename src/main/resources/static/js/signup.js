import {$} from './lib/utils.js';


const registerFlag = {
    'email': false,
    'name': false,
    'password': false,
    'confirmPassword': false,
    'phoneNumber': false
};

function signupHandler(event) {
    event.preventDefault();


    validateConfirmPassword();

    if(!monitorRegisterButton()){
        return;
    }

    const email = $('input[name=email]').value;
    const name = $('input[name=name]').value;
    const password = $('input[name=password]').value;
    const confirmPassword = $('input[name=confirm]').value;
    const phoneNumber = $('input[name=phoneNumber]').value;

    fetch('/api/users', {
        method: 'post',
        headers: {'content-type': 'application/json'},
        credentials: 'same-origin',
        body: JSON.stringify({
            email,
            name,
            password,
            confirmPassword,
            phoneNumber
        })
    })
        .then(response => {
            console.log(response.status);
            if (response.status >= 400 && response.status <= 404) {
                validateError(response);
            } else if (response.status === 201) {
                location.href = '/';
            }
        })
        .catch(error => {
            location.reload();
        });
}

function validateError(response) {
    response.json().then(({errors}) => {
        errors.forEach((error) => {
            $(`strong[name=invalid-${error.field}`).style.visibility = 'visible';
            $(`strong[name=invalid-${error.field}`).innerText = error.message;
            registerFlag[`${error.field}`] = false;
        });
    })
}



function monitorRegisterButton() {
    for (const key in registerFlag) {
        if (registerFlag[key] === false) {
            return false;
        }
    }
    return true;
}

function validateEmail() {

    const regex_email = /^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/i;// 이메일이 적합한지 검사할 정규식
    const email = $('input[name=email]').value;

    if (!email.match(regex_email)) {
        $('strong[name=invalid-email]').style.visibility = 'visible';
        registerFlag['email'] = false;
    } else {
        $('strong[name=invalid-email]').style.visibility = 'hidden';
        registerFlag['email'] = true;
    }
}

function validateName() {
    const regex_name = /[가-힣]{2,16}|[a-zA-Z]{2,16}/;
    const name = $('input[name=name]').value;
    if (!name.match(regex_name)) {
        $('strong[name=invalid-name]').style.visibility = 'visible';
        registerFlag['name'] = false;
    } else {
        $('strong[name=invalid-name]').style.visibility = 'hidden';
        registerFlag['name'] = true;
    }

}

function validatePassword() {
    const regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;
    const password = $('input[name=password]').value;
    if (!password.match(regex_password)) {
        $('strong[name=invalid-password]').style.visibility = 'visible';
        registerFlag['password'] = false;
    } else {
        $('strong[name=invalid-password]').style.visibility = 'hidden';
        registerFlag['password'] = true;
    }

}

function validateConfirmPassword() {
    const confirm = $('input[name=confirm]').value;
    const password = $('input[name=password]').value;
    if (password !== confirm) {
        $('strong[name=invalid-confirmPassword]').style.visibility = 'visible';
        registerFlag['confirmPassword'] = false;
    } else {
        $('strong[name=invalid-confirmPassword]').style.visibility = 'hidden';
        registerFlag['confirmPassword'] = true;
    }

}

function validatePhone() {
    const regex_phone = /(01[016789])-(\d{3,4})-(\d{4})$/;
    const phone = $('input[name=phoneNumber]').value;
    if (!phone.match(regex_phone)) {
        $('strong[name=invalid-phoneNumber]').style.visibility = 'visible';
        registerFlag['phoneNumber'] = false;
    } else {
        $('strong[name=invalid-phoneNumber]').style.visibility = 'hidden';
        registerFlag['phoneNumber'] = true;
    }
}

$('#signupForm').addEventListener('submit', signupHandler);
// $('#btnCloseLayer').addEventListener('click', () => {
//     $('#layer').style.display = 'none';
// });

$('input[name=email]').onchange = validateEmail;
$('input[name=name]').onchange = validateName;
$('input[name=password]').onchange = validatePassword;
$('input[name=confirm]').onchange = validateConfirmPassword;
$('input[name=phoneNumber]').onchange = validatePhone;