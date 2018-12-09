package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class jdbc {

    private static jdbc insJdbc = null;
    private Connection conn;

    private jdbc()
            throws Exception {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = "jdbc:derby://localhost:1527/Sivopia";
            String user = "sivopia";
            String password = "sivopia";
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception("Ups! hubo un error al conectarse con la base de datos.");
        }
    }

    public boolean createOrUpdate(String queryInsert, HashMap paramsAndValues)
            throws Exception {
        boolean isCreated = false;
        if (!queryInsert.isEmpty() && !paramsAndValues.isEmpty()) {
            try {
                PreparedStatement pStm = getInstance().conn.prepareStatement(queryInsert);
                int contParams = 1;
                for (Object key : paramsAndValues.keySet()) {
                    pStm.setString(contParams, paramsAndValues.get(key).toString());
                    contParams++;
                }
                isCreated = !pStm.execute();
            } catch (Exception ex) {
                throw new Exception("Ups! hubo un error al crear el registro.");
            }
        } else {
            throw new Exception("Ups! hubo un error al crear el registro.");
        }
        return isCreated;
    }

    public ResultSet read(String querySelect)
            throws Exception {
        ResultSet rs = null;
        if (!querySelect.isEmpty()) {
            try {
                Statement stm = getInstance().conn.createStatement();
                rs = stm.executeQuery(querySelect);
            } catch (Exception ex) {
                throw new Exception("Ups! hubo un error al consultar.");
                //System.out.println(ex);
            }
        } else {
            throw new Exception("Ups! hubo un error al consultar.");
        }

        return rs;
    }

    public static jdbc getInstance()
            throws Exception {
        try {
            if (insJdbc == null) {
                insJdbc = new jdbc();
            }
        } catch (Exception ex) {
            throw new Exception("Ups! hubo un error al crear la configuración con la base de datos.");
        }
        return insJdbc;
    }
}
