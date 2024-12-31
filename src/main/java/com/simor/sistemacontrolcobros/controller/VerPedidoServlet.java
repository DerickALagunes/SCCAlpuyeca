package com.simor.sistemacontrolcobros.controller;

import com.google.gson.Gson;
import com.simor.sistemacontrolcobros.model.DataTablePedidoResponse;
import com.simor.sistemacontrolcobros.model.Pedido;
import com.simor.sistemacontrolcobros.model.dao.PedidoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet(name = "ver_pedido", value = "/ver_pedidos")
public class VerPedidoServlet extends HttpServlet {
    private PedidoDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new PedidoDAO();
    }
    private static final Logger logger = Logger.getLogger(VerPedidoServlet.class.getName());

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
                "fecha_envio",
                "numero_guia",
                "recibio",
                "foto"
        };

        if(orderDir == null){
            orderDir = "asc";
        }

        String orderColumn = "id_pedido";
        if(orderColumnIndex != null){
            orderColumn = columnNames[Integer.parseInt(orderColumnIndex)];
        }


        // Aquí deberías obtener los datos desde tu base de datos
        ArrayList<Pedido> dataList = dao.get(start, length, searchTerm, orderColumn, orderDir);
        int total = dao.getCount(searchTerm);

        DataTablePedidoResponse dataTableResponse = new DataTablePedidoResponse();
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
