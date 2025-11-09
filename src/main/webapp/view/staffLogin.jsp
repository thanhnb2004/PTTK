<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
    if (session != null && "staff".equals(session.getAttribute("userRole"))) {
        response.sendRedirect(request.getContextPath() + "/view/GdChinhKh.jsp?page=menu");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #ffffff;
            padding: 36px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            width: 340px;
        }
        h1 {
            margin: 0 0 24px;
            text-align: center;
            color: #1a1a1a;
        }
        .error {
            background-color: #ffecec;
            border: 1px solid #ff3b30;
            color: #9b1c14;
            padding: 10px 12px;
            border-radius: 4px;
            margin-bottom: 16px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            color: #333333;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #dcdcdc;
            border-radius: 4px;
            margin-bottom: 16px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #2f80ed;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #2466c7;
        }
        .links {
            margin-top: 18px;
            text-align: center;
        }
        .links a {
            color: #2f80ed;
            text-decoration: none;
        }
        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Staff Login</h1>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <form action="<%= request.getContextPath() %>/auth" method="post">
            <input type="hidden" name="role" value="staff" />
            <label for="username">Username</label>
            <input type="text" id="username" name="username" placeholder="Enter username" required />

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter password" required />

            <button type="submit">Login</button>
        </form>
        <div class="links">
            <p><a href="<%= request.getContextPath() %>/view/customerLogin.jsp">Customer login</a></p>
        </div>
    </div>
</body>
</html>

