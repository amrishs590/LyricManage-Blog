package com.app.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0
        res.setDateHeader("Expires", 0); // Proxies


        // ✅ Allow login & unauthorized pages without filtering
        if (path.endsWith("login.jsp") || path.endsWith("unauthorized.jsp") || path.contains("LoginServlet")) {
            chain.doFilter(request, response);
            return;
        }

        // Get user role from session
        String role = (session != null) ? (String) session.getAttribute("role") : null;
//        System.out.println(role);
        if (role == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // 🚫 User cannot access admin-side
        if ("participant".equalsIgnoreCase(role) && (
                path.contains("LyricAdminServlet") ||
                path.contains("EditParticipantServlet") ||
                path.contains("NewUser.jsp") ||
                path.contains("NewAdmin.jsp"))) {
            res.sendRedirect("unauthorized.jsp");
            return;
        }

        // 🚫 Admin cannot access user-side
        if ("admin".equalsIgnoreCase(role) && (
                path.contains("UserDashboard"))) {
            res.sendRedirect("unauthorized.jsp");
            return;
        }

        // ✅ Allow if everything is fine
        chain.doFilter(request, response);
    }
}
