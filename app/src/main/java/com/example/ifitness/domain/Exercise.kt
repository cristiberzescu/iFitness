package com.example.ifitness.domain

import android.os.Parcel
import android.os.Parcelable

data class Exercise(
    var name: String = "",
    var series: MutableList<Series>? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        mutableListOf<Series>().apply {
            parcel.readList(this, Series::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(series)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}
