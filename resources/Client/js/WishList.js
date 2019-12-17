function pageLoad() {
    let listsHTML = `<table style="width:100%">` +
        '<tr>' +
        '<th style="text-align: left;" class="ID">ListID</th>' +
        '<th style="text-align: left;">ListName</th>' +
        '<th style="text-align: left;">Status</th>' +
        '<th style="text-align: left;" class="last">Options</th>' +
        '</tr>';

    fetch('/WishList/list', {method: 'get'}
    ).then(response => response.json()
    ).then(lists => {

        for (let list of lists) {

            listsHTML += `<tr>` +
                `<td class="ID">${list.ListID}</td>` +
                `<td id="things"><a href="/client/ItemList.html?id=${list.ListID}">${list.ListName}</a></td>` +
                `<td>${list.Status}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${list.ListID}'>Edit</button>` +
                `<button class='deleteButton' data-id='${list.ListID}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }

        listsHTML += '</table>';

        document.getElementById("listDiv").innerHTML = listsHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editList);
        }
        /*
        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deletelist);
        }
        */
    });

    //document.getElementById("saveButton").addEventListener("click", saveEditlist);
    //document.getElementById("cancelButton").addEventListener("click", cancelEditlist);

}

function editList(event) {
    const id = event.target.getAttribute("data-id");

    if (id === null){
        document.getElementById("editHeading").innerHTML = 'Add new list: ';

        document.getElementById("ListName").value = ' ';
        document.getElementById("Status").value = ' ';

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';

    } else {
        fetch('/WishList/get/' + id, {method: 'get'}).
            then(response => response.json()).
            then(list => {
                if (list.hasOwnProperty('error')) {
                    alert(list.error);
                } else {

                    document.getElementById("editHeading").innerHTML = 'Editing' + list.ListName + ':';

                    document.getElementById("ListName").value = list.ListName;
                    document.getElementById("Status").value = list.Status;

                    document.getElementById("listDiv").style.display = 'none';
                    document.getElementById("editDiv").style.display = 'block';

                }
        });
    }


}

