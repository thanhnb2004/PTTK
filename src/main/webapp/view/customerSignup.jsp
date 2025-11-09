<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
    String message = (String) request.getAttribute("message");
    if (session != null && session.getAttribute("userRole") != null) {
        Object role = session.getAttribute("userRole");
        if ("staff".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/view/GdChinhKh.jsp?page=menu");
        } else {
            response.sendRedirect(request.getContextPath() + "/view/GdChinhKh.jsp");
        }
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Signup</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef1f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .container {
            background: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
            width: 420px;
            box-sizing: border-box;
        }
        h1 {
            margin: 0 0 24px;
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
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 11px;
            border: 1px solid #dcdcdc;
            border-radius: 4px;
            margin-bottom: 18px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 11px;
            background-color: #ff6f3c;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #e85a28;
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
        <h1>Create Customer Account</h1>
        <% if (message != null) { %>
            <div class="message"><%= message %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <form action="<%= request.getContextPath() %>/customers" method="post">
            <input type="hidden" name="action" value="signup" />

            <label for="name">Full name</label>
            <input type="text" id="name" name="name" placeholder="Enter full name" value="<%= request.getAttribute("formName") != null ? request.getAttribute("formName") : "" %>" />

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter email" required value="<%= request.getAttribute("formEmail") != null ? request.getAttribute("formEmail") : "" %>" />

            <label for="phoneNumber">Phone number</label>
            <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Enter phone number" required value="<%= request.getAttribute("formPhone") != null ? request.getAttribute("formPhone") : "" %>" />

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter password" required />

            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter password" required />

            <button type="submit">Sign up</button>
        </form>
        <div class="links">
            <p><a href="<%= request.getContextPath() %>/view/customerLogin.jsp">Back to login</a></p>
        </div>
    </div>
</body>
</html>

