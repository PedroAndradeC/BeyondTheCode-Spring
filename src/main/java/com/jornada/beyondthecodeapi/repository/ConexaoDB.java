package com.jornada.beyondthecodeapi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String SERVER = "localhost";
    private static final String PORT = "1521"; // Porta TCP padrão do Oracle
    private static final String DATABASE = "xe";

    // Configuração dos parâmetros de autenticação
    private static final String USER = "system";
    private static final String PASS = "oracle";

    public static Connection getConnection () throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
        Connection connection = DriverManager.getConnection(url, USER, PASS);

        // sempre usar o schema JORNADA
        connection.createStatement().execute("alter session set current_schema=JORNADA1");

        return connection;
    }
}
