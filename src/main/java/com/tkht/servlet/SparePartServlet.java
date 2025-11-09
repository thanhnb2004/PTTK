package com.tkht.servlet;

import com.tkht.dao.PartDao;
import com.tkht.dao.Supplier_PartDao;
import com.tkht.model.Part;
import com.tkht.model.Supplier_Part;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SparePartServlet extends HttpServlet {
    private final PartDao partDao = new PartDao();
    private final Supplier_PartDao supplierPartDao = new Supplier_PartDao();

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
        String keyword = req.getParameter("keyword");
        String supplierId = req.getParameter("supplierId");
        
        // Nếu có supplierId từ parameter, lưu vào session
        if (supplierId != null && !supplierId.isEmpty()) {
            req.getSession().setAttribute("selectedSupplierId", supplierId);
        } else {
            // Lấy từ session nếu không có trong parameter
            supplierId = (String) req.getSession().getAttribute("selectedSupplierId");
        }
        
        List<Part> filteredParts = new ArrayList<>();
        
        // Chỉ lấy phụ tùng nếu đã chọn nhà cung cấp
        if (supplierId != null && !supplierId.isEmpty()) {
            // Lấy các Supplier_Part của nhà cung cấp này
            List<Supplier_Part> supplierParts = supplierPartDao.findBySupplierId(supplierId);
            
            // Lấy các part_id từ Supplier_Part
            Set<String> partIds = new HashSet<>();
            for (Supplier_Part sp : supplierParts) {
                partIds.add(sp.getPartId());
            }
            
            // Lấy các Part tương ứng
            for (String partId : partIds) {
                Part part = partDao.findById(partId);
                if (part != null) {
                    filteredParts.add(part);
                }
            }
            
            // Áp dụng filter theo keyword nếu có
            if (keyword != null && !keyword.trim().isEmpty()) {
                String lowerKeyword = keyword.toLowerCase().trim();
                List<Part> keywordFiltered = new ArrayList<>();
                for (Part part : filteredParts) {
                    if ((part.getName() != null && part.getName().toLowerCase().contains(lowerKeyword)) ||
                        (part.getId() != null && part.getId().toLowerCase().contains(lowerKeyword))) {
                        keywordFiltered.add(part);
                    }
                }
                filteredParts = keywordFiltered;
            }
        }
        
        req.setAttribute("parts", filteredParts);
        req.setAttribute("partKeyword", keyword);
        req.setAttribute("selectedSupplierId", supplierId);
        
        // Nếu là include request (từ JSP), chỉ set attribute và return
        // Không forward để tránh lỗi
        // Các JSP sẽ tự đọc attribute từ request
    }

    private void handleSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String keyword = req.getParameter("keyword");
        String supplierId = req.getParameter("supplierId");
        
        // Nếu có supplierId từ parameter, lưu vào session
        if (supplierId != null && !supplierId.isEmpty()) {
            req.getSession().setAttribute("selectedSupplierId", supplierId);
        } else {
            // Lấy từ session nếu không có trong parameter
            supplierId = (String) req.getSession().getAttribute("selectedSupplierId");
        }
        
        List<Part> filteredParts = new ArrayList<>();
        
        // Chỉ lấy phụ tùng nếu đã chọn nhà cung cấp
        if (supplierId != null && !supplierId.isEmpty()) {
            // Lấy các Supplier_Part của nhà cung cấp này
            List<Supplier_Part> supplierParts = supplierPartDao.findBySupplierId(supplierId);
            
            // Lấy các part_id từ Supplier_Part
            Set<String> partIds = new HashSet<>();
            for (Supplier_Part sp : supplierParts) {
                partIds.add(sp.getPartId());
            }
            
            // Lấy các Part tương ứng
            for (String partId : partIds) {
                Part part = partDao.findById(partId);
                if (part != null) {
                    filteredParts.add(part);
                }
            }
            
            // Áp dụng filter theo keyword nếu có
            if (keyword != null && !keyword.trim().isEmpty()) {
                String lowerKeyword = keyword.toLowerCase().trim();
                List<Part> keywordFiltered = new ArrayList<>();
                for (Part part : filteredParts) {
                    if ((part.getName() != null && part.getName().toLowerCase().contains(lowerKeyword)) ||
                        (part.getId() != null && part.getId().toLowerCase().contains(lowerKeyword))) {
                        keywordFiltered.add(part);
                    }
                }
                filteredParts = keywordFiltered;
            }
        }
        
        req.setAttribute("parts", filteredParts);
        req.setAttribute("partKeyword", keyword);
        req.setAttribute("selectedSupplierId", supplierId);
        
        // Forward đến trang nhập phụ tùng
        req.getRequestDispatcher("/view/GdChinhNhanVienKho.jsp?page=purchase").forward(req, resp);
    }
}
