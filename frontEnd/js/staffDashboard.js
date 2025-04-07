const API_BOOKS = "http://localhost:8080/api/v1/book";
const API_ORDERS = "http://localhost:8080/api/v1/orders";
const API_AUTHORS = "http://localhost:8080/api/v1/author";



function showSection(sectionId) {
    document.getElementById("book-management").style.display = sectionId === "book-management" ? "block" : "none";
    document.getElementById("author-management").style.display = sectionId === "author-management" ? "block" : "none";
    document.getElementById("order-management").style.display = sectionId === "order-management" ? "block" : "none";
}

$('#saveAuthor-btn').click((e) => {
    e.preventDefault();
    console.log("Saving author...");

    const name = $('#author-name').val().trim();
    const bio = $('#author-bio').val().trim();
    const country = $('#author-country').val().trim();

    if (!name || !bio || !country) {
        alert("Please fill in all fields!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/author/save",
        type: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        data: JSON.stringify({
            name: name,
            bio: bio,
            country: country
        }),
        success: (response) => {
            console.log("Author saved:", response);
            if (response.code === 200 || response.success) {
                alert("Author added successfully!");
                $('#author-form')[0].reset();
                fetchAuthors();
            } else {
                alert("Failed to save author. Please try again.");
            }
        },
        error: (err) => {
            console.error("Error saving author:", err);
            console.log(localStorage.getItem("token"));
            alert("Error saving author. Please check the console.");
        }
    });
});





async function fetchBooks() {
    $.ajax({
        url: "http://localhost:8080/api/v1/book/getAll",
        type: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        success: (response) => {
            console.log(response);

            if (response.code === 200 && response.data) {
                const tableBody = $('#author-table-body');
                tableBody.empty();

                response.data.forEach(author => {
                    tableBody.append(`
                        <tr>
                            <td>${author.id}</td>
                            <td>${author.name}</td>
                            <td>${author.bio}</td>
                            <td>${author.country}</td>
                            
                            
                        </tr>
                    `);
                });
            } else {
                console.warn("Failed to fetch customer data or invalid response format.");
            }
        },
        error: (err) => {
            console.log(err);
        }
    });
}

const fetchAuthors=() => {
   $.ajax({
         url:"http://localhost:8080/api/v1/author/getAll",
            type:"GET",
       headers: {
           "Content-Type": "application/json",
           "Authorization": `Bearer ${localStorage.getItem("token")}`
       },
            success:(response)=>{
                console.log(response);
                if (response.code === 200 && response.data){
                    const tableBody = $('#authorTableBody');

                    tableBody.empty();

                    response.data.forEach(author=>{
                        tableBody.append(`
                        <tr>
                            <td>${author.id}</td>
                            <td>${author.name}</td>
                            <td>${author.bio}</td>
                            <td>${author.country}</td>
                        </tr>
                        `);
                    });
                }else {
                    console.warn("Failed to fetch author data or invalid response format.");
                }
            },

   })
}

async function fetchOrders() {
    const response = await fetch(API_ORDERS);
    const orders = await response.json();
    const tableBody = document.getElementById("order-table-body");
    tableBody.innerHTML = "";
    orders.forEach((order, index) => {
        tableBody.innerHTML += `
            <tr>
                <td>${index + 1}</td>
                <td>${order.id}</td>
                <td>${order.customer.name}</td>
                <td>${order.status}</td>
                <td>$${order.totalPrice.toFixed(2)}</td>
                <td>
                    <button class="btn btn-info btn-sm" onclick="viewOrderDetails(${order.id})">View Details</button>
                </td>
            </tr>`;
    });
}

async function viewOrderDetails(orderId) {
    const response = await fetch(`${API_ORDERS}/${orderId}`);
    const order = await response.json();
    const orderDetails = `
        <div>
            <h4>Order ID: ${order.id}</h4>
            <p><strong>Customer:</strong> ${order.customer.name}</p>
            <p><strong>Status:</strong> ${order.status}</p>
            <p><strong>Total Price:</strong> $${order.totalPrice.toFixed(2)}</p>
            <h5>Items:</h5>
            <ul>
                ${order.items.map(item => `
                    <li>${item.book.title} - ${item.quantity} x $${item.book.price.toFixed(2)}</li>
                `).join('')}
            </ul>
        </div>
    `;
    const modal = new bootstrap.Modal(document.getElementById('order-details-modal'));
    document.getElementById('order-details-content').innerHTML = orderDetails;
    modal.show();
}

document.addEventListener("DOMContentLoaded", () => {
    fetchBooks();
    fetchAuthors();
    fetchOrders();
});
