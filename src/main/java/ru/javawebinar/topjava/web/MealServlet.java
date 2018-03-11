package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.web.dto.MealDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mealList", new MealDto().getMealList());
        request.getRequestDispatcher("/meals.jsp").forward(request,response);
    }
}
