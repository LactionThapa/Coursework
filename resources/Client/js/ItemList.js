let ListID;
function pageLoad() {
    checkLogin();
    let qs = getQueryStringParameters();
    let id = Number(qs["id"]);
    ListID = id;
    let listsHTML = `<table style="width:80%">` +
        '<tr>' +
        '<th style="text-align: middle;" class="ID">ItemID</th>' +
        '<th style="text-align: middle;">ItemName</th>' +
        '<th style="text-align: middle;">Price</th>' +
        '<th style="text-align: middle;" class="last">Options</th>' +
        '</tr>';

    fetch('/Item/listItem/' + id, {method: 'get'}
    ).then(response => response.json()
    ).then(lists => {
        for (let list of lists) {

            listsHTML += `<tr>` +
                `<td class="ID">${list.ItemID}</td>` +
                `<td id="things"><a href="/client/Item.html?id=${list.ItemID}"</a>${list.ItemName}</td>` +
                `<td> &pound${list.Price}</td>` +
                `<td class="last">` +
                `<button class='deleteButton' data-id='${list.ItemID}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }

        listsHTML += '</table>';

        document.getElementById("listDiv").innerHTML = listsHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editList);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deletelist);
        }

    });

    document.getElementById("saveButton").addEventListener("click", saveEditlist);
    document.getElementById("cancelButton").addEventListener("click", cancelEditlist);
    let username = Cookies.get("username");
    document.getElementById("UsersName").innerHTML = username;

}

function editList(event) {
        document.getElementById("editHeading").innerHTML = 'Add new list: ';

        document.getElementById("ItemID").value = '';
        document.getElementById("ItemName").value = '';
        document.getElementById("Price").value = '';
        document.getElementById("URL").value = '';
        document.getElementById("Quantity").value = '';
        document.getElementById("ListID").value = ListID;

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';
        document.getElementById("newButton").style.display = 'none';

}

function saveEditlist(event) {

    event.preventDefault();

    if (document.getElementById("ItemName").value.trim() === '') {
        alert("Please provide an item name.");
        return;
    }
    if (document.getElementById("Price").value.trim() === '') {
        alert("Please provide the price of the item.");
        return;
    }
    if (document.getElementById("Quantity").value.trim() === '') {
        alert("Please provide the Quantity of the item.");
        return;
    }
    const id = document.getElementById("ItemID").value;
    const form = document.getElementById("listForm");
    const formData = new FormData(form);

    fetch('/Item/add/', {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            document.getElementById("listDiv").style.display = 'block';
            document.getElementById("editDiv").style.display = 'none';
            document.getElementById("newButton").style.display = 'block';
            pageLoad();
        }
    });
}

function cancelEditlist(event) {

    event.preventDefault();

    document.getElementById("listDiv").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';
    document.getElementById("newButton").style.display = 'block';

}

function deletelist(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);

        fetch('/Item/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoad();
                }
            }
        );
    }
}
function checkLogin(){
    let username = Cookies.get("username");

    if(username === undefined) {
        window.location.href = '/client/index.html';
    }
}