import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
        Arrays.asList("", "/index", "/forgot", "/register")));
// doFilter function don't let anonymous user get into pages without login
        // Example reset.html can't be accessed directly since it filters on the basis of username and requests
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", ""); 

        boolean loggedIn = (session != null && session.getAttribute("Id") != null);
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (loggedIn || allowedPath) {
            chain.doFilter(req, res);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
// This function initializes filter and exception is added if that's not supported by the browser
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // This function destroys filter and exception is added if that's not supported by the browser
    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}