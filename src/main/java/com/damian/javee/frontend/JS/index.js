var commonValidation = false;
getAllCustomers();//Initial Load of the Customers.
getAllItems();//Initial Load of the Items.
getAllOrders();//Initial Load of the Orders.
$(".oidCard").css("display", "none");
var customerList;
var itemList;

function clearItemTable() {

    $("#iTable tbody").empty();


}

function clearCustomerTable() {
    $("#cTable tbody").empty();

}

function clearOrderTable() {
    $("#oTable tbody").empty();
}

function getAllOrders() {
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/order-manager?order_id=" + "fetchAll",
        async: true,
        method: "GET",
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                console.log(response)
                response.map((order) => {
                    console.log("item_qty after received " + order.item_qty)
                    console.log("item_price after received" + order.item_price)
                    console.log("total after received" + order.total)
                    /*Theres an issue in the item_price.(Since I used below approach.) */
                    let price = order.total / order.item_qty;
                    let row = "<tr>" +
                        "<td>" + order.order_id + "</td>" +
                        "<td>" + order.item_id + "</td>" +
                        "<td>" + order.customer_name + "</td>" +
                        "<td>" + order.item_name + "</td>" +
                        "<td>" + order.item_qty + "</td>" +
                        "<td>" + price + "</td>" +
                        "<td>" + order.total + "</td>" +
                        +"</tr>"
                    $("#oTable").append(row);


                })
            } else {
                swal("Error!", "Something went wrong while fetching all orders! : " + response.responseMesssage, "error")
            }


        },
        error: (e) => {
            swal("Error!", "An error occurred while requesting to the server! : " + e, "error");


        }


    });
}

function getAllCustomers() {
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/customer-manager?name=" + "fetchAll",
        method: "GET",
        async: true,
        dataType: "json",
        contentType: "application.json",
        success: ((response) => {
            console.log(response)
            if (response.status !== false) {
                customerList = response;
                response.map((customer) => {
                    let row = "<tr>" +
                        "<td>" + customer.customer_id + "</td>" +
                        "<td>" + customer.customer_name + "</td>" +
                        "<td>" + customer.customer_address + "</td>" +
                        "<td>" + customer.customer_email + "</td>" +
                        +"</tr>";

                    $("#cTable").append(row);

                });
            } else {
                swal("Error!", "An error occurred while fetching customers ! : " + response.responseMessage, "error")
            }


        }),
        error: ((e) => {
            console.log(e)

            swal("Error!", "An error occurred while requesting to the server! : " + e, "error");
        }),


    });


}

function getAllItems() {
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/item-manager?item_name=" + "fetchAll",
        method: "GET",
        async: true,
        dataType: "json",
        contentType: "application.json",
        success: ((response) => {
            if (response.staus !== false) {
                itemList = response;

                response.map((item) => {
                    let row = "<tr>" +
                        "<td>" + item.item_id + "</td>" +
                        "<td>" + item.item_name + "</td>" +
                        "<td>" + item.item_price + "</td>" +
                        "<td>" + item.item_qty + "</td>" +
                        +"</tr>";

                    $("#iTable").append(row);

                });
            } else {
                swal("Error!", "Something went wrong while fetching all items! : " + response.responseMessage, "error")
            }


        }),
        error: ((e) => {

            swal("Error!", "An error occurred while requesting to the server! : " + e, "error");
        }),


    });


}

$(document).ready(() => {
    if (window.innerWidth > 530) {
        Particles.init({
            selector: '.background',
            color: '#2f3128',
            maxParticles: 100,
            speed: 0.5,

        });
    }
    $("#customerContainer").css("display", "none");
    $("#itemContainer").css("display", "none");
    $("#orderContainer").css("display", "none");


    setInterval(() => {
        let d2 = new Date();
        $(".time").text(d2.getHours() + " : " + d2.getMinutes() + " : " + d2.getSeconds());
    }, 100)


});
setOrderDetails();//Initial Load of the Orders.
function setOrderDetails() {


    setTimeout(() => {
        customerList.map((c) => {

            $("#customerNameListOptions").append(`<option value="${c.customer_name}">${c.customer_name}</option>`);

        });
        itemList.map((i) => {

            $("#itemNameListOptions").append(`<option value="${i.item_name}">${i.item_name}</option>`);

        });

    }, 6000)


}

