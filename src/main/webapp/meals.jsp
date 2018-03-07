<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h2>Hello meals!!!</h2>
    <table>
        <thead>
            <tr>
                date
            </tr>
            <tr>
                description
            </tr>
            <tr>
                calories
            </tr>
        </thead>
        <tbody>
        <jsp:useBean id="mealClass" class="ru.javawebinar.topjava.web.dto.MealDto" />
        <c:set var="meals" value="${mealClass.mealList}" />
        <c:forEach items="${meals}" var="meal">
                    <tr style=${meal.exceed ? "color:red" : "color:black"}>
                        <td>
                            <c:out value="${meal.dateTime.toString().replace('T', ' ')}"/>
                        </td>
                        <td>
                            <c:out value="${meal.description}"/>
                        </td>
                        <td>
                            <c:out value="${meal.calories}"/>
                        </td>
                    </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
