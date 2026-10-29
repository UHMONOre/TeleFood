async function toggleRoleFields() {
    const userType = document.getElementById("roleSelect").value;

    const vendorDiv = document.getElementById("vendorGroup");
    const tinDiv  = document.getElementById("tinGroup");

    if (userType === "customer") {
        vendorDiv.classList.add("d-none");
        tinDiv.classList.add("d-none");
    } else if (userType === "vendor") {
        vendorDiv.classList.remove("d-none");
        tinDiv.classList.remove("d-none");
    } else {
        vendorDiv.classList.add("d-none");
        tinDiv.classList.remove("d-none");
    }
}

async function handleRegister() {
    const userType = document.getElementById("roleSelect").value;
    const firstName = document.getElementById("firstNameInput").value.trim();
    const lastName = document.getElementById("lastNameInput").value.trim();
    const email = document.getElementById("emailInput").value.trim();
    const password = document.getElementById("passwordInput").value.trim();
    const phoneNumber = document.getElementById("phoneInput").value.trim();
    const country = document.getElementById("countryInput").value.trim();
    const city = document.getElementById("cityInput").value.trim();
    const address = document.getElementById("addressInput").value.trim();

    let requestBody = {
        email: email,
        password: password,
        firstName: firstName, // Fixed typo: was "fistName"
        lastName: lastName,
        country: country,
        city: city,
        address: address,
        phoneNumber: phoneNumber
    };

    if (!userType || !firstName || !lastName || !email || !password || !phoneNumber || !country || !city || !address || !phoneNumber || !country) {
        showMessage("Please fulfill all available fields.", "alert-danger");
        return;
    }

    if (userType === "courier") {
        const tin = document.getElementById("tinInput").value.trim();

        if (!tin) {
            showMessage("Please fulfill all available fields.", "alert-danger");
            return;
        }

        requestBody.tin = tin;
    } else if (userType === "vendor") {
        const tin = document.getElementById("tinInput").value.trim();
        const vendorName = document.getElementById("vendorNameInput").value.trim();
        const description = document.getElementById("descriptionInput").value.trim();

        if (!tin || !vendorName) {
            showMessage("Please fulfill all available fields.", "alert-danger");
            return;
        }

        requestBody.tin = tin;
        requestBody.name = vendorName;
        requestBody.description = description;
    }

    try {
        const response = await fetch("/api/auth/register/" + userType, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestBody)
        });

        const resultText = await response.text();

        if (!response.ok) {
            showMessage(resultText || "Registration failed. Please check your details.", "alert-danger");
            return;
        }

        showMessage("Register successful! Redirecting...", "alert-success");

        setTimeout(() => {
            window.location.href = "index.html";
        }, 1200);

    } catch (error) {
        console.error("Registration error:", error);
        showMessage("Could not connect to the server.", "alert-danger");
    }
}

function showMessage(text, alertClass) {
    const messageBox = document.getElementById("messageBox");
    messageBox.textContent = text;
    messageBox.className = "alert " + alertClass;
}