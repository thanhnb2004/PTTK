package com.tkht.servlet;

import com.tkht.dao.SupplierDao;
import com.tkht.model.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierServlet extends HttpServlet {
    private final SupplierDao supplierDao = new SupplierDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        try {
            if ("search".equalsIgnoreCase(action)) {
                handleSearch(req, resp);
            } else {
                handleList(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Supplier> suppliers = supplierDao.findAll();
        req.setAttribute("suppliers", suppliers);
        
        // Nếu là include request (từ JSP), chỉ set attribute và return
        // Không forward để tránh lỗi
        // Các JSP sẽ tự đọc attribute từ request
    }

    private void handleSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String keyword = req.getParameter("keyword");
        List<Supplier> allSuppliers = supplierDao.findAll();
        List<Supplier> filteredSuppliers = new ArrayList<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase().trim();
            for (Supplier supplier : allSuppliers) {
                if ((supplier.getName() != null && supplier.getName().toLowerCase().contains(lowerKeyword)) ||
                    (supplier.getAddress() != null && supplier.getAddress().toLowerCase().contains(lowerKeyword)) ||
                    (supplier.getId() != null && supplier.getId().toLowerCase().contains(lowerKeyword))) {
                    filteredSuppliers.add(supplier);
                }
            }
        } else {
            filteredSuppliers = allSuppliers;
        }
        
        req.setAttribute("suppliers", filteredSuppliers);
        req.setAttribute("supplierKeyword", keyword);
        
        // Forward đến trang nhập phụ tùng
        req.getRequestDispatcher("/view/GdChinhNhanVienKho.jsp?page=purchase").forward(req, resp);
    }
}
