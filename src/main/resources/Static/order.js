window.onload = async function() {
    const vendorId = localStorage.getItem("UserId");

    if (!vendorId) {
        window.location.href = 'index.html';
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

    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get("orderId");

    if (!orderId) {
        window.location.href = "index.html";
        return;
    }



    loadOrderDetails();
}

async function loadOrderDetails() {
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

function showMessage(text, alertClass) {
    const messageBox = document.getElementById("messageBox");
    messageBox.textContent = text;
    messageBox.className = "alert " + alertClass;
}