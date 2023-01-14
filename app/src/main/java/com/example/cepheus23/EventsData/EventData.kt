package com.example.cepheus23.EventsData

import android.os.Parcel
import android.os.Parcelable


data class EventData(
    val id:Int?,
    val type:Int?,
    val team:Int?,
    val eventName:String?,
    val overview:String?
): Parcelable { //Made parcela-able so that putextra can pass the object in DescrpitionActivity
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(type)
        parcel.writeValue(team)
        parcel.writeString(eventName)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventData> {
        override fun createFromParcel(parcel: Parcel): EventData {
            return EventData(parcel)
        }

        override fun newArray(size: Int): Array<EventData?> {
            return arrayOfNulls(size)
        }
    }

}