package com.simor.sistemacontrolcobros.controller;

import com.google.gson.Gson;
import com.simor.sistemacontrolcobros.model.dao.FiltroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "filtros", value ="/filtros")
public class FiltrosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crear el DAO y obtener los valores únicos
        FiltroDAO dao = new FiltroDAO();
        Map<Integer, List<String>> valoresUnicos = dao.valores();

        // Establecer el tipo de respuesta a JSON y la codificación de caracteres
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Crear una instancia de Gson para convertir el mapa en JSON
        Gson gson = new Gson();

        // Escribir la respuesta JSON directamente en el flujo de salida
        gson.toJson(valoresUnicos, response.getWriter());

        // No es necesario utilizar PrintWriter aquí, ya que gson.toJson() ya lo está haciendo
    }
}