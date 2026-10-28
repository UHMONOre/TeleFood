async function handleLogin() {
    const email = document.getElementById("emailInput").value.trim();
    const password = document.getElementById("passwordInput").value.trim();

    if (!email || !password) {
        showMessage("Please enter both email and password.", "alert-danger");
        return;
    }

    try {
        // 1. Send POST request to AuthController (/api/auth/login)
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        const resultText = await response.text();

        if (!response.ok) {
            // Matches AuthController 401 response: "Invalid email or password"
            showMessage(resultText || "Invalid email or password", "alert-danger");
            return;
        }

        // 2. Extract the userId number from: {"userId": X is successfully logged in.}
        const match = resultText.match(/\d+/);
        if (!match) {
            showMessage("Login succeeded, but could not read UserId.", "alert-warning");
            return;
        }

        const userId = parseInt(match[0], 10);

        // Save UserId in the browser so all future requests can send @RequestHeader("UserId")
        localStorage.setItem("userId", userId);

        showMessage("Login successful! Redirecting...", "alert-success");

        // 3. Check UserController (/user/all) to determine if user is Customer, Vendor, or Courier
        await redirectByUserRole(userId);

    } catch (error) {
        console.error("Login error:", error);
        showMessage("Could not connect to the server.", "alert-danger");
    }
}

async function redirectByUserRole(userId) {
    try {
        const response = await fetch("/user/" + userId + "/type");
        const userType = (await response.text()).trim();

        if (userType == "Customer") {
            window.location.href = "customer.html";
        } else if (userType == "Vendor") {
            window.location.href = "vendor.html";
        } else if (userType == "Courier") {
            window.location.href = "courier.html";
        } else {
            console.error("Unknown user role returned:", userType);
            showMessage("Could not determine user role.", "alert-danger");
        }
    } catch (error) {
        console.error("Role detection error:", err);
        showMessage("Failed to fetch user role.", "alert-danger");
    }
}

function showMessage(text, alertClass) {
    const messageBox = document.getElementById("messageBox");
    messageBox.textContent = text;
    messageBox.className = "alert " + alertClass;
}