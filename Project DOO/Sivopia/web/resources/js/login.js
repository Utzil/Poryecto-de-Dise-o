var openLogin;
var openSignup;
var contLogin;
var contSignup;

window.addEventListener('DOMContentLoaded', function () {

    openLogin = document.getElementById('openLogin');
    openSignup = document.getElementById('openSignup');
    contLogin = document.getElementsByClassName('login');
    contSignup = document.getElementsByClassName('signup');

    openLogin.addEventListener('click', function () {
        openLogin.classList.toggle('op-account-active');
        openSignup.classList.toggle('op-account-active');
        open(contLogin[0], contSignup[0]);
    });

    openSignup.addEventListener('click', function () {
        openLogin.classList.toggle('op-account-active');
        openSignup.classList.toggle('op-account-active');
        open(contSignup[0], contLogin[0]);
    });

    function open(elementToOpen, elementToClose) {
        elementToOpen.classList.toggle('d-none');
        elementToClose.classList.toggle('d-none');
    }
}, true);
