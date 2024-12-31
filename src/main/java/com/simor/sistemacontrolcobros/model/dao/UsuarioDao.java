package com.simor.sistemacontrolcobros.model.dao;

import com.simor.sistemacontrolcobros.model.Usuario;
import com.simor.sistemacontrolcobros.utils.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    public Object findOne(String correo, String contra) {
        Usuario usr = new Usuario();
        String query = "select usuarios.* from usuarios where email = ? AND contrasena = sha2(?,256)";
        //String query = "select usuarios.* from usuarios where nombre_usuario = ? AND contrasena = ?";
        try(Connection con = DatabaseConnectionManager.getConnection()){
            try(PreparedStatement stmt = con.prepareStatement(query)){
                stmt.setString(1,correo);
                stmt.setString(2,contra);
                try (ResultSet res = stmt.executeQuery()){
                    if(res.next()){
                        usr.setId_usuario(res.getInt("id_usuario"));
                        usr.setNombre_usuario(res.getString("nombre_usuario"));
                        usr.setEmail(res.getString(("email")));
                        usr.setContrasena(res.getString("contrasena"));
                        usr.setTipo_usuario(res.getString("tipo_usuario"));
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usr;
    }

}
