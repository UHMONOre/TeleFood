package com.tele.telefood;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. Get the ID string from the header
        String userIdString = request.getHeader("User-ID");

        // If it is missing, block them
        if (userIdString == null || userIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing User-ID header.");
            return false;
        }

        try {
            // 2. Translate the String into a Long number
            Integer userId = Integer.parseInt(userIdString);

            // 3. Find the user by ID (instead of Email)
            User user = userRepository.findById(userId).orElse(null);

            // 4. CHECK THE FLAG
            if (user == null || !user.getLoggedInFlag()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are logged out. Please log in again.");
                return false;
            }

            // The flag is true! Let them through.
            return true;

        } catch (NumberFormatException e) {
            // This catches the error if someone sends text like "hello" instead of a number
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User-ID format.");
            return false;
        }
    }
}
