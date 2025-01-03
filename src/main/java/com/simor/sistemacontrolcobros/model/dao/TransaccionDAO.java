package com.simor.sistemacontrolcobros.model.dao;

import com.simor.sistemacontrolcobros.model.Cliente;
import com.simor.sistemacontrolcobros.model.Transaccion;
import com.simor.sistemacontrolcobros.model.Vehiculo;
import com.simor.sistemacontrolcobros.model.Verificacion;
import com.simor.sistemacontrolcobros.utils.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO implements CRUD {

    @Override
    public Transaccion findOne(int id) {
        Cliente cliente = new Cliente();
        Vehiculo vehiculo = new Vehiculo();
        Verificacion verificacion = new Verificacion();
        Transaccion transaccion = new Transaccion();
        String query = "SELECT t.id_transaccion, t.id_cliente, t.id_verificacion, t.tipo_pago, t.numero_nota, t.cotizacion, t.cuenta_deposito, t.numero_factura, t.pagado, t.atiende_y_cobra, t.fecha_pedido, v.id_vehiculo, v.materia, v.verificentro, v.precio, v.fecha_folio, v.folio, v2.placa, v2.serie, c.gestor, c.razon_social FROM transacciones t JOIN verificaciones v ON v.id_verificacion = t.id_verificacion JOIN vehiculos v2 ON v2.id_vehiculo = v.id_vehiculo JOIN clientes c ON c.id_cliente = t.id_cliente " +
                "where t.id_transaccion = ?";
        try (Connection con=DatabaseConnectionManager.getConnection()){
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet res = stmt.executeQuery()) {
                    if (res.next()) {
                        // Poblar cliente
                        cliente.setIdCliente(res.getInt("id_cliente"));
                        cliente.setGestor(res.getString("gestor"));
                        cliente.setRazonSocial(res.getString("razon_social"));

                        // Poblar vehículo
                        vehiculo.setIdVehiculo(res.getInt("id_vehiculo"));
                        vehiculo.setPlaca(res.getString("placa"));
                        vehiculo.setSerie(res.getString("serie"));

                        // Poblar verificación
                        verificacion.setIdVerificacion(res.getInt("id_verificacion"));
                        verificacion.setVehiculo(vehiculo);
                        verificacion.setMateria(res.getString("materia"));
                        verificacion.setVerificentro(res.getString("verificentro"));
                        verificacion.setPrecio(res.getDouble("precio"));
                        verificacion.setFechaFolio(res.getDate("fecha_folio"));
                        verificacion.setFolio(res.getString("folio"));

                        // Poblar transacción
                        transaccion.setIdTransaccion(res.getInt("id_transaccion"));
                        transaccion.setTipoPago(res.getString("tipo_pago"));
                        transaccion.setNumeroNota(res.getString("numero_nota"));
                        transaccion.setCotizacion(res.getString("cotizacion"));
                        transaccion.setCuentaDeposito(res.getString("cuenta_deposito"));
                        transaccion.setNumeroFactura(res.getString("numero_factura"));
                        transaccion.setPagado(res.getString("pagado"));
                        transaccion.setAtiendeYCobra(res.getString("atiende_y_cobra"));
                        transaccion.setFechaPedido(res.getDate("fecha_pedido"));

                        // Asociar objetos relacionados
                        transaccion.setCliente(cliente);
                        transaccion.setVerificacion(verificacion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transaccion;
    }

    @Override
    public List findAll() {
        List<Transaccion> list = new ArrayList<>();
        String query = "SELECT t.id_transaccion, t.id_cliente, t.id_verificacion, t.tipo_pago, t.numero_nota, t.cotizacion, t.cuenta_deposito, t.numero_factura, t.pagado, t.atiende_y_cobra, t.fecha_pedido, v.id_vehiculo, v.materia, v.verificentro, v.precio, v.fecha_folio, v.folio, v2.placa, v2.serie, c.gestor, c.razon_social FROM transacciones t JOIN verificaciones v ON v.id_verificacion = t.id_verificacion JOIN vehiculos v2 ON v2.id_vehiculo = v.id_vehiculo JOIN clientes c ON c.id_cliente = t.id_cliente";
        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                try (ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        Cliente cliente = new Cliente();
                        Vehiculo vehiculo = new Vehiculo();
                        Verificacion verificacion = new Verificacion();
                        Transaccion transaccion = new Transaccion();

                        // Poblar cliente
                        cliente.setIdCliente(res.getInt("id_cliente"));
                        cliente.setGestor(res.getString("gestor"));
                        cliente.setRazonSocial(res.getString("razon_social"));

                        // Poblar vehículo
                        vehiculo.setIdVehiculo(res.getInt("id_vehiculo"));
                        vehiculo.setPlaca(res.getString("placa"));
                        vehiculo.setSerie(res.getString("serie"));

                        // Poblar verificación
                        verificacion.setIdVerificacion(res.getInt("id_verificacion"));
                        verificacion.setVehiculo(vehiculo);
                        verificacion.setMateria(res.getString("materia"));
                        verificacion.setVerificentro(res.getString("verificentro"));
                        verificacion.setPrecio(res.getDouble("precio"));
                        verificacion.setFechaFolio(res.getDate("fecha_folio"));
                        verificacion.setFolio(res.getString("folio"));

                        // Poblar transacción
                        transaccion.setIdTransaccion(res.getInt("id_transaccion"));
                        transaccion.setCliente(cliente);
                        transaccion.setVerificacion(verificacion);
                        transaccion.setTipoPago(res.getString("tipo_pago"));
                        transaccion.setNumeroNota(res.getString("numero_nota"));
                        transaccion.setCotizacion(res.getString("cotizacion"));
                        transaccion.setCuentaDeposito(res.getString("cuenta_deposito"));
                        transaccion.setNumeroFactura(res.getString("numero_factura"));
                        transaccion.setPagado(res.getString("pagado"));
                        transaccion.setAtiendeYCobra(res.getString("atiende_y_cobra"));
                        transaccion.setFechaPedido(res.getDate("fecha_pedido"));

                        // Asociar objetos relacionados
                        transaccion.setCliente(cliente);
                        transaccion.setVerificacion(verificacion);

                        list.add(transaccion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean insert(Object entity) {
        Transaccion transaccion = (Transaccion) entity;

        String queryCliente = "INSERT INTO clientes (gestor, razon_social) VALUES (?, ?)";
        String queryVehiculo = "INSERT INTO vehiculos (placa, serie, id_cliente) VALUES (?, ?, ?)";
        String queryVerificacion = "INSERT INTO verificaciones (id_vehiculo, materia, verificentro, precio, fecha_folio, folio) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String queryTransaccion = "INSERT INTO transacciones (id_cliente, id_verificacion, tipo_pago, numero_nota, cotizacion, cuenta_deposito, numero_factura, pagado, atiende_y_cobra, fecha_pedido) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            int idCliente, idVehiculo, idVerificacion;

            // Insertar cliente y obtener ID generado
            try (PreparedStatement stmt = con.prepareStatement(queryCliente, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, transaccion.getCliente().getGestor());
                stmt.setString(2, transaccion.getCliente().getRazonSocial());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idCliente = rs.getInt(1);
                    } else {
                        con.rollback();
                        throw new SQLException("No se pudo obtener el ID del cliente.");
                    }
                }
            }

            // Insertar vehículo y obtener ID generado
            try (PreparedStatement stmt = con.prepareStatement(queryVehiculo, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, transaccion.getVerificacion().getVehiculo().getPlaca());
                stmt.setString(2, transaccion.getVerificacion().getVehiculo().getSerie());
                stmt.setInt(3, idCliente);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idVehiculo = rs.getInt(1);
                    } else {
                        con.rollback();
                        throw new SQLException("No se pudo obtener el ID del vehículo.");
                    }
                }
            }

            // Insertar verificación y obtener ID generado
            try (PreparedStatement stmt = con.prepareStatement(queryVerificacion, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, idVehiculo);
                stmt.setString(2, transaccion.getVerificacion().getMateria().toString());
                stmt.setString(3, transaccion.getVerificacion().getVerificentro());
                stmt.setDouble(4, transaccion.getVerificacion().getPrecio());
                stmt.setDate(5, transaccion.getVerificacion().getFechaFolio());
                stmt.setString(6, transaccion.getVerificacion().getFolio());
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idVerificacion = rs.getInt(1);
                    } else {
                        con.rollback();
                        throw new SQLException("No se pudo obtener el ID de la verificación.");
                    }
                }
            }

            // Insertar transacción
            try (PreparedStatement stmt = con.prepareStatement(queryTransaccion)) {
                stmt.setInt(1, idCliente);
                stmt.setInt(2, idVerificacion);
                stmt.setString(3, transaccion.getTipoPago().toString());
                stmt.setString(4, transaccion.getNumeroNota());
                stmt.setString(5, transaccion.getCotizacion());
                stmt.setString(6, transaccion.getCuentaDeposito());
                stmt.setString(7, transaccion.getNumeroFactura());
                String pagado = transaccion.getPagado() == Transaccion.Pagado.SI ? "SI" : "NO";
                stmt.setString(8, pagado);
                stmt.setString(9, transaccion.getAtiendeYCobra());
                stmt.setDate(10, transaccion.getFechaPedido());
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
            throw new RuntimeException("Error al insertar la transacción.", e);
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
        Transaccion transaccion = (Transaccion) entity;
        String queryCliente = "UPDATE clientes SET gestor = ?, razon_social = ? WHERE id_cliente = ?";
        String queryVehiculo = "UPDATE vehiculos SET placa = ?, serie = ? WHERE id_vehiculo = ?";
        String queryVerificacion = "UPDATE verificaciones SET materia = ?, verificentro = ?, precio = ?, fecha_folio = ?, folio = ? " +
                "WHERE id_verificacion = ?";
        String queryTransaccion = "UPDATE transacciones SET tipo_pago = ?, numero_nota = ?, cotizacion = ?, cuenta_deposito = ?, " +
                "numero_factura = ?, pagado = ?, atiende_y_cobra = ?, fecha_pedido = ? WHERE id_transaccion = ?";

        Connection con = null;
        try {
            con = DatabaseConnectionManager.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Actualizar cliente
            try (PreparedStatement stmt = con.prepareStatement(queryCliente)) {
                stmt.setString(1, transaccion.getCliente().getGestor());
                stmt.setString(2, transaccion.getCliente().getRazonSocial());
                stmt.setInt(3, transaccion.getCliente().getIdCliente());
                stmt.executeUpdate();
            }

            // Actualizar vehículo
            try (PreparedStatement stmt = con.prepareStatement(queryVehiculo)) {
                stmt.setString(1, transaccion.getVerificacion().getVehiculo().getPlaca());
                stmt.setString(2, transaccion.getVerificacion().getVehiculo().getSerie());
                stmt.setInt(3, transaccion.getVerificacion().getVehiculo().getIdVehiculo());
                stmt.executeUpdate();
            }

            // Actualizar verificación
            try (PreparedStatement stmt = con.prepareStatement(queryVerificacion)) {
                stmt.setString(1, transaccion.getVerificacion().getMateria().toString());
                stmt.setString(2, transaccion.getVerificacion().getVerificentro());
                stmt.setDouble(3, transaccion.getVerificacion().getPrecio());
                stmt.setDate(4, transaccion.getVerificacion().getFechaFolio());
                stmt.setString(5, transaccion.getVerificacion().getFolio());
                stmt.setInt(6, transaccion.getVerificacion().getIdVerificacion());
                stmt.executeUpdate();
            }

            // Actualizar transacción
            try (PreparedStatement stmt = con.prepareStatement(queryTransaccion)) {
                stmt.setString(1, transaccion.getTipoPago().toString());
                stmt.setString(2, transaccion.getNumeroNota());
                stmt.setString(3, transaccion.getCotizacion());
                stmt.setString(4, transaccion.getCuentaDeposito());
                stmt.setString(5, transaccion.getNumeroFactura());
                String pagado = transaccion.getPagado() == Transaccion.Pagado.SI ? "SI" : "NO";
                stmt.setString(6, pagado);
                stmt.setString(7, transaccion.getAtiendeYCobra());
                stmt.setDate(8, transaccion.getFechaPedido());
                stmt.setInt(9, transaccion.getIdTransaccion());
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
            throw new RuntimeException("Error al actualizar la transacción.", e);
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
    public boolean delete(int id) {
        String query = "DELETE FROM transacciones WHERE id_transaccion = ?";
        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la transacción con ID " + id, e);
        }
    }

    public ArrayList<Transaccion> get(int start, int length, String orderColumn, String orderDir, List<String> searchValues, String[] columnNames, String searchTerm) {
        ArrayList<Transaccion> list = new ArrayList<>();

        // Construir cláusula WHERE dinámica
        StringBuilder whereClause = new StringBuilder(" WHERE 1=1 ");
        for (int i = 0; i < searchValues.size(); i++) {
            if (searchValues.get(i) != null && !searchValues.get(i).isEmpty()) {
                whereClause.append(" AND ").append(columnNames[i]).append(" LIKE ?");
            }
        }

        // Búsqueda global con searchTerm
        if (searchTerm != null && !searchTerm.isEmpty()) {
            whereClause.append(" AND (");
            for (int i = 0; i < columnNames.length; i++) {
                if (i > 0) {
                    whereClause.append(" OR ");
                }
                whereClause.append(columnNames[i]).append(" LIKE ?");
            }
            whereClause.append(")");
        }

        String query = "SELECT t.id_transaccion, t.id_cliente, t.id_verificacion, t.tipo_pago, t.numero_nota, " +
                "t.cotizacion, t.cuenta_deposito, t.numero_factura, t.pagado, " +
                "CASE " +
                "WHEN t.pagado = 'SI' THEN 'NO' " +
                "WHEN t.pagado = 'NO' THEN 'SI' " +
                "ELSE t.pagado " +
                "END AS pendiente, t.atiende_y_cobra, t.fecha_pedido, " +
                "v.id_vehiculo, v.materia, v.verificentro, v.precio, v.fecha_folio, v.folio, v2.placa, v2.serie, " +
                "c.gestor, c.razon_social " +
                "FROM transacciones t " +
                "JOIN verificaciones v ON v.id_verificacion = t.id_verificacion " +
                "JOIN vehiculos v2 ON v2.id_vehiculo = v.id_vehiculo " +
                "JOIN clientes c ON c.id_cliente = t.id_cliente " +
                whereClause.toString() +
                " ORDER BY " + orderColumn + " " + orderDir + " LIMIT ? OFFSET ?";

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                int paramIndex = 1;

                // Agregar parámetros específicos por columna
                for (String value : searchValues) {
                    if (value != null && !value.isEmpty()) {
                        stmt.setString(paramIndex++, "%" + value + "%");
                    }
                }

                // Agregar parámetros de búsqueda global (searchTerm)
                if (searchTerm != null && !searchTerm.isEmpty()) {
                    for (String columnName : columnNames) {
                        stmt.setString(paramIndex++, "%" + searchTerm + "%");
                    }
                }

                // Configurar parámetros de paginación
                stmt.setInt(paramIndex++, length);
                stmt.setInt(paramIndex, start);

                try (ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        Cliente cliente = new Cliente();
                        Vehiculo vehiculo = new Vehiculo();
                        Verificacion verificacion = new Verificacion();
                        Transaccion transaccion = new Transaccion();

                        // Poblar cliente
                        cliente.setIdCliente(res.getInt("id_cliente"));

                        String gestor = res.getString("gestor");
                        cliente.setGestor(gestor != null ? gestor:"<span class=\"text-danger\">No registrado</span>");

                        String razon_social = res.getString("razon_social");
                        cliente.setRazonSocial(razon_social != null ? razon_social:"<span class=\"text-danger\">No registrada</span>");

                        // Poblar vehículo
                        vehiculo.setIdVehiculo(res.getInt("id_vehiculo"));

                        String placa = res.getString("placa");
                        vehiculo.setPlaca(placa != null ? placa:"<span class=\"text-danger\">No registrada</span>");

                        String serie = res.getString("serie");
                        vehiculo.setSerie(serie != null ? serie:"<span class=\"text-danger\">No registrada</span>");

                        vehiculo.setCliente(cliente);

                        // Poblar verificación
                        verificacion.setIdVerificacion(res.getInt("id_verificacion"));
                        verificacion.setVehiculo(vehiculo);

                        String materia = res.getString("materia");
                        verificacion.setMateria(materia != null ? materia:"<span class=\"text-danger\">No registrada</span>");

                        String verificentro = res.getString("verificentro");
                        verificacion.setVerificentro(verificentro != null ? verificentro:"<span class=\"text-danger\">No registrado</span>");

                        verificacion.setPrecio(res.getDouble("precio"));
                        verificacion.setFechaFolio(res.getDate("fecha_folio"));

                        String folio = res.getString("folio");
                        verificacion.setFolio(folio != null ? folio:"<span class=\"text-danger\">No registrado</span>");


                        // Poblar transacción
                        transaccion.setIdTransaccion(res.getInt("id_transaccion"));
                        transaccion.setTipoPago(res.getString("tipo_pago"));
                        transaccion.setNumeroNota(res.getString("numero_nota"));
                        transaccion.setCotizacion(res.getString("cotizacion"));
                        transaccion.setCuentaDeposito(res.getString("cuenta_deposito"));
                        transaccion.setNumeroFactura(res.getString("numero_factura"));
                        transaccion.setPagado(res.getString("pagado"));
                        transaccion.setAtiendeYCobra(res.getString("atiende_y_cobra"));
                        transaccion.setFechaPedido(res.getDate("fecha_pedido"));

                        // Asociar objetos relacionados
                        transaccion.setCliente(cliente);
                        transaccion.setVerificacion(verificacion);

                        list.add(transaccion);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getCount(List<String> searchValues, String[] columnNames, String searchTerm) {
        int count = 0;

        // Construir cláusula WHERE dinámica
        StringBuilder whereClause = new StringBuilder(" WHERE 1=1 ");
        for (int i = 0; i < searchValues.size(); i++) {
            if (searchValues.get(i) != null && !searchValues.get(i).isEmpty()) {
                whereClause.append(" AND ").append(columnNames[i]).append(" LIKE ?");
            }
        }

        // Búsqueda global con searchTerm
        if (searchTerm != null && !searchTerm.isEmpty()) {
            whereClause.append(" AND (");
            for (int i = 0; i < columnNames.length; i++) {
                if (i > 0) {
                    whereClause.append(" OR ");
                }
                whereClause.append(columnNames[i]).append(" LIKE ?");
            }
            whereClause.append(")");
        }

        // Consulta SQL para contar registros con búsqueda dinámica
        String query = "SELECT COUNT(*) AS total " +
                "FROM transacciones t " +
                "JOIN verificaciones v ON v.id_verificacion = t.id_verificacion " +
                "JOIN vehiculos v2 ON v2.id_vehiculo = v.id_vehiculo " +
                "JOIN clientes c ON c.id_cliente = t.id_cliente " +
                whereClause.toString();

        try (Connection con = DatabaseConnectionManager.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                int paramIndex = 1;

                // Agregar parámetros específicos por columna
                for (String value : searchValues) {
                    if (value != null && !value.isEmpty()) {
                        stmt.setString(paramIndex++, "%" + value + "%");
                    }
                }

                // Agregar parámetros de búsqueda global (searchTerm)
                if (searchTerm != null && !searchTerm.isEmpty()) {
                    for (String columnName : columnNames) {
                        stmt.setString(paramIndex++, "%" + searchTerm + "%");
                    }
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
