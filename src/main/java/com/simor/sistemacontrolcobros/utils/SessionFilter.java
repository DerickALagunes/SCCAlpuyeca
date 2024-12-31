package com.simor.sistemacontrolcobros.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/ver_transacciones",
        "/excel",
        "/eliminar_transaccion",
        "/pedido",
        "/eliminar_pedido",
        "/eliminar_costo",
        "/transaccion",
        "/ver_costos",
        "/ver_pedidos",
        "/costo",
        "/actualizar_transaccion",
        "/pedidos.jsp",
        "/transacciones.jsp",
        "/editar_transaccion.jsp",
        "/costos.jsp",
        "/registroInfo.jsp",
        "/registroMasivo.jsp"
})
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed (optional)
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the HttpSession from the request
        HttpSession session = httpRequest.getSession(false);

        // Check if the user is an admin(assuming isAdmin is a boolean session attribute)
        boolean isTecnico = false;
        if(session != null){
            isTecnico = session.getAttribute("tipoSesion").equals("user") || session.getAttribute("tipoSesion").equals("admin");
        }

        if (isTecnico) {
            // If the user is an admin, allow access to the Servlet
            chain.doFilter(request, response);
        } else {
            // If the user is not an admin, redirect them to a restricted access page
            httpResponse.sendRedirect("accesoDenegado.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed (optional)
    }
}