function changeTitle(title) {
    document.title = title;

}

$("#customerManager").on("click", () => {
    $(".time").css("display", "none");
    $("#customerContainer").css("display", "flex");
    $("#customerContainer").addClass("animate__animated animate__zoomInDown");
    $("#itemContainer").css("display", "none");
    $("#orderContainer").css("display", "none");


});
$("#orderManager").on("click", () => {
    $(".time").css("display", "none");
    $("#itemContainer").css("display", "none");
    $("#customerContainer").css("display", "none");
    $("#orderContainer").css("display", "flex");
    $("#orderContainer").addClass("animate__animated animate__zoomInDown");


});
$("#itemManager").on("click", () => {
    $(".time").css("display", "none");
    $("#customerContainer").css("display", "none");
    $("#orderContainer").css("display", "none");
    $("#itemContainer").css("display", "flex");
    $("#itemContainer").addClass("animate__animated animate__zoomInDown");


});

$("#addItemButton").on("click", () => {
    const fields = ["#iID", "#iName", "#iPrice", "#iQty"];
    validator(fields, null, "#itemForm")
    if (commonValidation) {
        let item = {
            "item_id": $("#iID").val(),
            "item_name": $("#iName").val(),
            "item_price": $("#iPrice").val(),
            "item_qty": $("#iQty").val(),

        }
        let itemJSON = JSON.stringify(item);
        $.ajax({
            url: "http://localhost:8080/OrderCloud/api/v1/item-manager",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: itemJSON,
            async: true,
            dataType: "json",
            contentType: "application.json",
            success: (response) => {
                if (response.status !== false) {
                    clearItemTable();
                    getAllItems();
                    $("#cfItems").click();
                    swal("Done!", "Item Saved Successfully!", "success");
                } else {
                    swal("Error!", "Something went wrong while saving the item! : " + response.responseMessage, "error")
                }


            },
            error: (e) => {
                swal("Error!", "An error occurred while requesting to the server! : " + e, "error");
            }


        });
    }

});

$("#addCustomerButton").on("click", () => {
    const fields = ["#cID", "#cName", "#cAddress"];
    validator(fields, "#cEmail", "#form")
    setTimeout(() => {
        if (commonValidation === true) {
            var customer = {
                "customer_id": $("#cID").val(),
                "customer_name": $("#cName").val(),
                "customer_address": $("#cAddress").val(),
                "customer_email": $("#cEmail").val(),
            }

            let customerJSON = JSON.stringify(customer);
            $.ajax({
                url: "http://localhost:8080/OrderCloud/api/v1/customer-manager",
                type: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                data: customerJSON,
                async: true,
                dataType: "json",
                contentType: "application.json",
                success: (response) => {
                    if (response !== false) {
                        clearCustomerTable()
                        getAllCustomers();
                        $("#cfAdd").click();//Clearing the fields.
                        swal("Done!", "Customer Saved Successfully!", "success");
                    } else {
                        swal("Error!", "An error occurred while saving the customer! : " + response.responseMessage, "error");
                    }

                },
                error: (e) => {
                    swal("Error!", "An Error occurred  : " + e, "error");
                }


            });
        } else {
            swal("Error!", "Validation Failed!", "error");
        }

    }, 1000)


});


function validator(fields, email, formId) {
    console.log("validator called");
    const validation = new JustValidate(formId);
    fields.map((field) => {
        console.log("fields array running");
        console.log(field);
        validation
            .addField(field, [
                {
                    rule: 'minLength',
                    value: 3,
                },
                {
                    rule: 'maxLength',
                    value: 30,
                }

            ])
        if (email) {
            validation.addField(email, [
                {
                    rule: 'required',
                    errorMessage: 'Email is required.',
                },
                {
                    rule: 'email',
                    errorMessage: 'Email is invalid!',
                },
            ]);


        }

    })


    validation.onSuccess(() => {
        console.log("Validation Success");
        commonValidation = true;

    })
    validation.onFail(() => {
        console.log("Validation Failed");
        commonValidation = false;
    })


}

