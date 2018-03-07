package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<Meal> userMeals = new ArrayList<Meal>();
        List<MealWithExceed> userMealWithExceeds = new ArrayList<MealWithExceed>();
        LocalDateTime date = null;
        int sumCalories = 0;

        for(int j = 0; j < mealList.size(); j++){
            Meal meal = mealList.get(j);
            LocalDateTime maelDate = meal.getDateTime();
            if (date == null) {
                date = meal.getDateTime();
            }
            if ((date.getDayOfYear() == maelDate.getDayOfYear()) && (j < mealList.size()-1)) {
                date = maelDate;
                sumCalories += meal.getCalories();

                if ((maelDate.getHour() > startTime.getHour()) && (maelDate.getHour() < endTime.getHour())) {
                    Meal userMeal = new Meal(maelDate, meal.getDescription(), meal.getCalories());
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
                    Meal user = userMeals.get(i);
                    if (user.getDateTime().getDayOfYear() == dateExceed.getDayOfYear()){
                        MealWithExceed userMealWithExceed = new MealWithExceed(user.getDateTime(), user.getDescription(), user.getCalories(), true);
                        userMealWithExceeds.add(userMealWithExceed);
                    }else {
                        MealWithExceed userMealWithExceed = new MealWithExceed(user.getDateTime(), user.getDescription(), user.getCalories(), false);
                        userMealWithExceeds.add(userMealWithExceed);
                    }
                }
                userMeals.clear();
                date = maelDate;
                sumCalories = 0;
                sumCalories += meal.getCalories();
                if ((maelDate.getHour() > startTime.getHour()) && (maelDate.getHour() < endTime.getHour())) {
                    Meal userMeal = new Meal(maelDate, meal.getDescription(), meal.getCalories());
                    userMeals.add(userMeal);
                }
            }
        }
        return userMealWithExceeds;

    }
}