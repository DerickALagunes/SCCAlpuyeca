package com.simor.sistemacontrolcobros.controller;

import com.simor.sistemacontrolcobros.model.Mensaje;
import com.simor.sistemacontrolcobros.model.dao.TransaccionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

@WebServlet(name = "eliminarTransaccion", value = "/eliminar_transaccion")
public class EliminarTransaccionServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TransaccionServlet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null){
            try{
                TransaccionDAO dao = new TransaccionDAO();
                dao.delete(Integer.parseInt(id));

                //Mandar mensaje de exito
                req.getSession().setAttribute("mensaje", new Mensaje(
                        Mensaje.TipoMensaje.SUCCESS,
                        new ArrayList<String>(
                                Arrays.asList(
                                        "La información se borró exitosamente"
                                )
                        )
                ));
            }catch (Exception e){
                logger.severe("Error al eliminar transaccion: " + e.getMessage());
                String mensajeError = "Ocurrió un error al eliminar el transaccion: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
            
        }else{
            //Mandar mensaje de error
            req.getSession().setAttribute("mensaje", new Mensaje(
                    Mensaje.TipoMensaje.ERROR,
                    new ArrayList<String>(
                            Arrays.asList(
                                    "No se encontro el registro"
                            )
                    )
            ));
        }
        resp.sendRedirect("transacciones.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("transacciones.jsp");
    }
}