$("#searchItemButton").on("click", () => {
    let item_name = $("#iName").val();
    if (!item_name) {
        return swal("Error!", "Item name cannot be empty!", "error")
    }
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/item-manager?item_name=" + item_name,
        method: "GET",
        async: true,
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {

                $("#iID").val(response.item_id);
                $("#iName").val(response.item_name);
                $("#iPrice").val(response.item_price);
                $("#iQty").val(response.item_qty);
                $("#iID").attr("disabled", "disabled");

            } else {
                swal("Error!", "Something went wrong while fetching the item! :" + response.responseMessage, "error")
            }


        },
        error: (e) => {
            swal("Error!", "Something went wrong while requesting to the server! : " + e, "error");
        }


    });


});

$("#searchCustomerButton").on("click", () => {
    let name = $("#cName").val();
    if (!$("#cName").val()) {
        return swal("Error!", "Customer name cannot be empty!", "error")
    }
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/customer-manager?name=" + name,
        method: "GET",
        async: true,
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                $("#cID").val(response.customer_id);
                $("#cName").val(response.customer_name);
                $("#cAddress").val(response.customer_address);
                $("#cEmail").val(response.customer_email);
                /*CID is disabled.*/
                $("#cID").attr("disabled", "disabled");
            } else {
                swal("Customer Not found!", response.responseMessage, "error")
            }


        },
        error: (jqXHR, textStatus, errorThrown) => {
            swal("Error!", "An error occurred while sending the request!" + errorThrown, "error");
        }


    })


});
$("#dashboard").on("click", () => {
    $("#customerContainer").css("display", "none");
    $(".time").css("display", "flex");


});

$("#updateCustomerButton").on("click", () => {
    if (!$("#cID").val()) {
        return swal("Error!", "Search a customer first!", "error")
    }
    var customer = {
        "customer_id": $("#cID").val(),
        "customer_name": $("#cName").val(),
        "customer_address": $("#cAddress").val(),
        "customer_email": $("#cEmail").val(),
    }
    let customerJSON = JSON.stringify(customer);
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/customer-manager",
        method: "PUT",
        async: true,
        data: customerJSON,
        headers: {
            "Content-Type": "application/json"
        },
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                swal("Done!", "Customer Successfully updated!", "success")
                clearCustomerTable();
                getAllCustomers();
                $("#cfAdd").click();
            } else {
                swal("Error!", "Something went wrong while updating the customer!" + response.responseMessage, "error")
            }


        },
        error: (error) => {
            swal("Error!", "Something went wrong while performing the request!" + error, "error")
        }


    });


});
$("#deleteCustomerButton").on("click", () => {
    if (!$("#cID").val()) {
        return swal("Error!", "Search a customer first!", "error")
    }
    let id = $("#cID").val();
    console.log(id)
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/customer-manager?cId=" + id,
        method: "DELETE",
        async: true,
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                swal("Done!", "Customer successfully deleted!", "success")
                clearCustomerTable();
                getAllCustomers();
                $("#cfAdd").click();
            } else {
                swal("Error!", "Something went wrong while deleting customer! : " + response.responseMessage, "error")
            }
        },
        error: (error) => {
            swal("Error!", "Something went wrong when performing request to the server! : " + e, "error")
        }


    });


});
$("#cfAdd").on("click", () => {
    $("#cID").val("");
    $("#cName").val("");
    $("#cAddress").val("");
    $("#cEmail").val("");
    $("#cID").removeAttr("disabled");


});
$("#cfItems").on("click", () => {

    $("#iID").val("");
    $("#iName").val("");
    $("#iPrice").val("");
    $("#iQty").val("");
    $("#iID").removeAttr("disabled");

});

