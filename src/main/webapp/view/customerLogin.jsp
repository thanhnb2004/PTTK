<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
    if (session != null && session.getAttribute("userRole") != null) {
        response.sendRedirect(request.getContextPath() + "/GdChinhKh");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 360px;
        }
        h1 {
            margin-top: 0;
            text-align: center;
            color: #1a1a1a;
        }
        .message {
            background-color: #e6ffed;
            border: 1px solid #34c759;
            color: #1c7c2a;
            padding: 10px 12px;
            border-radius: 4px;
            margin-bottom: 16px;
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
        input[type="email"],
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
            background-color: #0070f3;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0059c1;
        }
        .links {
            margin-top: 20px;
            text-align: center;
        }
        .links a {
            color: #0070f3;
            text-decoration: none;
        }
        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Customer Login</h1>
        <% if (message != null) { %>
            <div class="message"><%= message %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <form action="<%= request.getContextPath() %>/auth" method="post">
            <input type="hidden" name="role" value="customer" />
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter email" required />

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter password" required />

            <button type="submit">Login</button>
        </form>
        <div class="links">
            <p><a href="<%= request.getContextPath() %>/view/customerSignup.jsp">Create customer account</a></p>
            <p><a href="<%= request.getContextPath() %>/view/staffLogin.jsp">Staff login</a></p>
        </div>
    </div>
</body>
</html>

