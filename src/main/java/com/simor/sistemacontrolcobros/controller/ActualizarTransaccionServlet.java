package com.simor.sistemacontrolcobros.controller;

import com.simor.sistemacontrolcobros.model.*;
import com.simor.sistemacontrolcobros.model.dao.TransaccionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet(name = "actualizar_transaccion", value = "/actualizar_transaccion")
public class ActualizarTransaccionServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TransaccionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String transaccionId = req.getParameter("id");
        if (transaccionId != null) {
            try {
                int id = Integer.parseInt(transaccionId);

                // Obtener el objeto de transacción
                TransaccionDAO dao = new TransaccionDAO();
                Transaccion transaccion = dao.findOne(id);

                if (transaccion != null) {
                    // Enviar el objeto transacción a la vista de edición
                    req.setAttribute("transaccion", transaccion);

                    // Redirigir a la página de edición (editar_transaccion.jsp)
                    req.getRequestDispatcher("editar_transaccion.jsp").forward(req, resp);
                } else {
                    // Manejar caso en que no se encuentre la transacción
                    logger.warning("No se encontró ninguna transacción con el ID: " + id);
                    String mensajeError = "No se encontró ninguna transacción con el ID: " + id;
                    req.setAttribute("error", mensajeError);
                    req.getRequestDispatcher("error.jsp").forward(req, resp);
                }
            } catch (NumberFormatException e) {
                logger.severe("El ID de la transacción no es válido: " + e.getMessage());
                String mensajeError = "El ID de la transacción no es válido.";
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            } catch (Exception e) {
                logger.severe("Error al obtener la transacción: " + e.getMessage());
                String mensajeError = "Error al obtener la transacción: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        } else {
            logger.severe("Error con el identificador de la transacción: No se encuentra la variable");
            String mensajeError = "Error con el identificador de la transacción: No se encuentra la variable.";
            req.setAttribute("error", mensajeError);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idTransaccion = req.getParameter("id_transaccion");
        if(idTransaccion != null){

            //Obtener Id's
            TransaccionDAO dao = new TransaccionDAO();
            Transaccion transaccionOriginal = dao.findOne(Integer.parseInt(idTransaccion));
            int idCliente = transaccionOriginal.getCliente().getIdCliente();
            int idVehiculo = transaccionOriginal.getVerificacion().getVehiculo().getIdVehiculo();
            int idVerificacion = transaccionOriginal.getVerificacion().getIdVerificacion();

            //Cliente
            String gestor = req.getParameter("gestor");
            String razon_social  = req.getParameter("razon_social");

            Cliente cliente = new Cliente(idCliente, gestor, razon_social);

            //Vehiculo
            String placa = req.getParameter("placa");
            String serie = req.getParameter("serie");

            Vehiculo vehiculo = new Vehiculo(idVehiculo, placa, serie , cliente);

            //Verificación
            String materiaString = req.getParameter("materia");
            Verificacion.Materia materia = Verificacion.Materia.NULL;
            if(materiaString != null) {
                switch (materiaString) {
                    case "HUMO":
                        materia = Verificacion.Materia.HUMO;
                        break;
                    case "MOTRIZ":
                        materia = Verificacion.Materia.MOTRIZ;
                        break;
                    case "ARRASTRE":
                        materia = Verificacion.Materia.ARRASTRE;
                        break;
                    case "GASOLINA":
                        materia = Verificacion.Materia.GASOLINA;
                        break;
                    default:
                        break;
                }
            }

            String verificentro = req.getParameter("verificentro");
            String folio = req.getParameter("folio");

            String fecha_folio = req.getParameter("fecha_folio");
            Date sqlDate;
            if (fecha_folio != null && !fecha_folio.isEmpty()) {
                // Convertir la cadena a LocalDate
                LocalDate localDate = LocalDate.parse(fecha_folio);

                // Convertir LocalDate a java.sql.Date
                sqlDate = Date.valueOf(localDate);
            } else {
                // Usar la fecha actual como valor predeterminado
                LocalDate today = LocalDate.now();
                sqlDate = Date.valueOf(today);
            }

            String precioString = req.getParameter("precio");
            Double precio = precioString == null ? 0.0 : Double.parseDouble(precioString);

            Verificacion verificacion = new Verificacion(idVerificacion, vehiculo, materia,verificentro, precio, sqlDate,folio);

            //Transaccion
            String tipo_pagoString = req.getParameter("tipo_pago");
            Transaccion.TipoPago tipo_pago = Transaccion.TipoPago.NULL;
            if(tipo_pagoString != null) {
                switch (tipo_pagoString) {
                    case "FACTURA":
                        tipo_pago = Transaccion.TipoPago.FACTURA;
                        break;
                    case "EFECTIVO":
                        tipo_pago = Transaccion.TipoPago.EFECTIVO;
                        break;
                    case "DEPOSITO":
                        tipo_pago = Transaccion.TipoPago.DEPOSITO;
                        break;
                    case "NULL":
                        tipo_pago = Transaccion.TipoPago.NULL;
                        break;
                    default:
                        break;
                }
            }

            String cuenta_deposito = req.getParameter("cuenta_deposito");

            String estado_pagoString = req.getParameter("estado_pago"); //PAGADA o NO_PAGADA
            Transaccion.Pagado pagado = estado_pagoString.equals("PAGADA") ? Transaccion.Pagado.SI : Transaccion.Pagado.NO;

            String numero_nota = req.getParameter("numero_nota");
            String numero_factura = req.getParameter("numero_factura");
            String numero_cotizacion = req.getParameter("numero_cotizacion");
            String atiende_y_cobra = req.getParameter("atiende_y_cobra");

            String fecha_pedido = req.getParameter("fecha_pedido");
            Date sqlDate2;
            if (fecha_pedido != null && !fecha_pedido.isEmpty()) {
                // Convertir la cadena a LocalDate
                LocalDate localDate = LocalDate.parse(fecha_pedido);

                // Convertir LocalDate a java.sql.Date
                sqlDate2 = Date.valueOf(localDate);
            } else {
                // Usar la fecha actual como valor predeterminado
                LocalDate today = LocalDate.now();
                sqlDate2 = Date.valueOf(today);
            }

            Transaccion transaccion = new Transaccion(
                    Integer.parseInt(idTransaccion),
                    cliente,
                    verificacion,
                    tipo_pago,
                    numero_nota,
                    numero_cotizacion,
                    cuenta_deposito,
                    numero_factura,
                    pagado,
                    atiende_y_cobra,
                    sqlDate2
            );

            try {
                dao.update(transaccion);
                //Mandar mensaje de exito
                req.getSession().setAttribute("mensaje",new Mensaje(
                        Mensaje.TipoMensaje.SUCCESS,
                        new ArrayList<String>(
                                Arrays.asList(
                                        "La información se actualizo exitosamente"
                                )
                        )
                ));

                resp.sendRedirect("transacciones.jsp");

            } catch (Exception e) {
                logger.severe("Error al insertar transacción: " + e.getMessage());
                String mensajeError = "Ocurrió un error al procesar la transacción: " + e.getMessage();
                e.printStackTrace();
                req.setAttribute("error", mensajeError);
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        }else {
            logger.severe("Error, update transaccion sin ID");
            String mensajeError = "Error, update transaccion sin ID";
            req.setAttribute("error", mensajeError);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
