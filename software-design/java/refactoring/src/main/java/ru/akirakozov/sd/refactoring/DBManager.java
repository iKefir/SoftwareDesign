package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class DBManager {
    private String dbURL;

    public DBManager(String dbURL) throws RuntimeException {
        this.dbURL = dbURL;

        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeDBRequest(null, sql, "");
    }

    public void executeDBRequest(HttpServletResponse response, String sql, String message) throws RuntimeException {
        executeDBRequest(response, sql, message, true);
    }

    public void executeDBRequest(HttpServletResponse response, String sql, String message, boolean answerIsList) throws RuntimeException {
        try {
            try (Connection c = DriverManager.getConnection(dbURL)) {
                Statement stmt = c.createStatement();
                ResultSet rs = null;
                if (stmt.execute(sql)) {
                    rs = stmt.getResultSet();
                }

                if (response != null) {
                    response.getWriter().println("<html><body>");
                    if (!message.isEmpty()) {
                        response.getWriter().println(message);
                    }
                    if (rs != null) {
                        if (answerIsList) {
                            while (rs.next()) {
                                String name = rs.getString("name");
                                int price = rs.getInt("price");
                                response.getWriter().println(name + "\t" + price + "</br>");
                            }
                        } else {
                            if (rs.next()) {
                                response.getWriter().println(rs.getInt(1));
                            }
                        }
                    }
                    response.getWriter().println("</body></html>");

                    response.setContentType("text/html");
                    response.setStatus(HttpServletResponse.SC_OK);
                }

                if (rs != null) {
                    rs.close();
                }
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
