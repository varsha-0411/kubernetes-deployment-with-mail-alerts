package com.chitti;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HelloServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req, HttpServletResponse res)
         throws ServletException, IOException {
     res.setContentType("text/html");
     PrintWriter out = res.getWriter();
     out.println("<h1>Welcome to Chitti Store 🚀</h1>");
 }
}