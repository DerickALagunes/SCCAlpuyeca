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
import java.util.ArrayList;
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
                "pendiente",
                "fecha_pedido"
        };

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
        ArrayList<Transaccion> dataList = dao.get(start, length, searchTerm, orderColumn, orderDir);
        int total = dao.getCount(searchTerm);

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
}
