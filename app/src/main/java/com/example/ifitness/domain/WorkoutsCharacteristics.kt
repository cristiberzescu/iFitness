package com.example.ifitness.domain

object WorkoutsCharacteristics {

    private var serviceTitle :String? = null
    private var serviceKey : String? = null



    fun setKey(saveKey: String) {
        serviceKey = saveKey
    }
    fun getKey(): String?{
        return serviceKey
    }


    fun setTitle(saveTitle: String) {
        serviceTitle = saveTitle
    }

    fun getTitle(): String?{
        return serviceTitle
    }

}