package com.tkht.servlet;

import com.tkht.dao.ServiceDao;
import com.tkht.model.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ServiceServlet extends HttpServlet {
    private final ServiceDao serviceDao = new ServiceDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            resp.sendRedirect(req.getContextPath() + "/view/customerLogin.jsp");
            return;
        }

        String servletPath = req.getServletPath();

        try {
            if ("/GdTimKiem".equals(servletPath)) {
                handleSearch(req, resp);
                return;
            }

        if ("/searchService".equals(servletPath)) {
            handleDetail(req, resp);
            return;
        }

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            throw new ServletException("Failed to query the database", e);
        }
    }

    private void handleSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String keyword = req.getParameter("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {
            req.setAttribute("services", serviceDao.searchByName(keyword.trim()));
        }

        req.setAttribute("keyword", keyword);
        req.setAttribute("page", "search");
        req.getRequestDispatcher("/view/GdChinhKh.jsp").forward(req, resp);
    }

    private void handleDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String id = req.getParameter("id");
        if (id != null) {
            id = id.trim();
        }

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/view/GdChinhKh.jsp?page=search");
            return;
        }

        Service service = serviceDao.findById(id);
        if (service == null) {
            req.setAttribute("error", "Không tìm thấy dịch vụ với ID đã cung cấp.");
            resp.sendRedirect(req.getContextPath() + "/view/GdChinhKh.jsp?page=search");
            return;
        }

        req.setAttribute("service", service);
        req.setAttribute("page", "detail");
        req.getRequestDispatcher("/view/GdChinhKh.jsp").forward(req, resp);
    }
}

