package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static Game mapper(ResultSet rs) throws SQLException {
        return new Game(rs.getInt(1), rs.getString(2), rs.getString(3) );
    }

    public static void main(String[] args) {

        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setPort(3306);
        ds.setUser(System.getenv("MYSQL_USER"));
        ds.setPassword( System.getenv("MYSQL_PASSWORD"));
        ds.setDatabaseName("ad");

        try( Connection conn = ds.getConnection()){

            PreparedStatement st = conn.prepareStatement("SELECT * FROM games WHERE platform LIKE ?");
            st.setString(1, "PC");
            ResultSet results = st.executeQuery();

            ArrayList<Game> listado = new ArrayList<>();

            while(results.next()){
                listado.add( mapper(results) );
            }

            for(Game g : listado){
                System.out.println(g);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}