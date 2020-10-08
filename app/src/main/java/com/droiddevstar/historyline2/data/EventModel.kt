package com.droiddevstar.historyline2.data


data class EventModel(val name: String = "",
                      val year: Int = 0,
                      val readableDate: String = "") : Comparable<EventModel> {

    override fun compareTo(other: EventModel): Int {
        return this.year.compareTo(other.year)
    }

}


