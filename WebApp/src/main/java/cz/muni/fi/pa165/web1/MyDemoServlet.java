package cz.muni.fi.pa165.web1;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.*;

@WebServlet(urlPatterns = {"/text/*", "/file/*", "/redirect/*"})
public class MyDemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/text".equals(request.getServletPath()) && "/direct".equals(request.getPathInfo())) {
            response.setContentType("text/html ;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h1>Text</h1><pre>generated directly from servlet code");
            out.println("serverInfo=" + getServletContext().getServerInfo());
            out.println("parameters:<i>");
            for (String p : Collections.list(request.getParameterNames())) {
                out.println(p + "=" + request.getParameter(p));
            }
        } else if ("/text".equals(request.getServletPath()) && "/fromjsp".equals(request.getPathInfo())) {
            request.setAttribute("mylist", Arrays.asList("milk", "bread", "pizza"));
            request.getRequestDispatcher("/mypage.jsp").forward(request, response);
        } else if ("/file".equals(request.getServletPath())) {
            response.setContentType("application/zip");
            response.setHeader("Content­disposition", "attachment ; filename=\"myfile.zip\"");
            ServletOutputStream sos = response.getOutputStream();
            ZipOutputStream zos = new ZipOutputStream(sos);
            zos.putNextEntry(new ZipEntry("something.txt"));
            zos.write("some text".getBytes());
            zos.putNextEntry(new ZipEntry("differentfile.txt"));
            zos.write("other text".getBytes());
            zos.close();
        } else if ("/redirect".equals(request.getServletPath())) {
            String url = request.getContextPath() + "/text/direct?z=" + URLEncoder.encode("?/ =", "UTF-8");
            response.sendRedirect(response.encodeRedirectURL(url));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Nothing here, you are lost");
        }
    }
}