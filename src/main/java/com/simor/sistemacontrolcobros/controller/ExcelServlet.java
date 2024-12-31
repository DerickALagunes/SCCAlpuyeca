package com.simor.sistemacontrolcobros.controller;

import com.simor.sistemacontrolcobros.model.*;
import com.simor.sistemacontrolcobros.model.dao.TransaccionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "excel", value = "/excel")
public class ExcelServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ExcelServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener el archivo subido
        Part filePart = req.getPart("archivo");

        if (filePart == null || filePart.getSize() == 0) {
            logger.severe("Error no hay archivo o esta corrupto");
            String mensajeError = "Error no hay archivo o esta corrupto";
            req.setAttribute("error", mensajeError);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        List<Transaccion> transacciones = new ArrayList<>();

        try (InputStream fileContent = filePart.getInputStream();
             Workbook workbook = WorkbookFactory.create(fileContent)) {
                Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja
                boolean isFirstRow = true;

                for (Row row : sheet) {
                    if (isFirstRow) {
                        isFirstRow = false; // Saltar la primera fila (encabezados)
                        continue;
                    }

                    Cliente cliente = new Cliente();
                    Vehiculo vehiculo = new Vehiculo();
                    Verificacion verificacion = new Verificacion();
                    Transaccion transaccion = new Transaccion();

                    // Leer las celdas de cada fila
                    cliente.setGestor(row.getCell(0).getStringCellValue());
                    cliente.setRazonSocial(row.getCell(1).getStringCellValue());
                    vehiculo.setPlaca(row.getCell(2).getStringCellValue());
                    vehiculo.setSerie(row.getCell(3).getStringCellValue());
                    vehiculo.setCliente(cliente);
                    verificacion.setMateria(row.getCell(4).getStringCellValue());
                    verificacion.setVerificentro(row.getCell(5).getStringCellValue());
                    verificacion.setPrecio(row.getCell(6).getNumericCellValue());
                    verificacion.setVehiculo(vehiculo);
                    transaccion.setTipoPago(row.getCell(7).getStringCellValue());
                    transaccion.setNumeroNota(row.getCell(8).getStringCellValue());
                    try{
                        transaccion.setCotizacion(row.getCell(9).getStringCellValue());
                    }catch (IllegalStateException e){
                        transaccion.setCotizacion(row.getCell(9).getNumericCellValue()+"");
                    }
                    transaccion.setVerificacion(verificacion);
                    transaccion.setCliente(cliente);

                    // Agregar a la lista
                    transacciones.add(transaccion);
                }
        } catch (Exception e) {
            logger.severe("Error al procesar el archivo, revisa la estructura y la información o esta corrupto");
            String mensajeError = "Error al procesar el archivo, revisa la estructura y la información o esta corrupto";
            e.printStackTrace();
            req.setAttribute("error", mensajeError);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        // Registrar las transacciones en la base de datos
        try {
            TransaccionDAO dao = new TransaccionDAO(); // Asume que tienes un DAO implementado
            for (Transaccion t : transacciones) {
                dao.insert(t); // Inserta cada transacción en la BD
            }
            //mensaje de confirmación
            req.getSession().setAttribute("mensaje",new Mensaje(
                    Mensaje.TipoMensaje.SUCCESS,
                    new ArrayList<String>(
                            Arrays.asList(
                                    "La información se registro exitosamente"
                            )
                    )
            ));

            resp.sendRedirect("index.jsp");

        } catch (Exception e) {
            logger.severe("Error al hacer carga masiva: " + e.getMessage());
            String mensajeError = "Ocurrió un error al procesar la carga del archivo: " + e.getMessage();
            e.printStackTrace();
            req.setAttribute("error", mensajeError);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
