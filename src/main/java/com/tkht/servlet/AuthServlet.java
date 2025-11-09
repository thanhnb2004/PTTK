package com.tkht.servlet;

import com.tkht.dao.CustomerDao;
import com.tkht.dao.MemberDao;
import com.tkht.model.Customer;
import com.tkht.model.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AuthServlet extends HttpServlet {
    private final CustomerDao customerDao = new CustomerDao();
    private final MemberDao memberDao = new MemberDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect(req.getContextPath() + "/view/customerLogin.jsp");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
        if ("customer".equalsIgnoreCase(role)) {
            handleCustomerLogin(req, resp);
            return;
        }
        if ("staff".equalsIgnoreCase(role)) {
            handleStaffLogin(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported role");
    }

    private void handleCustomerLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("/view/customerLogin.jsp").forward(req, resp);
            return;
        }

        try {
            Customer customer = customerDao.findByEmail(email);
            if (customer == null || customer.getPassword() == null || !customer.getPassword().equals(password)) {
                req.setAttribute("error", "Invalid email or password.");
                req.getRequestDispatcher("/view/customerLogin.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("userRole", "customer");
            session.setAttribute("customer", customer);
            resp.sendRedirect(req.getContextPath() + "/view/GdChinhKh.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void handleStaffLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("/view/staffLogin.jsp").forward(req, resp);
            return;
        }

        try {
            Member member = memberDao.findByUsername(username);
            if (member == null || member.getPassword() == null || !member.getPassword().equals(password) || member.getStaffId() == null) {
                req.setAttribute("error", "Invalid staff credentials.");
                req.getRequestDispatcher("/view/staffLogin.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("userRole", "staff");
            session.setAttribute("staffMember", member);
            resp.sendRedirect(req.getContextPath() + "/view/GdChinhNhanVienKho.jsp?page=menu");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

