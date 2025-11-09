package com.tkht.servlet;

import com.tkht.dao.CustomerDao;
import com.tkht.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class CustomerServlet extends HttpServlet {
    private final CustomerDao customerDao = new CustomerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (servletPath == null) {
            servletPath = "";
        }

        switch (servletPath) {
            case "":
            case "/":
                req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
                break;
            case "/customers":
                handleCustomerEntry(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if ("/customers".equals(servletPath)) {
            String action = req.getParameter("action");
            if ("signup".equalsIgnoreCase(action)) {
                handleCustomerSignup(req, resp);
                return;
            }
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported action");
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void handleCustomerSignup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        req.setAttribute("formName", name);
        req.setAttribute("formEmail", email);
        req.setAttribute("formPhone", phoneNumber);

        if (email == null || email.isEmpty() || phoneNumber == null || phoneNumber.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Email, phone number and password are required.");
            req.getRequestDispatcher("/view/customerSignup.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("/view/customerSignup.jsp").forward(req, resp);
            return;
        }

        try {
            Customer existingCustomer = customerDao.findByEmail(email);
            if (existingCustomer != null) {
                req.setAttribute("error", "Email already exists.");
                req.getRequestDispatcher("/view/customerSignup.jsp").forward(req, resp);
                return;
            }

            String customerId = UUID.randomUUID().toString();
            Customer customer = new Customer(
                customerId,
                name,
                email,
                phoneNumber,
                password,
                null,
                null,
                null
            );

            customerDao.create(customer);
            req.setAttribute("message", "Signup successful. You can now log in.");
            req.getRequestDispatcher("/view/customerLogin.jsp").forward(req, resp);
            return;
        } catch (SQLException e) {
            req.setAttribute("error", "Failed to create customer account.");
        }

        req.getRequestDispatcher("/view/customerSignup.jsp").forward(req, resp);
    }

    private void handleCustomerEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("signup".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("/view/customerSignup.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/view/customerLogin.jsp");
    }
}


