package com.droiddevstar.historyline2


import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.droiddevstar.historyline2.activities.MainActivity2
import com.droiddevstar.historyline2.data.EventListModel
import com.droiddevstar.historyline2.data.EventModel
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber

class MyAdapter(dataSet: EventListModel)
    : DragDropSwipeAdapter<EventModel, MyAdapter.ViewHolder>(dataSet) {

    private var moves = 0

    class ViewHolder(itemView: View)
        : DragDropSwipeAdapter.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.item_text)
        val dragIcon: AppCompatImageView = itemView.findViewById(R.id.drag_icon)
        val llEvent: LinearLayoutCompat = itemView.findViewById(R.id.ll_event)
    }

    var context: Context? = null
        set(value) {
            field = value
        }

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    override fun onBindViewHolder(item: EventModel,
                                  viewHolder: ViewHolder,
                                  position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.itemText.text = item.name
    }

    override fun getViewToTouchToStartDraggingItem(item: EventModel,
                                                   viewHolder: ViewHolder,
                                                   position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
//        return viewHolder.dragIcon
        return viewHolder.llEvent
    }

    @ExperimentalCoroutinesApi
    override fun onDragFinished(item: EventModel,
                                viewHolder: ViewHolder) {
        viewHolder.dragIcon.setImageResource(R.drawable.hand_icon)
        super.onDragFinished(item, viewHolder)
        Timber.e("!!! IS SORTED: ${EventListModel(
            dataSet
        ).isSorted()}")

        moves++
        if (context is MainActivity2) {
            if (EventListModel(dataSet).isSorted()) {
                GlobalScope.async {
                    (context as MainActivity2).onSortSuccessful()
                }
            } else if (moves > dataSet.size) {
                (context as MainActivity2).onSortFail()
            }
        }
    }

    override fun onDragStarted(item: EventModel,
                               viewHolder: ViewHolder) {
        viewHolder.dragIcon.setImageResource(R.drawable.drag_hand_icon)
        super.onDragStarted(item, viewHolder)
    }

}