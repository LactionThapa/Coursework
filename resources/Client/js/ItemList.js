function pageLoad() {
    let qs = getQueryStringParameters();
    let id = Number(qs["id"]);
    let ItemListHTML = `<table style="width:100%">` +
        '<tr>' +
        '<th style="text-align: left;">ItemName</th>' +
        '<th style="text-align: left;">Quantity</th>' +
        '<th style="text-align: left;">Marker</th>' +
        '<th style="text-align: left;" class="last">Options</th>' +
        '</tr>';

    fetch('/ListItem/get/{id}' + id, {method: 'get'}
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

        ItemListHTML += '</table>';

        document.getElementById("listDiv").innerHTML = ItemListHTML;
    });
}

/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

function getQueryStringParameters() {
    let params = [];
    let q = document.URL.split('?')[1];
    if (q !== undefined) {
        q = q.split('&');
        for (let i = 0; i < q.length; i++) {
            let bits = q[i].split('=');
            params[bits[0]] = bits[1];
        }
    }
    return params;
}