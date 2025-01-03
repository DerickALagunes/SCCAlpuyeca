package com.simor.sistemacontrolcobros.model.dao;

import com.simor.sistemacontrolcobros.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiltroDAO {

    public Map<Integer, List<String>> valores(){
        // Obtener los valores únicos de cada columna de la base de datos
        Map<Integer, List<String>> valoresUnicos = new HashMap<>();

        // Consultas a la base de datos para obtener los valores únicos de cada columna
        try (Connection conn = DatabaseConnectionManager.getConnection()) {
            // Consultas para obtener los valores únicos de las columnas
            String queryTipoPago = "SELECT DISTINCT tipo_pago FROM transacciones";
            String queryNumeroNota = "SELECT DISTINCT numero_nota FROM transacciones";
            String queryCotizacion = "SELECT DISTINCT cotizacion FROM transacciones";
            String queryCuentaDeposito = "SELECT DISTINCT cuenta_deposito FROM transacciones";
            String queryNumeroFactura = "SELECT DISTINCT numero_factura FROM transacciones";
            String queryPagado = "SELECT DISTINCT pagado FROM transacciones";
            String queryAtiendeYCobra = "SELECT DISTINCT atiende_y_cobra FROM transacciones";
            String queryFechaPedido = "SELECT DISTINCT fecha_pedido FROM transacciones";

            String queryMateria = "SELECT DISTINCT materia FROM verificaciones";
            String queryVerificentro = "SELECT DISTINCT verificentro FROM verificaciones";
            String queryPrecio = "SELECT DISTINCT precio FROM verificaciones";
            String queryFechaFolio = "SELECT DISTINCT fecha_folio FROM verificaciones";
            String queryFolio = "SELECT DISTINCT folio FROM verificaciones";

            String queryPlaca = "SELECT DISTINCT placa FROM vehiculos";
            String querySerie = "SELECT DISTINCT serie FROM vehiculos";

            String queryGestor = "SELECT DISTINCT gestor FROM clientes";
            String queryRazonSocial = "SELECT DISTINCT razon_social FROM clientes";

            // Obtener los valores únicos para cada columna
            valoresUnicos.put(0, obtenerValoresUnicos(conn, queryGestor));
            valoresUnicos.put(1, obtenerValoresUnicos(conn, queryRazonSocial));
            valoresUnicos.put(2, obtenerValoresUnicos(conn, queryPlaca));
            valoresUnicos.put(3, obtenerValoresUnicos(conn, querySerie));
            valoresUnicos.put(4, obtenerValoresUnicos(conn, queryMateria));
            valoresUnicos.put(5, obtenerValoresUnicos(conn, queryVerificentro));
            valoresUnicos.put(6, obtenerValoresUnicos(conn, queryPrecio));
            valoresUnicos.put(7, obtenerValoresUnicos(conn, queryTipoPago));

            valoresUnicos.put(8, obtenerValoresUnicos(conn, queryNumeroNota));
            valoresUnicos.put(9, obtenerValoresUnicos(conn, queryCotizacion));
            valoresUnicos.put(10, obtenerValoresUnicos(conn, queryFechaFolio));
            valoresUnicos.put(11, obtenerValoresUnicos(conn, queryFolio));
            valoresUnicos.put(12, obtenerValoresUnicos(conn, queryCuentaDeposito));

            valoresUnicos.put(13, obtenerValoresUnicos(conn, queryNumeroFactura));

            ArrayList<String> valores = new ArrayList<>();
            valores.add("SI");
            valores.add("NO");
            valoresUnicos.put(14, valores);
            valoresUnicos.put(15, valores);

            valoresUnicos.put(16, obtenerValoresUnicos(conn, queryFechaPedido));

            return valoresUnicos;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Método para ejecutar la consulta y obtener los valores únicos de una columna
    private List<String> obtenerValoresUnicos(Connection conn, String query) throws SQLException {
        List<String> valoresUnicos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                valoresUnicos.add(rs.getString(1));
            }
        }
        return valoresUnicos;
    }
}
