package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {

        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,11,0), "2-ой Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 490),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1100),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,10,0), "Завтрак", 100),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,13,0), "Обед", 1500),
                new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1,20,0), "Ужин", 40)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMeal> userMeals = new ArrayList<UserMeal>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<UserMealWithExceed>();
        LocalDateTime date = null;
        int sumCalories = 0;

        for(int j = 0; j < mealList.size(); j++){
            UserMeal meal = mealList.get(j);
            LocalDateTime maelDate = meal.getDateTime();
            if (date == null) {
                date = meal.getDateTime();
            }
            if ((date.getDayOfYear() == maelDate.getDayOfYear()) && (j < mealList.size()-1)) {
                date = maelDate;
                sumCalories += meal.getCalories();

                if ((maelDate.getHour() > startTime.getHour()) && (maelDate.getHour() < endTime.getHour())) {
                    UserMeal userMeal = new UserMeal(maelDate, meal.getDescription(), meal.getCalories());
                    userMeals.add(userMeal);
                }
            }else {
                LocalDateTime dateExceed ;
                if(j == mealList.size()-1){
                    sumCalories += meal.getCalories();
                }
                if (sumCalories > caloriesPerDay){
                    dateExceed = date;
                }else {
                    dateExceed = date.minusDays(10);
                }
                for(int i = 0; i < userMeals.size(); i++){
                    UserMeal user = userMeals.get(i);
                    if (user.getDateTime().getDayOfYear() == dateExceed.getDayOfYear()){
                        UserMealWithExceed userMealWithExceed = new UserMealWithExceed(user.getDateTime(), user.getDescription(), user.getCalories(), true);
                        userMealWithExceeds.add(userMealWithExceed);
                    }else {
                        UserMealWithExceed userMealWithExceed = new UserMealWithExceed(user.getDateTime(), user.getDescription(), user.getCalories(), false);
                        userMealWithExceeds.add(userMealWithExceed);
                    }
                }
                userMeals.clear();
                date = maelDate;
                sumCalories = 0;
                sumCalories += meal.getCalories();
                if ((maelDate.getHour() > startTime.getHour()) && (maelDate.getHour() < endTime.getHour())) {
                    UserMeal userMeal = new UserMeal(maelDate, meal.getDescription(), meal.getCalories());
                    userMeals.add(userMeal);
                }
            }
        }
        return userMealWithExceeds;

    }
}
