package com.example.cepheus23.EventsData

class ScheduleEvent {
    var event_name: String? = null
    var time: String? = null
    var date: String? = null

    constructor(event_name: String, time: String, date: String) {
        this.event_name = event_name
        this.time = time
        this.date = date
    }
}