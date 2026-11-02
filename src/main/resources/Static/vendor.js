window.onload = async function() {
    const vendorId = localStorage.getItem("UserId");

    if (!vendorId) {
        window.location.href = "index.html";
        return;
    }

    try {
        const response = await fetch("/vendor/info", {
            method: "GET",
            headers: {
                "UserId": vendorId
            }
        });

        if (!response.ok) {
            localStorage.removeItem("UserId");
            window.location.href = "index.html";
            return;
        }
    } catch (error) {
        console.error("Error fetching vendor", error);
        localStorage.removeItem("UserId");
        window.location.href = "index.html";
        return;
    }

    loadVendorName(vendorId);
    loadVendorOrders(vendorId);
    connectVendorWebSocket(vendorId);
}

async function handleLogout() {
    const vendorId = localStorage.getItem("UserId");

    if (!vendorId) {
        window.location.href = "index.html";
    }

    try {
        const response = await fetch("/api/auth/logout", {
            method: "POST",
            headers: {
                "UserId": vendorId
            }
        });

        if (response.ok) {
            showMessage("Logout successful! Redirecting...", "alert-success");

            localStorage.removeItem("UserId");

            setTimeout(() => {
                window.location.href = "index.html";
            }, 1200);
        }
    } catch (error) {
        console.error("Could not logout", error);
    }
}

async function loadVendorName(vendorId) {
    try {
        const response = await fetch("/vendor/info", {
            method: "GET",
            headers: {
                "UserId": vendorId
            }
        });

        if (response.ok) {
            const info = await response.json();
            document.getElementById("vendorNameDisplay").textContent = info.name;
        }
    } catch (error) {
        console.error("Error loading vendor info:", error);
    }
}

async function loadVendorOrders(vendorId) {
    if (!vendorId) {
        vendorId = localStorage.getItem("UserId");
    }

    try {
        const response = await fetch("/order/all/info", {
            method: "GET",
            headers: {
                "UserId": vendorId
            }
        });

        if (response.ok) {
            const info = await response.json();

            const activeContainer = document.getElementById("activeOrdersContainer");
            const completedContainer = document.getElementById("completedOrdersContainer");
            const cancelledContainer = document.getElementById("cancelledOrdersContainer");

            const noActiveText = document.getElementById("noActiveOrdersText");
            const noCompletedText = document.getElementById("noCompletedOrdersText");
            const noCancelledText = document.getElementById("noCancelledOrdersText");

            activeContainer.innerHTML = "";
            completedContainer.innerHTML = "";
            cancelledContainer.innerHTML = "";

            const activeOrders = info.activeOrders || [];
            if (activeOrders.length > 0) {
                noActiveText.classList.add("d-none");
                for (const order of activeOrders) {
                    const card = await createOrderCard(order, vendorId, "bg-primary");
                    if (card) {
                        activeContainer.appendChild(card);
                    }
                }
            } else {
                noActiveText.classList.remove("d-none");
            }

            const completedOrders = info.completedOrders || [];
            if (completedOrders.length > 0) {
                noCompletedText.classList.add("d-none");
                for (const order of completedOrders) {
                    const card = await createOrderCard(order, vendorId, "bg-success");
                    if (card) {
                        completedContainer.appendChild(card);
                    }
                }
            } else {
                noCompletedText.classList.remove("d-none");
            }

            const cancelledOrders = info.cancelledOrders || [];
            if (cancelledOrders.length > 0) {
                noCancelledText.classList.add("d-none");
                for (const order of cancelledOrders) {
                    const card = await createOrderCard(order, vendorId, "bg-danger");
                    if (card) {
                        cancelledContainer.appendChild(card);
                    }
                }
            } else {
                noCancelledText.classList.remove("d-none");
            }
        }
    } catch (error) {
        console.error("Error loading orders for vendor:", error);
    }
}

let stompClient = null;

function connectVendorWebSocket(vendorId) {
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        stompClient.subscribe("/topic/vendor/" + vendorId, function (messageOutput) {
            const notification = JSON.parse(messageOutput.body);

            if (notification.total !== undefined) {
                showWsNotification(
                    "New Order #" + notification.orderId + " received! Total: €" + notification.total
                );
            } else if (notification.message !== undefined) {
                showWsNotification(notification.message);
            }

            setTimeout(() => {
                loadVendorOrders();
            }, 300);
        });
    });
}

function showWsNotification(text) {
    const box = document.getElementById("wsNotificationBox");
    const textSpan = document.getElementById("wsNotificationText");
    textSpan.textContent = text;
    box.classList.remove("d-none");
}

function hideNotification() {
    document.getElementById("wsNotificationBox").classList.add("d-none");
}

async function createOrderCard(order, vendorId, badgeColorClass) {
    const col = document.createElement("div");
    col.className = "col-md-6";

    try {
        const response = await fetch("/order/" + order.id + "/customer/info", {
            method: "GET",
            headers: {
                "UserId": vendorId
            }
        });

        if (response.ok) {
            const customerInfo = await response.json();
            const firstName = customerInfo.firstName;
            const lastName = customerInfo.lastName;
            const address = customerInfo.address;
            const deliveryStatus = order.status;
            const deliveryMethod = order.method;

            col.innerHTML = `
                <div class="card border shadow-sm h-100" style="cursor: pointer;"
                    onclick="window.location.href='order.html?orderId=${order.id}'">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <!-- Left Side: Customer & Price -->
                        <div>
                            <h6 class="fw-bold mb-1">${firstName} ${lastName}</h6>
                            <div class="text-muted small mb-2">${address}</div>
                            <div class="fw-semibold small">Total: €${order.total}</div>
                        </div>
                        
                        <!-- Right Side: Status & Delivery Method Bars -->
                        <div class="d-flex flex-column align-items-end gap-2">
                            <div class="d-flex align-items-center">
                                <small class="text-muted me-2 fw-bold">Status:</small>
                                <span class="badge ${badgeColorClass}" style="width: 85px;">${deliveryStatus}</span>
                            </div>
                            <div class="d-flex align-items-center">
                                <small class="text-muted me-2 fw-bold">Method:</small>
                                <span class="badge bg-dark" style="width: 85px;">${deliveryMethod}</span>
                            </div>
                        </div>
                    </div>
                </div>
            `;

            return col;
        }
    } catch (error) {
        console.error("Error creating order cards", error);
    }

    return null;
}

function showMessage(text, alertClass) {
    const messageBox = document.getElementById("messageBox");
    messageBox.textContent = text;
    messageBox.className = "alert " + alertClass;
}