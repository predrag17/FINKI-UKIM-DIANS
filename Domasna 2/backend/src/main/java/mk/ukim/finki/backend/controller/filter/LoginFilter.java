package mk.ukim.finki.backend.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.backend.model.User;
import org.springframework.context.annotation.Profile;


import java.io.IOException;

@WebFilter(filterName = "auth-filter", urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = {
                @WebInitParam(name = "login-path", value = "/login"),
                @WebInitParam(name = "register-path", value = "/register"),
                @WebInitParam(name = "home-path", value = "/home"),
                @WebInitParam(name = "wineries-path", value = "/wineries"),
                @WebInitParam(name = "about-path", value = "/winery/aboutUs"),
                @WebInitParam(name = "info-path", value = "/winery/info"),
        })
@Profile("servlet")
public class LoginFilter implements Filter {

    private String loginPath;
    private String registerPath;
    private String homePath;
    private String wineriesPath;
    private String aboutPath;
    private String infoPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        loginPath = filterConfig.getInitParameter("login-path");
        registerPath = filterConfig.getInitParameter("register-path");

        homePath = filterConfig.getInitParameter("home-path");
        wineriesPath = filterConfig.getInitParameter("wineries-path");
        aboutPath = filterConfig.getInitParameter("about-path");
        infoPath = filterConfig.getInitParameter("info-path");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute("user");
        String path = request.getServletPath();
        if (path.startsWith(loginPath) || path.startsWith(registerPath) || path.startsWith(homePath)
                || path.startsWith(wineriesPath) || path.startsWith(aboutPath) || path.startsWith(infoPath) || user != null) {
            System.out.println("WebFilter preprocessing...");
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println("WebFilter postprocessing...");
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
