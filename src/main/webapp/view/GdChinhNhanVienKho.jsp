<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Trang chủ nhân viên kho</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #4CAF50;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .button-group {
            display: flex;
            gap: 10px;
        }
        button, a.button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            display: inline-block;
        }
        button:hover, a.button:hover {
            background-color: #1976D2;
        }
        .content {
            background-color: white;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .content h2 {
            color: #333;
            margin-bottom: 20px;
        }
        /* Styles for menu page */
        .menu-item {
            margin: 20px 0;
            padding: 20px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            background-color: #fafafa;
        }
        .menu-item h2 {
            color: #333;
            margin-top: 0;
        }
        .menu-item p {
            color: #666;
            margin-bottom: 15px;
        }
        /* Styles for purchase page layout */
        .purchase-layout {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 20px;
        }
        .left-panel, .right-panel {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .panel-section {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            background-color: #fafafa;
        }
        .panel-section h3 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 10px;
        }
        .selected-parts {
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            background-color: #fff;
        }
        .selected-parts h3 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 10px;
        }
        /* Search form styles */
        .search-form {
            margin-bottom: 20px;
        }
        .search-form input {
            width: 70%;
            padding: 10px;
            font-size: 14px;
            border: 2px solid #ddd;
            border-radius: 5px;
            margin-right: 10px;
        }
        .search-form button {
            padding: 10px 20px;
            font-size: 14px;
        }
        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
            font-size: 14px;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .action-btn {
            padding: 5px 10px;
            font-size: 12px;
            margin: 2px;
        }
        .btn-select {
            background-color: #4CAF50;
        }
        .btn-select:hover {
            background-color: #45a049;
        }
        .btn-remove {
            background-color: #f44336;
        }
        .btn-remove:hover {
            background-color: #da190b;
        }
        .btn-confirm {
            background-color: #FF9800;
        }
        .btn-confirm:hover {
            background-color: #F57C00;
        }
        .no-results {
            padding: 20px;
            text-align: center;
            color: #666;
        }
        /* Modal styles for quantity and price input */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            border-radius: 5px;
            width: 400px;
        }
        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .close {
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover {
            color: black;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .welcome-content {
            text-align: center;
            padding: 60px 20px;
        }
        .welcome-content h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 28px;
        }
        .welcome-content p {
            color: #666;
            font-size: 18px;
            margin-bottom: 30px;
        }
        /* Menu items hidden by default */
        .menu-items-container {
            display: none;
        }
        .menu-items-container.show {
            display: block;
        }
        .menu-toggle-btn {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .menu-toggle-btn:hover {
            background-color: #1976D2;
        }
    </style>
    <script>
        function toggleMenu() {
            var menuContainer = document.getElementById('menuItemsContainer');
            if (menuContainer) {
                menuContainer.classList.toggle('show');
            }
        }
    </script>
</head>
<body><%
    String contextPath = request.getContextPath();
    if (session == null || session.getAttribute("userRole") == null || !"staff".equals(session.getAttribute("userRole"))) {
        response.sendRedirect(contextPath + "/view/staffLogin.jsp");
        return;
    }
    
    String pageParam = (String) request.getAttribute("page");
    if (pageParam == null || pageParam.isEmpty()) {
        pageParam = request.getParameter("page");
    }
    
    String pageTitle = "Trang chủ nhân viên kho";
    String includePage = "";
    boolean showWelcome = false;
    boolean showMenu = false;
    
    if (pageParam == null || pageParam.isEmpty()) {
        showWelcome = true;
        includePage = "";
    } else {
        switch (pageParam) {
            case "menu":
                pageTitle = "Menu nhân viên kho";
                showMenu = true;
                includePage = "/view/GdChonMenuNvKho.jsp";
                break;
            case "purchase":
                pageTitle = "Nhập phụ tùng từ nhà cung cấp";
                // Không tự động load dữ liệu, chỉ hiển thị trang purchase
                // Dữ liệu sẽ được load khi user search
                includePage = "/view/GdNhapPhuTung.jsp";
                break;
            default:
                showWelcome = true;
                includePage = "";
                break;
        }
    }
%>
    <div class="header">
        <h1><%= pageTitle %></h1>
        <div class="button-group">
            <% if (showMenu) { %>
                <button onclick="toggleMenu()" class="menu-toggle-btn">Menu</button>
            <% } else { %>
                <a href="<%= contextPath %>/view/GdChinhNhanVienKho.jsp?page=menu" class="button">Menu</a>
            <% } %>
            <a href="<%= contextPath %>/auth?action=logout" class="button" style="background-color:#f44336;">Đăng xuất</a>
        </div>
    </div>
    
    <div class="content">
        <% if (showWelcome) { %>
            <div class="welcome-content">
                <h2>Chào mừng đến với hệ thống quản lý kho</h2>
                <p>Vui lòng chọn Menu để bắt đầu sử dụng</p>
            </div>
        <% } else if (showMenu) { %>
            <div id="menuItemsContainer" class="menu-items-container">
                <jsp:include page="<%= includePage %>" />
            </div>
        <% } else { %>
            <jsp:include page="<%= includePage %>" />
        <% } %>
    </div>
</body>
</html>

