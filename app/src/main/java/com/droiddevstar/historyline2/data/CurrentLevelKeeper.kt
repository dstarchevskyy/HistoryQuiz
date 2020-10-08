package com.droiddevstar.historyline2.data


class CurrentLevelKeeper {

    companion object {
        var currentLevel = 1

        fun incrementLevel() {
            if (currentLevel >= MAX_LEVEL_NUMBER) {
                currentLevel = 1
            } else {
                currentLevel++
            }
        }
    }

}

const val MAX_LEVEL_NUMBER = 10