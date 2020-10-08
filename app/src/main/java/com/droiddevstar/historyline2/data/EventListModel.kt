package com.droiddevstar.historyline2.data


data class EventListModel(val list: List<EventModel>) : ArrayList<EventModel>(list) {
    fun isSorted() : Boolean {
        if (size > 1) {

            for (i in (1 until size)) {
                if (get(i) < get(i-1)) {
                    return false
                }
            }

            return true
        }

        return true
    }

}