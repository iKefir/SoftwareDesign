package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DBManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    DBManager dbManager;

    public QueryServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws RuntimeException, IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            dbManager.executeDBRequest(response,"SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", "<h1>Product with max price: </h1>");
        } else if ("min".equals(command)) {
            dbManager.executeDBRequest(response,"SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", "<h1>Product with min price: </h1>");
        } else if ("sum".equals(command)) {
            dbManager.executeDBRequest(response,"SELECT SUM(price) FROM PRODUCT", "Summary price: ", false);
        } else if ("count".equals(command)) {
            dbManager.executeDBRequest(response,"SELECT COUNT(*) FROM PRODUCT", "Number of products: ", false);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }

}
