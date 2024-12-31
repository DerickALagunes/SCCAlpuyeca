package com.simor.sistemacontrolcobros.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        try {
            // Cargar propiedades desde el archivo
            Properties properties = new Properties();
            try (InputStream input = DatabaseConnectionManager.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
                if (input == null) {
                    throw new IOException("No se pudo encontrar el archivo dbconfig.properties");
                }
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Error cargando las propiedades de la base de datos", e);
            }

            // Cargar el driver
            String driverClassName = properties.getProperty("db.driverClassName");
            System.out.println(properties.getProperty("db.driverClassName"));
            Class.forName(driverClassName);

            // Configurar HikariCP
            String jdbcUrl = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);

            // Ajustar la configuración del pool de conexiones
            config.setMaximumPoolSize(50);
            config.setMinimumIdle(10);
            config.setIdleTimeout(300000);
            config.setConnectionTimeout(30000);
            config.setMaxLifetime(600000); // 60 seconds

            dataSource = new HikariDataSource(config);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DatabaseConnectionManager() {
        // Constructor privado para evitar la creación de instancias
    }
}