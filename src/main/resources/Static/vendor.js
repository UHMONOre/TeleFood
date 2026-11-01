window.onload = function() {
    const vendorId = localStorage.getItem("UserId");

    if (!vendorId) {
        window.location.href = "index.html";
        return;
    }

    loadVendorName(vendorId);
    loadVendorOrders(vendorId);
    connectVendorWebSocket(vendorId);
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
                activeOrders.forEach((order) => {
                    activeContainer.appendChild(createOrderCard(order, "bg-primary"));
                });
            } else {
                noActiveText.classList.remove("d-none");
            }

            const completedOrders = info.completedOrders || [];
            if (completedOrders.length > 0) {
                noCompletedText.classList.add("d-none");
                completedOrders.forEach((order) => {
                    completedContainer.appendChild(createOrderCard(order, "bg-success"));
                });
            } else {
                noCompletedText.classList.remove("d-none");
            }

            const cancelledOrders = info.cancelledOrders || [];
            if (cancelledOrders.length > 0) {
                noCancelledText.classList.add("d-none");
                cancelledOrders.forEach((order) => {
                    completedContainer.appendChild(createOrderCard(order, "bg-danger"));
                });
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

            loadVendorOrders();
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

// Builds a clickable Bootstrap card for an individual Order
function createOrderCard(order, badgeColorClass) {
    const col = document.createElement("div");
    col.className = "col-md-6";

    col.innerHTML = `
        <div class="card border shadow-sm h-100" style="cursor: pointer;"
             onclick="window.location.href='order.html?orderId=${order.id}'">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="fw-bold mb-1">Order #${order.id}</h6>
                    <small class="text-muted">Total: €${order.total}</small>
                </div>
                <span class="badge ${badgeColorClass}">${order.status}</span>
            </div>
        </div>
    `;

    return col;
}