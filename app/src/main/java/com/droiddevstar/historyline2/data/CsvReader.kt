package com.droiddevstar.historyline2.data


import android.content.res.AssetManager
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class CsvReader {
    companion object {
        fun readCsv(assets: AssetManager,
                    locale: Locale,
                    level: Int = 1): ArrayList<EventModel> {
            val nameId = 0
            val yearId = 1
            val readableYear = 2

            val fileReader: BufferedReader?

            val eventsList = ArrayList<EventModel>()
            var line: String?

            val languageRussian = "ru"
            val fileName: String = if (locale.language == languageRussian) {
                Timber.e("events_level${level}_ru.csv")
                "events_level${level}_ru.csv"
            } else {
                "events_level${level}_en.csv"
            }

            val inputStream = assets.open(fileName)

            fileReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

            fileReader.use {
                line = it.readLine()

                while (line != null) {
                    val tokens = line?.split(",")
                    if (null != tokens
                        && tokens.isNotEmpty()) {
                        val eventModel =
                            EventModel(
                                tokens[nameId],
                                Integer.parseInt(tokens[yearId]),
                                tokens[readableYear]
                            )

                        eventsList.add(eventModel)
                    }

                    line = it.readLine()
                }

                return eventsList
            }
        }
    }

}