package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DBManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    DBManager dbManager;

    public AddProductServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        dbManager.executeDBRequest(response, sql, "OK");
    }
}
