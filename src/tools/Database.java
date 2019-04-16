package tools;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/examination_system";
    private static final String NAME = "root";
    private static final String PASSWORD = "Ylazy19911217";

    private static Connection getConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动不存在！");
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
        }

        return conn;
    }

    private static Statement getStatement(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("创建Statement失败");
        }

        return statement;
    }

    private static ResultSet executeSQL(Statement statement, String query) {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("查询失败！");
        }

        return rs;
    }

    private static void closeConnect(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
