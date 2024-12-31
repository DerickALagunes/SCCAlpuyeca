package com.simor.sistemacontrolcobros.model.dao;

import com.simor.sistemacontrolcobros.model.Costo;
import com.simor.sistemacontrolcobros.utils.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CostoDAO implements CRUD {

    @Override
    public Costo findOne(int id) {
        Costo costo = new Costo();
        String query = "SELECT * FROM costos " +
                "where id_costo = ?";
        try (Connection con=DatabaseConnectionManager.getConnection()){
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet res = stmt.executeQuery()) {
                    if (res.next()) {
                        costo.setIdCosto(res.getInt("id_costo"));
                        costo.setMateria(res.getString("materia"));
                        costo.setCosto(res.getDouble("costo"));
                        costo.setCliente(res.getString("cliente_empresa"));
                        costo.setEncargado(res.getString("encargado"));
                        costo.setAtiendeYCobra(res.getString("atiende_y_cobra"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return costo;
    }

    @Override
    public List<Costo> findAll() {
        List<Costo> costos = new ArrayList<>();
        String query = "SELECT * FROM costos";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                try (ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        Costo costo = new Costo();

                        // Poblar el objeto Costo
                        costo.setIdCosto(res.getInt("id_costo"));
                        costo.setMateria(res.getString("materia"));
                        costo.setCosto(res.getDouble("costo"));
                        costo.setCliente(res.getString("cliente_empresa"));
                        costo.setEncargado(res.getString("encargado"));
                        costo.setAtiendeYCobra(res.getString("atiende_y_cobra"));

                        // Agregar a la lista
                        costos.add(costo);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return costos;
    }

    @Override
    public boolean insert(Object entity) {
        Costo costo = (Costo) entity;
        String query = "INSERT INTO costos (materia, costo, cliente_empresa, encargado, atiende_y_cobra) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Insertar costo
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, costo.getMateria());
                stmt.setDouble(2, costo.getCosto());
                stmt.setString(3, costo.getCliente());
                stmt.setString(4, costo.getEncargado());
                stmt.setString(5, costo.getAtiendeYCobra());
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
            throw new RuntimeException("Error al insertar el costo.", e);
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
        Costo costo = (Costo) entity;

        String query = "UPDATE costos SET materia = ?, costo= ?, cliente_empresa = ?, encargado = ?, atiende_y_cobra = ? WHERE id_costo = ?";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Actualizar costo
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, costo.getMateria());
                stmt.setDouble(2, costo.getCosto());
                stmt.setString(3, costo.getCliente());
                stmt.setString(4, costo.getEncargado());
                stmt.setString(5, costo.getAtiendeYCobra());
                stmt.setInt(6, costo.getIdCosto());
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
            throw new RuntimeException("Error al actualizar el costo.", e);
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
    public boolean delete(int idCosto) {
        String query = "DELETE FROM costos WHERE id_costo = ?";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Eliminar costo
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, idCosto);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("No se encontró ningún costo con el ID proporcionado.");
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
            throw new RuntimeException("Error al eliminar el costo.", e);
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

    public ArrayList<Costo> get(int start, int length, String searchTerm, String orderColumn, String orderDir) {
        ArrayList<Costo> list = new ArrayList<>();
        String query = "SELECT id_costo, materia, costo, cliente_empresa, encargado, atiende_y_cobra " +
                "FROM costos " +
                "WHERE materia LIKE ? OR costo LIKE ? OR cliente_empresa  LIKE ? OR encargado LIKE ? OR atiende_y_cobra LIKE ? " +
                "ORDER BY " + orderColumn + " " + orderDir + " LIMIT ? OFFSET ?;";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String searchPattern = "%" + searchTerm + "%";

                // Configurar parámetros de búsqueda
                for (int i = 1; i <= 5; i++) {
                    stmt.setString(i, searchPattern);
                }

                // Parámetros de paginación
                stmt.setInt(6, length);
                stmt.setInt(7, start);

                try (ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        Costo costo = new Costo();

                        // Poblar costo
                        costo.setIdCosto(res.getInt("id_costo"));
                        costo.setMateria(res.getString("materia"));
                        costo.setCosto(res.getDouble("costo"));
                        costo.setCliente(res.getString("cliente_empresa"));
                        costo.setEncargado(res.getString("encargado"));
                        costo.setAtiendeYCobra(res.getString("atiende_y_cobra"));

                        list.add(costo);
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
                "FROM costos " +
                "WHERE materia LIKE ? OR costo LIKE ? OR cliente_empresa  LIKE ? OR encargado LIKE ? OR atiende_y_cobra LIKE ?";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String searchPattern = "%" + searchTerm + "%";

                // Configurar parámetros de búsqueda
                for (int i = 1; i <= 5; i++) {
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
