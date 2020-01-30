function pageLoad() {
    checkLogin();
    let qs = getQueryStringParameters();
    let id = Number(qs["id"]);
    let listHTML = ``;
    fetch('/Item/list/' + id, {method: 'get'}
    ).then(response => response.json()
    ).then(lists => {
        for (let list of lists) {
            listHTML +=
                `<h1 style="text-align: center;">${list.ItemName}</h1>` +
                `<p style="display: none;" ${list.ItemID}</p>`+
                `<p style="font-size: 20px">Price: &pound${list.Price}</p>`+
                `<p style="font-size: 20px">Quantity: ${list.Quantity}</p>`+
                `<button class='editButton' id='settings' data-id='${list.ItemID}'>Edit</button>` +
                `<button class='deleteButton' id='settings1' data-id='${list.ItemID}'>Delete</button>`+
                `<button id="BUY"><a style="text-decoration: none; font-size: 20px" href="${list.URL}">BUY</a></button>`;

        }
        document.getElementById("list").innerHTML= listHTML;
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
    const id = event.target.getAttribute("data-id");


    fetch('/Item/get/' + id, {method: 'get'}
    ).then(response => response.json()
    ).then(lists => {

        if (lists.hasOwnProperty('error')) {
            alert(lists.error);
        } else {

            document.getElementById("editHeading").innerHTML = 'Editing ' + lists.ItemName + ':';

            document.getElementById("ItemID").value = id;
            document.getElementById("ItemName").value = lists.ItemName;
            document.getElementById("Price").value = lists.Price;
            document.getElementById("Quantity").value = lists.Quantity;
            document.getElementById("URL").value = lists.URL;


            document.getElementById("list").style.display = 'none';
            document.getElementById("editDiv").style.display = 'block';

        }
    });

}

function saveEditlist(event) {

    event.preventDefault();

    if (document.getElementById("ItemName").value.trim() === '') {
        alert("Please provide a list name.");
        return;
    }
    if (document.getElementById("Price").value.trim() === '') {
        alert("Please provide the price of the item");
        return;
    }
    if (document.getElementById("Quantity").value.trim() === '') {
        alert("Please provide the quantity of the item.");
        return;
    }
    const id = document.getElementById("ItemID").value;
    const form = document.getElementById("listForm");
    const formData = new FormData(form);


    fetch('/Item/update/', {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            document.getElementById("list").style.display = 'block';
            document.getElementById("editDiv").style.display = 'none';
            pageLoad();
        }
    });
}

function cancelEditlist(event) {

    event.preventDefault();

    document.getElementById("list").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';

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
                    window.location.href="http://localhost:8081/client/WishList.html" ;
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