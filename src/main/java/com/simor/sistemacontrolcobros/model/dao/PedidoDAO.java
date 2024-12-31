package com.simor.sistemacontrolcobros.model.dao;

import com.simor.sistemacontrolcobros.model.*;
import com.simor.sistemacontrolcobros.utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements CRUD {

    @Override
    public Pedido findOne(int id) {
        Pedido pedido = new Pedido();
        String query = "SELECT * FROM pedidos " +
                "where id_pedido = ?";
        try (Connection con=DatabaseConnectionManager.getConnection()){
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet res = stmt.executeQuery()) {
                    if (res.next()) {
                        pedido.setIdPedido(res.getInt("id_pedido"));
                        pedido.setFechaEnvio(res.getDate("fecha_envio"));
                        pedido.setNumeroGuia(res.getString("numero_guia"));
                        pedido.setRecibio(res.getString("recibio"));
                        pedido.setFoto(res.getString("foto"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pedido;
    }

    @Override
    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM pedidos";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                try (ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        Pedido pedido = new Pedido();

                        // Poblar el objeto Pedido
                        pedido.setIdPedido(res.getInt("id_pedido"));
                        pedido.setFechaEnvio(res.getDate("fecha_envio"));
                        pedido.setNumeroGuia(res.getString("numero_guia"));
                        pedido.setRecibio(res.getString("recibio"));
                        pedido.setFoto(res.getString("foto"));

                        // Agregar a la lista
                        pedidos.add(pedido);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pedidos;
    }

    @Override
    public boolean insert(Object entity) {
        Pedido pedido = (Pedido) entity;
        String query = "INSERT INTO pedidos (fecha_envio, numero_guia, recibio, foto) " +
                "VALUES (?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Insertar pedido
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setDate(1, pedido.getFechaEnvio());
                stmt.setString(2, pedido.getNumeroGuia());
                stmt.setString(3, pedido.getRecibio());
                stmt.setString(4, pedido.getFoto());
                stmt.executeUpdate();
            }

            con.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Revertir cambios en caso de error
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException("Error al revertir la transacción.", rollbackEx);
                }
            }
            throw new RuntimeException("Error al insertar el pedido.", e);
        } finally {
            if (con != null) {
                try {
                    con.close(); // Cerrar conexión manualmente
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión.", closeEx);
                }
            }
        }
    }



    @Override
    public boolean update(Object entity) {
        Pedido pedido = (Pedido) entity;

        String query = "UPDATE pedidos SET fecha_envio = ?, numero_guia = ?, recibio = ?, foto = ? WHERE id_pedido = ?";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Actualizar pedido
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setDate(1, pedido.getFechaEnvio());
                stmt.setString(2, pedido.getNumeroGuia());
                stmt.setString(3, pedido.getRecibio());
                stmt.setString(4, pedido.getFoto());
                stmt.setInt(5, pedido.getIdPedido());
                stmt.executeUpdate();
            }

            con.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Revertir cambios en caso de error
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException("Error al revertir la transacción.", rollbackEx);
                }
            }
            throw new RuntimeException("Error al actualizar el pedido.", e);
        } finally {
            if (con != null) {
                try {
                    con.close(); // Cerrar conexión manualmente
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión.", closeEx);
                }
            }
        }
    }


    @Override
    public boolean delete(int idPedido) {
        String query = "DELETE FROM pedidos WHERE id_pedido = ?";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Eliminar pedido
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, idPedido);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("No se encontró ningún pedido con el ID proporcionado.");
                }
            }

            con.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Revertir cambios en caso de error
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException("Error al revertir la transacción.", rollbackEx);
                }
            }
            throw new RuntimeException("Error al eliminar el pedido.", e);
        } finally {
            if (con != null) {
                try {
                    con.close(); // Cerrar conexión manualmente
                } catch (SQLException closeEx) {
                    throw new RuntimeException("Error al cerrar la conexión.", closeEx);
                }
            }
        }
    }

    public ArrayList<Pedido> get(int start, int length, String searchTerm, String orderColumn, String orderDir) {
        ArrayList<Pedido> list = new ArrayList<>();
        String query = "SELECT id_pedido, fecha_envio, numero_guia, recibio, foto " +
                "FROM pedidos " +
                "WHERE fecha_envio LIKE ? OR recibio LIKE ? OR numero_guia  LIKE ? OR foto LIKE ? " +
                "ORDER BY " + orderColumn + " " + orderDir + " LIMIT ? OFFSET ?;";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String searchPattern = "%" + searchTerm + "%";

                // Configurar parámetros de búsqueda
                for (int i = 1; i <= 4; i++) {
                    stmt.setString(i, searchPattern);
                }

                // Parámetros de paginación
                stmt.setInt(5, length);
                stmt.setInt(6, start);

                try (ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        Pedido pedido = new Pedido();

                        // Poblar pedido
                        pedido.setIdPedido(res.getInt("id_pedido"));
                        pedido.setFechaEnvio(res.getDate("fecha_envio"));
                        pedido.setNumeroGuia(res.getString("numero_guia"));
                        pedido.setRecibio(res.getString("recibio"));
                        pedido.setFoto(res.getString("foto"));

                        list.add(pedido);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getCount(String searchTerm) {
        int count = 0;

        String query = "SELECT COUNT(*) AS total " +
                "FROM pedidos " +
                "WHERE fecha_envio LIKE ? OR recibio LIKE ? OR numero_guia LIKE ? OR foto LIKE ?";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String searchPattern = "%" + searchTerm + "%";

                // Configurar parámetros de búsqueda
                for (int i = 1; i <= 4; i++) {
                    stmt.setString(i, searchPattern);
                }

                // Ejecutar consulta y obtener el conteo
                try (ResultSet res = stmt.executeQuery()) {
                    if (res.next()) {
                        count = res.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }


}
