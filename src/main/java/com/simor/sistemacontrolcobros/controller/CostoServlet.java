package com.simor.sistemacontrolcobros.controller;

import com.simor.sistemacontrolcobros.model.Costo;
import com.simor.sistemacontrolcobros.model.Mensaje;
import com.simor.sistemacontrolcobros.model.dao.CostoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "costo", value = "/costo")
public class CostoServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CostoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id_costo = req.getParameter("id_costo");

        if(id_costo != null && !id_costo.equals("0")) {
            //actualizacion
            CostoDAO dao = new CostoDAO();

            Costo original = dao.findOne(Integer.parseInt(id_costo));

            String materia = req.getParameter("materia");
            String costo_s = req.getParameter("costo");

            Double costo_d = 0.0;
            if(costo_s != null){
                costo_d = Double.parseDouble(costo_s);
            }

            String cliente_empresa = req.getParameter("cliente_empresa");
            String encargado = req.getParameter("encargado");
            String atiende_y_cobra = req.getParameter("atiende_y_cobra");

            Costo costo = new Costo(
                    original.getIdCosto(),
                    materia,
                    costo_d,
                    cliente_empresa,
                    encargado,
                    atiende_y_cobra
            );

            try {
                dao.update(costo);
                //Mandar mensaje de exito
                req.getSession().setAttribute("mensaje", new Mensaje(
                        Mensaje.TipoMensaje.SUCCESS,
                        new ArrayList<String>(
                                Arrays.asList(
                                        "La información se actualizó exitosamente"
                                )
                        )
                ));

                resp.sendRedirect("costos.jsp");

            } catch (Exception e) {
                logger.severe("Error al actualizar costo: " + e.getMessage());
                String mensajeError = "Ocurrió un error al actualizar el costo: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }


        }else {
            //registro
            String materia = req.getParameter("materia");
            String costo_s = req.getParameter("costo");

            Double costo_d = 0.0;
            if(costo_s != null){
                costo_d = Double.parseDouble(costo_s);
            }

            String cliente_empresa = req.getParameter("cliente_empresa");
            String encargado = req.getParameter("encargado");
            String atiende_y_cobra = req.getParameter("atiende_y_cobra");

            Costo costo = new Costo(
                    materia,
                    costo_d,
                    cliente_empresa,
                    encargado,
                    atiende_y_cobra
            );

            //Ya se tiene la transacción ahora debo registrarla en la base de datos
            CostoDAO dao = new CostoDAO();

            try {
                dao.insert(costo);
                //Mandar mensaje de exito
                req.getSession().setAttribute("mensaje", new Mensaje(
                        Mensaje.TipoMensaje.SUCCESS,
                        new ArrayList<String>(
                                Arrays.asList(
                                        "La información se registro exitosamente"
                                )
                        )
                ));

                resp.sendRedirect("costos.jsp");

            } catch (Exception e) {
                logger.severe("Error al insertar costo: " + e.getMessage());
                String mensajeError = "Ocurrió un error al procesar el costo: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        }
    }
}
