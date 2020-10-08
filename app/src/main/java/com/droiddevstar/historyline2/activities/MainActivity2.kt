package com.droiddevstar.historyline2.activities


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.droiddevstar.historyline2.*
import com.droiddevstar.historyline2.data.CsvReader
import com.droiddevstar.historyline2.data.CurrentLevelKeeper
import com.droiddevstar.historyline2.data.EventListModel
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ActivityLayout(getLayoutId = R.layout.activity_main2)
class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notInsertedEvents = CsvReader
            .readCsv(assets, getCurrentLocale(this@MainActivity2), CurrentLevelKeeper.currentLevel)
        notInsertedEvents.shuffle()
        val mAdapter = MyAdapter(
            EventListModel(
                notInsertedEvents
            )
        )
        mAdapter.context = this@MainActivity2
        val mList = findViewById<DragDropSwipeRecyclerView>(R.id.list)
        mList.layoutManager = LinearLayoutManager(this@MainActivity2)
        mList.adapter = mAdapter
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity2::class.java)
        }
    }

    private fun getCurrentLocale(context: Context) : Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.resources.configuration.locales.get(0)
        } else{
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }

    @ExperimentalCoroutinesApi
    fun onSortSuccessful() {
        CurrentLevelKeeper.incrementLevel()
        startActivity<YouWinActivity>()
        finish()
    }

    @ExperimentalCoroutinesApi
    fun onSortFail() {
        startActivity<YouLoseActivity>()
        finish()
    }

}