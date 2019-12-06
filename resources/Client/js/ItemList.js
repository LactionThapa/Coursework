function pageLoad(event) {
    const id = event.target.getAttribute("data-id");
    let ItemListHTML = `<table style="width:100%">` +
        '<tr>' +
        '<th style="text-align: left;">ItemName</th>' +
        '<th style="text-align: left;">Quantity</th>' +
        '<th style="text-align: left;">Marker</th>' +
        '<th style="text-align: left;" class="last">Options</th>' +
        '</tr>';

    fetch('/ListItem/get/{id}', {method: 'get'}
    ).then(response => response.json()
    ).then(lists => {

        for (let list of lists) {

            listsHTML += `<tr>` +
                `<td>${list.ItemName}</td>` +
                `<td>${list.Quantity}</td>` +
                `<td>${list.MarkedUserID}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${list.ItemID}'>Edit</button>` +
                `<button class='deleteButton' data-id='${list.ItemID}'>Delete</button>` +
                `</td>` +
                `</tr>`;

        }

        listsHTML += '</table>';

        document.getElementById("listDiv").innerHTML = listsHTML;
    });
}