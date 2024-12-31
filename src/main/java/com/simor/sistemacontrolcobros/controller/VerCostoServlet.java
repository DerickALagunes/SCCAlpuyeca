package com.simor.sistemacontrolcobros.controller;

import com.google.gson.Gson;
import com.simor.sistemacontrolcobros.model.DataTableCostoResponse;
import com.simor.sistemacontrolcobros.model.Costo;
import com.simor.sistemacontrolcobros.model.dao.CostoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet(name = "ver_costo", value = "/ver_costos")
public class VerCostoServlet extends HttpServlet {
    private CostoDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new CostoDAO();
    }
    private static final Logger logger = Logger.getLogger(VerCostoServlet.class.getName());

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
                "materia",
                "costo",
                "cliente_empresa",
                "encargado",
                "atiende_y_cobra"
        };

        if(orderDir == null){
            orderDir = "asc";
        }

        String orderColumn = "id_costo";
        if(orderColumnIndex != null){
            orderColumn = columnNames[Integer.parseInt(orderColumnIndex)];
        }


        // Aquí deberías obtener los datos desde tu base de datos
        ArrayList<Costo> dataList = dao.get(start, length, searchTerm, orderColumn, orderDir);
        int total = dao.getCount(searchTerm);

        DataTableCostoResponse dataTableResponse = new DataTableCostoResponse();
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
