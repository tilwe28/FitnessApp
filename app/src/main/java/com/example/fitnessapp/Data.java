package com.example.fitnessapp;

public class Data {

    private static String name="";
    private static double height=0.0, weight=0.0, calories=0.0, currentCalories=0.0;
    private static int age=0;

    public static String getName() {
        return name;
    }
    public static double getHeight() {
        return height;
    }
    public static double getWeight() {
        return weight;
    }
    public static double getCalories() {
        return calories;
    }
    public static double getCurrentCalories() {
        return currentCalories;
    }
    public static int getAge() {
        return age;
    }

    public static void setName(String newName) {
        name = newName;
    }
    public static void setHeight(double newHeight) {
        height = newHeight;
    }
    public static void setWeight(double newWeight) {
        weight = newWeight;
    }
    public static void setCalories(double newCalories) {
        calories = newCalories;
    }
    public static void setCurrentCalories(double addCalories) {
        currentCalories = addCalories;
    }
    public static void setAge(int newAge) {
        age = newAge;
    }
}
