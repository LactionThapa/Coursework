function pageLoad() {

    let listsHTML = `<table style="width:100%">` +
        '<tr>' +
        '<th style="text-align: left;">ListID</th>' +
        '<th style="text-align: left;">ListName</th>' +
        '<th style="text-align: left;">Status</th>' +
        '<th style="text-align: left;">UserID</th>' +
        '<th style="text-align: left;" class="last">Options</th>' +
        '</tr>';

    fetch('/WishList/list', {method: 'get'}
    ).then(response => response.json()
    ).then(lists => {

        for (let list of lists) {

            listsHTML += `<tr>` +
                `<td>${list.ListID}</td>` +
                `<td>${list.ListName}</td>` +
                `<td>${list.Status}</td>` +
                `<td>${list.UserID}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${list.ListId}'>Edit</button>` +
                `<button class='deleteButton' data-id='${list.ListId}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }

        listsHTML += '</table>';

        document.getElementById("listDiv").innerHTML = listsHTML;

        /*let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editFruit);
        }*/

        /*let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteFruit);
        }*/

    });

    //document.getElementById("saveButton").addEventListener("click", saveEditFruit);
    //document.getElementById("cancelButton").addEventListener("click", cancelEditFruit);

}