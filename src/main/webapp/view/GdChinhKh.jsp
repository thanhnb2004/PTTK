<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Trang chủ khách hàng</title>
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
        /* Styles for search page */
        .search-form {
            margin-bottom: 30px;
        }
        .search-form input {
            width: 70%;
            padding: 12px;
            font-size: 16px;
            border: 2px solid #ddd;
            border-radius: 5px;
            margin-right: 10px;
        }
        .search-form button {
            padding: 12px 30px;
        }
        .results {
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
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
        .no-results {
            padding: 20px;
            text-align: center;
            color: #666;
        }
        /* Styles for detail page */
        .detail-item {
            margin: 20px 0;
            padding: 15px;
            border-bottom: 1px solid #e0e0e0;
        }
        .detail-item:last-child {
            border-bottom: none;
        }
        .detail-label {
            font-weight: bold;
            color: #333;
            font-size: 16px;
            margin-bottom: 5px;
        }
        .detail-value {
            color: #666;
            font-size: 18px;
            padding-left: 20px;
        }
        .no-service {
            text-align: center;
            padding: 40px;
            color: #666;
        }
        /* Styles for welcome page */
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
    </style>
</head>
<body><%
    String contextPath = request.getContextPath();
    if (session == null || session.getAttribute("userRole") == null) {
        response.sendRedirect(contextPath + "/view/customerLogin.jsp");
        return;
    }
    
    String pageParam = (String) request.getAttribute("page");
    if (pageParam == null || pageParam.isEmpty()) {
        pageParam = request.getParameter("page");
    }
    
    String pageTitle = "Trang chủ khách hàng";
    String includePage = "";
    boolean showWelcome = false;
    
    if (pageParam == null || pageParam.isEmpty()) {
        showWelcome = true;
        includePage = "";
    } else {
        switch (pageParam) {
            case "menu":
                pageTitle = "Menu";
                includePage = "/view/GdChonMenu.jsp";
                break;
            case "search":
                pageTitle = "Tìm kiếm dịch vụ";
                includePage = "/view/GdTimKiem.jsp";
                break;
            case "detail":
                pageTitle = "Chi tiết dịch vụ";
                includePage = "/view/GdChiTietDichVu.jsp";
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
            <a href="<%= contextPath %>/view/GdChinhKh.jsp?page=menu" class="button">Menu</a>
            <a href="<%= contextPath %>/auth?action=logout" class="button" style="background-color:#f44336;">Đăng xuất</a>
        </div>
    </div>
    
    <div class="content">
        <% if (showWelcome) { %>
            <div class="welcome-content">
                <h2>Chào mừng đến với hệ thống quản lý dịch vụ</h2>
                <p>Vui lòng chọn Menu để bắt đầu sử dụng dịch vụ</p>
            </div>
        <% } else { %>
            <jsp:include page="<%= includePage %>" />
        <% } %>
    </div>
</body>
</html>

