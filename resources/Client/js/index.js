function pageLoad(){
    start();
    if(window.location.search === '?logout') {
        document.getElementById('content').innerHTML = '<h1>Logging out, please wait...</h1>';
        logout();
    }
    checkLogin()
}

function start(){
    document.getElementById('signup-form').style.display = 'none';
    document.getElementById('signup').style.background = 'none';
    document.getElementById('signup').addEventListener('click',signUpForm);
    document.getElementById('login').addEventListener('click',logInForm);
    document.getElementById("loginButton").addEventListener("click", login);
    document.getElementById("SignUpButton").addEventListener("click", signUp);


}

function signUpForm() {

    document.getElementById('signup-form').style.display = 'block';
    document.getElementById('signup').style.background = 'white';
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('login').style.background = 'none';

}

function logInForm() {

    document.getElementById('signup-form').style.display = 'none';
    document.getElementById('signup').style.background = 'none';
    document.getElementById('login-form').style.display = 'block';
    document.getElementById('login').style.background = 'white';

}

function login(event) {

    event.preventDefault();

    const form = document.getElementById("loginForm");
    const formData = new FormData(form);

    fetch("/user/login", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.set("username", responseData.username);
            Cookies.set("token", responseData.token);

            window.location.href = '/client/WishList.html';
        }
    });
}


function logout() {

    fetch("/user/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {

            Cookies.remove("username");
            Cookies.remove("token");

            window.location.href = '/client/index.html';

        }
    });

}

function checkLogin(){
    let username = Cookies.get("username");

    if(username !== undefined && window.location.search !== '?logout') {
        logout()
    }
}

function signUp(event){

    event.preventDefault();

    const form = document.getElementById("SignUpForm");
    const formData = new FormData(form);

    fetch("/user/new", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        }else {
            fetch("/user/login", {method: 'post', body: formData}
            ).then(response => response.json()
            ).then(responseData => {

                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    Cookies.set("username", responseData.username);
                    Cookies.set("token", responseData.token);

                    window.location.href = '/client/WishList.html';
                }
            });
        }
    });
}