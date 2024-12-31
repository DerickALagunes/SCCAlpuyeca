package com.simor.sistemacontrolcobros.controller;

import com.simor.sistemacontrolcobros.model.Mensaje;
import com.simor.sistemacontrolcobros.model.Pedido;
import com.simor.sistemacontrolcobros.model.dao.PedidoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "pedido", value = "/pedido")
public class PedidoServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(PedidoServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id_pedido = req.getParameter("id_pedido");

        if(id_pedido != null && !id_pedido.equals("0")){
            //actualizacion
            PedidoDAO dao = new PedidoDAO();

            Pedido original = dao.findOne(Integer.parseInt(id_pedido));

            String fecha_envio = req.getParameter("fecha_envio");
            Date sqlDate;
            if (fecha_envio != null && !fecha_envio.isEmpty()) {
                // Convertir la cadena a LocalDate
                LocalDate localDate = LocalDate.parse(fecha_envio);
                // Convertir LocalDate a java.sql.Date
                sqlDate = Date.valueOf(localDate);
            } else {
                // Usar la fecha actual como valor predeterminado
                LocalDate today = LocalDate.now();
                sqlDate = Date.valueOf(today);
            }

            String numero_guia = req.getParameter("numero_guia");
            String recibio = req.getParameter("recibio");

            //Foto
            String UPLOAD_DIRECTORY = req.getServletContext().getRealPath("/") + "assets" + File.separator + "fotosPedidos";
            String fotoPath = "";

            Part filePart = req.getPart("foto");
            if (filePart != null || filePart.getSize() > 0 || filePart.getSubmittedFileName() != null || !filePart.getSubmittedFileName().isEmpty()) {
                try {
                    String fileName = getSubmittedFileName(filePart);
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                    fotoPath = UPLOAD_DIRECTORY + File.separator + uniqueFileName;
                    InputStream fileContent = filePart.getInputStream();
                    Files.copy(fileContent, Paths.get(fotoPath));

                    File fotoVieja = new File(original.getFoto());  // Crear un objeto File con la ruta
                    // Verificar si el archivo existe y eliminarlo
                    if (fotoVieja.exists()) {
                        boolean deleted = fotoVieja.delete();  // Eliminar el archivo
                        if (deleted) {
                            System.out.println("El archivo fue eliminado exitosamente.");
                        } else {
                            System.out.println("No se pudo eliminar el archivo.");
                        }
                    } else {
                        System.out.println("El archivo no existe en la ruta proporcionada.");
                    }
                } catch (Exception e) {
                    logger.severe("Error al guardar la foto: " + e.getMessage());
                    String mensajeError = "Ocurrió un error al guardar la foto: " + e.getMessage();
                    e.printStackTrace();
                    req.setAttribute("error", mensajeError);
                    req.getRequestDispatcher("error.jsp").forward(req, resp);
                }
            } else {
                fotoPath = original.getFoto();
            }

            Pedido pedido = new Pedido(
                    original.getIdPedido(),
                    sqlDate,
                    numero_guia,
                    recibio,
                    fotoPath
            );

            //Ya se tiene la transacción ahora debo registrarla en la base de datos


            try {
                dao.update(pedido);
                //Mandar mensaje de exito
                req.getSession().setAttribute("mensaje", new Mensaje(
                        Mensaje.TipoMensaje.SUCCESS,
                        new ArrayList<String>(
                                Arrays.asList(
                                        "La información se actualizó exitosamente"
                                )
                        )
                ));

                resp.sendRedirect("pedidos.jsp");

            } catch (Exception e) {
                logger.severe("Error al actualizar pedido: " + e.getMessage());
                String mensajeError = "Ocurrió un error al actualizar el pedido: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }


        }else {
            //registro
            String fecha_envio = req.getParameter("fecha_envio");
            Date sqlDate;
            if (fecha_envio != null && !fecha_envio.isEmpty()) {
                // Convertir la cadena a LocalDate
                LocalDate localDate = LocalDate.parse(fecha_envio);
                // Convertir LocalDate a java.sql.Date
                sqlDate = Date.valueOf(localDate);
            } else {
                // Usar la fecha actual como valor predeterminado
                LocalDate today = LocalDate.now();
                sqlDate = Date.valueOf(today);
            }

            String numero_guia = req.getParameter("numero_guia");
            String recibio = req.getParameter("recibio");

            //Foto
            String UPLOAD_DIRECTORY = req.getServletContext().getRealPath("/") + "assets" + File.separator + "fotosPedidos";
            String fotoPath = "";

            Part filePart = req.getPart("foto");
            if (filePart != null || filePart.getSize() > 0 || filePart.getSubmittedFileName() != null || !filePart.getSubmittedFileName().isEmpty()) {
                try {
                    String fileName = getSubmittedFileName(filePart);
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                    fotoPath = UPLOAD_DIRECTORY + File.separator + uniqueFileName;
                    InputStream fileContent = filePart.getInputStream();
                    Files.copy(fileContent, Paths.get(fotoPath));
                } catch (Exception e) {
                    logger.severe("Error al guardar la foto: " + e.getMessage());
                    String mensajeError = "Ocurrió un error al guardar la foto: " + e.getMessage();
                    e.printStackTrace();
                    req.setAttribute("error", mensajeError);
                    req.getRequestDispatcher("error.jsp").forward(req, resp);
                }
            } else {
                fotoPath = UPLOAD_DIRECTORY + File.separator + "sin_imagen.png";
            }

            Pedido pedido = new Pedido(
                    sqlDate,
                    numero_guia,
                    recibio,
                    fotoPath
            );

            //Ya se tiene la transacción ahora debo registrarla en la base de datos
            PedidoDAO dao = new PedidoDAO();

            try {
                dao.insert(pedido);
                //Mandar mensaje de exito
                req.getSession().setAttribute("mensaje", new Mensaje(
                        Mensaje.TipoMensaje.SUCCESS,
                        new ArrayList<String>(
                                Arrays.asList(
                                        "La información se registro exitosamente"
                                )
                        )
                ));

                resp.sendRedirect("pedidos.jsp");

            } catch (Exception e) {
                logger.severe("Error al insertar pedido: " + e.getMessage());
                String mensajeError = "Ocurrió un error al procesar el pedido: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        }
    }

    private String getSubmittedFileName(Part part) {
        String header = part.getHeader("content-disposition");
        String[] elements = header.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(
                        element.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
}
