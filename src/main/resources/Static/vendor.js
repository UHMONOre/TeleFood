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