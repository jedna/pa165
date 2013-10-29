package cz.muni.fi.pa165.web1;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import javax.servlet.annotation.WebFilter;
@WebFilter("/protected/*")
public class ProtectFilter implements Filter {
  private String username = "pepa";
  private String password = "heslo";
  @Override
  public void init(FilterConfig fc) throws ServletException {
    String u = fc.getInitParameter("username");
    if(u!=null) username = u;
    String p = fc.getInitParameter("password");
    if(p!=null) password = p;
  }
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String auth = request.getHeader("Authorization");
      if (auth == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW­Authenticate", "Basic realm=\"type password\"");
        return;
      }
      String[] creds = new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
      if(!creds[0].equals(username)||!creds[1].equals(password)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW­Authenticate", "Basic realm=\"type password\"");
        return;
      }
      chain.doFilter(req, res);
  }
  @Override
  public void destroy() {
  }
}