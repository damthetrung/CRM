package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//urlPatterns : Khi người dùng gọi link được quy định trong đây thì
// filter sẽ được kích hoạt
@WebFilter(urlPatterns = {"/*"})
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//     Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse resp= (HttpServletResponse) response;
        HttpSession session=req.getSession();
        if(!req.getServletPath().startsWith("/login")){
                if(session.getAttribute("isSuccess")!=null) {
                    String path = req.getServletPath();
                    int role_id = (int) session.getAttribute("role_id");
                    if (path.equals("/user-profile") || path.equals("/logout") || path.equals("/blank") || path.equals("/404")) {
                        chain.doFilter(request, response);
                    }
                    if (path.equals("/user-table") || path.equals("/user-add") || path.equals("/user-edit") || path.equals("/user-details") || path.equals("/user-delete")) {
                        if (role_id == 1) {
                            chain.doFilter(request, response);
                        } else {
                            resp.sendRedirect(req.getContextPath() + "/404");
                        }
                    }
                    if (path.equals("/role-table") || path.equals("/role-add") || path.equals("/role-edit") || path.equals("/role-delete")) {
                        if (role_id == 1) {
                            chain.doFilter(request, response);
                        } else {
                            resp.sendRedirect(req.getContextPath() + "/404");
                        }
                    }
                    if (path.equals("/groupwork") || path.equals("/groupwork-add") || path.equals("/groupwork-details") || path.equals("/groupwork-delete") || path.equals("/groupwork-edit")) {
                        if (role_id == 1 || role_id == 2) {
                            chain.doFilter(request, response);
                        } else {
                            resp.sendRedirect(req.getContextPath() + "/404");
                        }
                    }
                    if (path.equals("/task") || path.equals("/task-add") || path.equals("/task-edit")) {
                        if (role_id == 1 || role_id == 2 || role_id == 3) {
                            chain.doFilter(request, response);
                        }
                    }
                }else{
                    resp.sendRedirect(req.getContextPath()+"/login");
                }
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