$("#updateItemButton").on("click", () => {
    if (!$("#iID").val()) {
        return swal("Error!", "Search an item first!", "error")
    }
    let item = {
        item_id: $("#iID").val(),
        item_name: $("#iName").val(),
        item_price: $("#iPrice").val(),
        item_qty: $("#iQty").val()


    }
    let itemJSON = JSON.stringify(item);
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/item-manager",
        method: "PUT",
        async: true,
        headers: {
            "Content-Type": "application/json"
        },
        data: itemJSON,
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                clearItemTable();
                getAllItems();
                getAllOrders();
                $("#cfItems").click();
                swal("Done!", "Item Updated Successfully!", "success");
            } else {
                swal("Error!", "An error occurred while updating the item! : " + response.responseMessage, "error");
            }

        },
        error: (e) => {
            swal("Error!", "An error occurred while requesting to the server! : " + e, "error");
        }


    })


});

$("#deleteItemButton").on("click", () => {
    if (!$("#iID").val()) {
        return swal("Error!", "Search an item first!", "error")
    }
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/item-manager?item_id=" + $("#iID").val(),
        method: "DELETE",
        async: true,
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                clearItemTable();
                getAllItems();
                $("#cfItems").click();
                swal("Done!", "Item Deleted Successfully!", "success");
            } else {
                swal("Error!", "Something went wrong while deleting the item! : " + response.responseMessage, "error");
            }

        },
        error: (e) => {
            swal("Error!", "An error occurred while requesting to the server ! : " + e, "error");
        }


    })


});

$("#customerName").on("mouseleave", () => {


    customerList.map((c) => {
        if (c.customer_name === $("#customerName").val()) {
            $("#customerId").val(c.customer_id);
        }


    });


});
$("#itemName").on("mouseleave", () => {
    itemList.map((i) => {
        if (i.item_name === $("#itemName").val()) {
            $("#itemId").val(i.item_id);
            $("#itemPrice").val(i.item_price);


        }


    })

})


$("#itemQty").on("mouseleave", () => {
    itemList.map((i) => {
        if (i.item_name === $("#itemName").val()) {
            if ($("#itemQty").val() > i.item_qty) {
                swal("Error!", "We only have " + i.item_qty + " " + i.item_name + " in stock.", "error");
                $("#itemQty").val("");
            } else {
                let qty = $("#itemQty").val();
                let price = $("#itemPrice").val();
                $("#total").val(qty * price);


            }


        }
    });


});
$("#cfOrders").on("click", () => {
    $("#orderId").val("");
    $("#itemId").val("");
    $("#customerName").val("");
    $("#itemName").val("");
    $("#itemQty").val("");
    $("#itemPrice").val("");
    $("#total").val("");
    $("#orderId").removeAttr("disabled");
    $("#itemId").removeAttr("disabled");
    $("#itemPrice").removeAttr("disabled");
    $("#customerId").val("");


});

function generateOrderId() {
    var currentDate = new Date();
    var timestamp = currentDate.getTime();
    var randomDigits = Math.floor(Math.random() * 10000); // Generate a random 4-digit number

    var orderId = "ORDER" + timestamp + randomDigits;
    $("#orderId").val(orderId)
}


$("#addOrderButton").on("click", () => {
    var co;
    /*Getting the customer details to set to the order!*/
    customerList.map((customer) => {
        console.log(customer)
        console.log($("#customerId").val())
        if ($("#customerId").val() === customer.customer_id) {
            console.log(customer.customer_id)
            console.log("condition is true")
            co = customer;


        } else {
            console.log("condition is false")
        }
    })
    generateOrderId();
    var order = {
        order_id: $("#orderId").val(),
        item_id: $("#itemId").val(),
        customer_name: $("#customerName").val(),
        item_name: $("#itemName").val(),
        item_qty: $("#itemQty").val(),
        item_price: $("#itemPrice").val(),
        total: $("#total").val(),
        customer_id: co

    }
    let orderJSON = JSON.stringify(order);
    $.ajax({
        url: "http://localhost:8080/OrderCloud/api/v1/order-manager",
        method: "POST",
        async: true,
        headers: {
            "Content-Type": "application/json"
        },
        data: orderJSON,
        dataType: "json",
        contentType: "application.json",
        success: (response) => {
            if (response.status !== false) {
                swal("Done!", "Order Added Successfully!", "success");
                $("#cfOrders").click();
                clearItemTable();
                clearOrderTable();
                getAllItems();
                getAllOrders();

            } else {
                swal("error!", "An error occurred while adding the order! : " + response.responseMessage, "error");
            }


        }, error: (e) => {
            swal("error!", "An error occurred while requesting to the server! : " + e, "error");


        }


    })


});


