package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.*;
import ru.akirakozov.sd.refactoring.DBManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

public class ServletFunctionsTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static PrintWriter writer;
    private static DBManager manager;

    @BeforeAll
    static void createMocks() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);
        manager = new DBManager("jdbc:sqlite:forTest.db");

        when(request.getParameter("name")).thenReturn("testProduct");
        when(response.getWriter()).thenReturn(writer);
    }

    @BeforeEach
    void createTable() throws RuntimeException {
        String sql = "DROP TABLE IF EXISTS PRODUCT";
        manager.executeDBRequest(null, sql, "");
        sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        manager.executeDBRequest(null, sql, "");
    }

    @Test
    void simpleTest() throws RuntimeException, IOException {
        AddProductServlet addServlet = new AddProductServlet(manager);
        GetProductsServlet getServlet = new GetProductsServlet(manager);

        when(request.getParameter("price")).thenReturn("300");

        for (int i = 0; i < 100; ++i) {
            addServlet.doGet(request, response);
        }

        getServlet.doGet(request, response);

        verify(writer, times(100)).println("testProduct\t300</br>");
    }

    @Test
    void randomFilterTest() throws RuntimeException, IOException {
        AddProductServlet addServlet = new AddProductServlet(manager);
        QueryServlet qServlet = new QueryServlet(manager);
        Random rand = new Random();

        List<String> commands = Arrays.asList("max", "min", "sum", "count");
        String command;
        int nextOp, price;

        int curMax = 0;
        int curMin = Integer.MAX_VALUE;
        int sum = 0;
        int count = 0;

        for (int i = 0; i < 1000; ++i) {
            nextOp = rand.nextInt(5);
            if (i==0 || nextOp == 4) {
                price = rand.nextInt(1000000);
                when(request.getParameter("price")).thenReturn(Integer.toString(price));
                if (price > curMax) {
                    curMax = price;
                }
                if (price < curMin) {
                    curMin = price;
                }
                sum += price;
                count += 1;

                addServlet.doGet(request, response);
            } else {
                command = commands.get(nextOp);

                when(request.getParameter("command")).thenReturn(command);

                qServlet.doGet(request, response);

                switch (command) {
                    case "max":
                        verify(writer, atLeastOnce()).println("testProduct\t" + Integer.toString(curMax) + "</br>");
                        break;
                    case "min":
                        verify(writer, atLeastOnce()).println("testProduct\t" + Integer.toString(curMin) + "</br>");
                        break;
                    case "sum":
                        verify(writer, atLeastOnce()).println(sum);
                        break;
                    case "count":
                        verify(writer, atLeastOnce()).println(count);
                        break;
                }
            }
        }
    }
}
