package com.example.ifitness.domain

object FoodCharacteristics {

    private var foodName: String? = null
    private var protein: String? = null
    private var carbs: String? = null
    private var fats: String? = null
    private var calories: String? = null
    private var foodDate: String? = null

    fun setCalories(foodCalories: String) {
        calories = foodCalories
    }

    fun getCalories(): String? {
        return calories
    }

    fun setProtein(foodProtein: String) {
        protein = foodProtein
    }

    fun getProtein(): String? {
        return protein
    }

    fun setCarbs(foodCarbs: String) {
        carbs = foodCarbs
    }

    fun getCarbs(): String? {
        return carbs
    }

    fun setFats(foofFats: String) {
        fats = foofFats
    }

    fun getFats(): String? {
        return fats
    }


    fun setName(name: String) {
        foodName = name
    }

    fun getName(): String? {
        return foodName

    }

    fun setdate(date: String) {
        foodDate = date
    }

    fun getdate(): String? {
        return foodDate
    }

}