$(".oidCard").on("click", "#closeButton", () => {
    $(".oidCard").css("display", "none");
});

$("#oTable").on("click", "tr", (event) => {

    let order_id = $(event.currentTarget).find("td:eq(0)").text();
    let item_id = $(event.currentTarget).find("td:eq(1)").text();
    let customer_name = $(event.currentTarget).find("td:eq(2)").text();
    let item_name = $(event.currentTarget).find("td:eq(3)").text();
    let item_qty = $(event.currentTarget).find("td:eq(4)").text();
    let item_price = $(event.currentTarget).find("td:eq(5)").text();
    let total = $(event.currentTarget).find("td:eq(6)").text();
    $("#orderId").val(order_id);
    $("#itemId").val(item_id);
    $("#customerName").val(customer_name);
    $("#itemName").val(item_name);
    $("#itemQty").val(item_qty);
    $("#itemPrice").val(item_price);
    $("#total").val(total);


    $("#orderId").attr("disabled", "disabled");
    $("#itemId").attr("disabled", "disabled");
    $("#itemPrice").attr("disabled", "disabled");

    customerList.map((customer) => {
        if (customer_name === $("#customerName").val()) {
            return $("#customerId").val(customer.customer_id)
        }

    })

});
$("#updateOrderButton").on("click", () => {
    var customerObject = null;
    if (!$("#orderId").val()) {
        swal("Error.", "Select a row to update!", "error");
    } else {
        /*Setting the customer object to the order object.*/
        customerList.map((customer) => {
            console.log(customer)
            console.log($("#customerId").val())
            if ($("#customerId").val() === customer.customer_id) {
                console.log(customer.customer_id)
                console.log("condition is true")
                customerObject = customer;


            } else {
                console.log("condition is false")
            }
        })
        let updatedOrder = {
            order_id: $("#orderId").val(),
            item_id: $("#itemId").val(),
            customer_name: $("#customerName").val(),
            item_name: $("#itemName").val(),
            item_qty: $("#itemQty").val(),
            item_price: $("#itemPrice").val(),
            total: $("#total").val(),
            customer_id: customerObject

        }
        let orderJSON = JSON.stringify(updatedOrder);
        $.ajax({
            url: "http://localhost:8080/OrderCloud/api/v1/order-manager",
            method: "PUT",
            async: true,
            headers: {
                "Content-Type": "application/json"
            },
            data: orderJSON,
            dataType: "json",
            contentType: "application.json",
            success: (response) => {
                if (response.status !== false) {
                    console.log(response);
                    $("#cfOrders").click();
                    clearItemTable();
                    clearOrderTable();
                    getAllItems();
                    getAllOrders();
                    swal("Done!", "Order Updated Successfully!", "success");
                } else {
                    swal("Error!", "Something went wrong while updating the order! : " + response.responseMessage, "error")
                }


            },
            error: (e) => {
                swal("Error!", "An error occurred while requesting to the server! : " + e, "error");

            }


        })


    }


});

$("#deleteOrderButton").on("click", () => {
    if (!$("#orderId").val()) {
        swal("Error!", "Select a row to delete!", "error");
    } else {

        $.ajax({
            url: "http://localhost:8080/OrderCloud/api/v1/order-manager?order_id=" + $("#orderId").val(),
            method: "DELETE",
            async: true,
            dataType: "json",
            contentType: "application.json",
            success: (response) => {
                if (response.status !== false) {
                    swal("Done.", "Order successfully deleted!", "success")
                    $("#cfOrders").click();
                    clearItemTable();
                    clearOrderTable();
                    getAllItems();
                    getAllOrders();
                } else {
                    swal("Error!", "An error occurred while deleting the order! : " + response.responseMessage, "error")
                }

            },
            error: (e) => {
                swal("Error!", "An error occurred while requesting to the server! : " + e, "error");
            }


        });
    }


});


