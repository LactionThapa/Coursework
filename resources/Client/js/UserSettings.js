let newUsername;
function pageLoad() {
    let username = Cookies.get("username");
    document.getElementById("user").innerHTML = username;
    let listsHTML = ``;

    fetch('/user/get', {method: 'get'}
    ).then(response => response.json()
    ).then(lists =>{

        if (lists.hasOwnProperty('error')) {
            alert(lists.error);
        } else {

            document.getElementById("UserID").value = lists.UserID;
            document.getElementById("Username").value = lists.Username;
            document.getElementById("Password").value = lists.Password;
            document.getElementById("EmailAddress").value = lists.EmailAddress;

            document.getElementById("editDiv").style.display = 'block';
            listsHTML += `<button class='deleteButton'>Delete Account</button>`;
        }

        document.getElementById('listDiv').innerHTML += listsHTML;

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteUser);
        }
    });

    document.getElementById("saveButton").addEventListener("click", saveSettings);
    document.getElementById("cancelButton").addEventListener("click", cancelSettings);
    document.getElementById("UsersName").innerHTML = username;

}


function saveSettings(event) {

    event.preventDefault();

    if (document.getElementById("Username").value.trim() === '') {
        alert("Please provide a username.");
        return;
    }
    if (document.getElementById("Password").value.trim() === '') {
        alert("Please provide a password.");
        return;
    }
    const id = document.getElementById("UserID").value;
    const form = document.getElementById("listForm");
    const formData = new FormData(form);

    fetch('/user/update/', {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            newUsername = document.getElementById("Username").value;
            Cookies.set("username", newUsername);
            window.location.href = '/client/WishList.html';
        }
    });
}

function cancelSettings(event) {

    event.preventDefault();

    window.location.href='/client/WishList.html';

}

function deleteUser() {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = document.getElementById("UserID").value;
        let formData = new FormData();
        formData.append("UserID", id);

        fetch('/user/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    window.location.href = '/client/index.html';
                }
            }
        );
    }
}