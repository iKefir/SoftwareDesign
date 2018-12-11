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
public class GetProductsServlet extends HttpServlet {

    DBManager dbManager;

    public GetProductsServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        try {
            dbManager.executeDBRequest(response, "SELECT * FROM PRODUCT", "");
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
