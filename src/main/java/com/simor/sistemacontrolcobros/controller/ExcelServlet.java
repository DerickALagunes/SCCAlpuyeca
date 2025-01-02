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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
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
                    vehiculo.setCliente(cliente);
                    Verificacion verificacion = new Verificacion();
                    verificacion.setVehiculo(vehiculo);
                    Transaccion transaccion = new Transaccion();
                    transaccion.setCliente(cliente);
                    transaccion.setVerificacion(verificacion);

                    for (int i = 0; i < 17; i++){
                        obtenerDato(row, i, transaccion, i, req, resp);
                    }


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

    private String[] columnas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public void obtenerDato(Row row, int i, Transaccion t, int j, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cell cell = row.getCell(i);
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);

        try {
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                manejarCeldaVacia(j, t, sqlDate);
            } else {
                manejarCeldaNoVacia(cell, j, t, req, resp, columnas[i]);
            }
        } catch (Exception e) {
            manejarError(e, columnas[i], req, resp);
        }
    }

    // Método para manejar celdas vacías
    private void manejarCeldaVacia(int j, Transaccion t, Date sqlDate) {
        System.out.println("Entro a celda vacia : " + j);
        switch (j) {
            case 0 -> t.getCliente().setGestor(null);
            case 1 -> t.getCliente().setRazonSocial(null);
            case 2 -> t.getVerificacion().getVehiculo().setPlaca(null);
            case 3 -> t.getVerificacion().getVehiculo().setSerie(null);
            case 4 -> t.getVerificacion().setMateria(Verificacion.Materia.NULL);
            case 5 -> t.getVerificacion().setVerificentro(null);
            case 6 -> t.getVerificacion().setPrecio(0.0);
            case 7 -> t.setTipoPago(Transaccion.TipoPago.NULL);
            case 8 -> t.setNumeroNota(null);
            case 9 -> t.setCotizacion(null);
            case 10 -> t.getVerificacion().setFechaFolio(null);
            case 11 -> t.getVerificacion().setFolio(null);
            case 12 -> t.setCuentaDeposito(null);
            case 13 -> t.setNumeroFactura(null);
            case 14 -> t.setPagado(Transaccion.Pagado.NULL);
            case 16 -> t.setFechaPedido(null);
            default -> { /* No action needed for undefined cases */ }
        }
    }

    // Método para manejar celdas no vacías
    private void manejarCeldaNoVacia(Cell cell, int j, Transaccion t, HttpServletRequest req, HttpServletResponse resp, String columna) throws ServletException, IOException {
        try {
            logger.info("Valor celda: " + j + " = " + cell.toString());
            switch (j) {
                case 0 -> asignarValorTexto(cell, valor -> {
                    t.getCliente().setGestor(valor);
                    t.getVerificacion().getVehiculo().getCliente().setGestor(valor);
                });
                case 1 -> asignarValorTexto(cell, valor -> {
                    t.getCliente().setRazonSocial(valor);
                    t.getVerificacion().getVehiculo().getCliente().setRazonSocial(valor);
                });
                case 2 -> asignarValorTexto(cell, t.getVerificacion().getVehiculo()::setPlaca);
                case 3 -> asignarValorTexto(cell, t.getVerificacion().getVehiculo()::setSerie);
                case 4 -> asignarValorTexto(cell, t.getVerificacion()::setMateria);
                case 5 -> asignarValorTexto(cell, t.getVerificacion()::setVerificentro);
                case 6 -> asignarValorNumerico(cell, t.getVerificacion()::setPrecio, req, resp, columna);
                case 7 -> asignarValorTexto(cell, t::setTipoPago);
                case 8 -> asignarValorTexto(cell, t::setNumeroNota);
                case 9 -> asignarValorTexto(cell, t::setCotizacion);
                case 10 -> t.getVerificacion().setFechaFolio(convertirFecha(cell));
                case 11 -> asignarValorTexto(cell, t.getVerificacion()::setFolio);
                case 12 -> asignarValorTexto(cell, t::setCuentaDeposito);
                case 13 -> asignarValorTexto(cell, t::setNumeroFactura);
                case 14 -> asignarValorTexto(cell, t::setPagado);
                case 16 -> t.setFechaPedido(convertirFecha(cell));
                default -> { /* No action needed for undefined cases */ }
            }
        } catch (Exception e) {
            throw new ServletException("Error procesando celda en columna: " + columna, e);
        }
    }

    // Asigna valores numéricos con manejo de errores
    private void asignarValorNumerico(Cell cell, Consumer<Double> setter, HttpServletRequest req, HttpServletResponse resp, String columna) throws ServletException, IOException {
        try {
            setter.accept(cell.getNumericCellValue());
        } catch (Exception ex) {
            manejarFormatoNumero(cell, setter, req, resp, columna);
        }
    }

    // Método para asignar valores de texto
    private void asignarValorTexto(Cell cell, Consumer<String> setter) {
        if (cell.getCellType() == CellType.STRING) {
            setter.accept(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            setter.accept(BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString());
        }
    }

    // Manejo de formato incorrecto en valores numéricos
    private void manejarFormatoNumero(Cell cell, Consumer<Double> setter, HttpServletRequest req, HttpServletResponse resp, String columna) throws ServletException, IOException {
        try {
            String cellValue = cell.getStringCellValue().replaceAll("[^0-9.]", "");
            setter.accept(Double.parseDouble(cellValue));
        } catch (NumberFormatException e) {
            manejarErrorFormato(columna, req, resp);
        }
    }

    // Manejo de errores de formato
    private void manejarErrorFormato(String columna, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mensajeError = "Error en formato numérico en la columna: " + columna + ". Revisa la estructura del archivo.";
        logger.severe(mensajeError);
        req.setAttribute("error", mensajeError);
        req.getRequestDispatcher("error.jsp").forward(req, resp);
    }

    // Conversión de celdas a fechas
    private Date convertirFecha(Cell cell) {
        return new java.sql.Date(cell.getDateCellValue().getTime());
    }

    // Manejo de errores generales
    private void manejarError(Exception e, String columna, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mensajeError = "Error al procesar la columna: " + columna + ". Verifica la información.";
        logger.severe(mensajeError);
        e.printStackTrace();
        req.setAttribute("error", mensajeError);
        req.getRequestDispatcher("error.jsp").forward(req, resp);
    }
}
