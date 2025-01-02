package com.simor.sistemacontrolcobros.controller;

import com.google.gson.Gson;
import com.simor.sistemacontrolcobros.model.DataTableResponse;
import com.simor.sistemacontrolcobros.model.Transaccion;
import com.simor.sistemacontrolcobros.model.dao.TransaccionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "ver_transaccion", value = "/ver_transacciones")
public class VerTransaccionServlet extends HttpServlet {
    private TransaccionDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new TransaccionDAO();
    }
    private static final Logger logger = Logger.getLogger(VerTransaccionServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int draw = Integer.parseInt(request.getParameter("draw"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));

        String searchTerm = request.getParameter("search[value]");

        // Obtener parámetros de ordenación
        String orderColumnIndex = request.getParameter("order[0][column]");
        String orderDir = request.getParameter("order[0][dir]");
        String[] columnNames = {
                "gestor",
                "razon_social",
                "placa",
                "serie",
                "materia",
                "verificentro",
                "precio",
                "tipo_pago",
                "numero_nota",
                "cotizacion",
                "fecha_folio",
                "folio",
                "cuenta_deposito",
                "numero_factura",
                "pagado",
                "fecha_pedido"
        };

        List<String> searchValues = new ArrayList<>();
        for (int i = 0; i < columnNames.length; i++) {
            if(i == 10 || i == 16){
                String searchValue = request.getParameter("columns[" + i + "][search][value]");
                // Validar y convertir si es una fecha
                if (searchValue != null && !searchValue.isEmpty()) {
                    try {
                        // Intentar parsear la fecha
                        Date parsedDate = parseDate(searchValue);
                        searchValue = parsedDate.toString(); // Convertir a formato esperado por la base de datos
                    } catch (IllegalArgumentException e) {
                        // Si no es una fecha válida, mantener el valor original
                        // Opcional: Puedes decidir si ignorar o lanzar una excepción
                    }
                }
                searchValues.add(searchValue);
            }else{
                String searchValue = request.getParameter("columns[" + i + "][search][value]");
                searchValues.add(searchValue);
            }
        }

        if(orderDir == null){
            orderDir = "asc";
        }

        String orderColumn = "id_transaccion";
        if(orderColumnIndex != null){
            orderColumn = columnNames[Integer.parseInt(orderColumnIndex)];
            if(orderColumn.equals("pendiente")){
                orderColumn = "pagado";
                if(orderDir.equals("asc")){
                    orderDir = "desc";
                }else{
                    orderDir = "asc";
                }
            }
        }

        // Aquí deberías obtener los datos desde tu base de datos
        ArrayList<Transaccion> dataList = dao.get(start, length, orderColumn, orderDir, searchValues, columnNames , searchTerm);
        int total = dao.getCount(searchValues, columnNames ,searchTerm);

        DataTableResponse dataTableResponse = new DataTableResponse();
        dataTableResponse.setDraw(draw);
        dataTableResponse.setRecordsTotal(total);
        dataTableResponse.setRecordsFiltered(total);
        dataTableResponse.setData(dataList);

        Gson gson = new Gson();
        String json = gson.toJson(dataTableResponse);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    public Date parseDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM d, yyyy"); // Formato recibido
            java.util.Date parsedDate = inputFormat.parse(dateString);
            return new Date(parsedDate.getTime()); // Convertir a java.sql.Date
        } catch (ParseException e) {
            throw new IllegalArgumentException("Fecha no válida: " + dateString, e);
        }
    }
}
