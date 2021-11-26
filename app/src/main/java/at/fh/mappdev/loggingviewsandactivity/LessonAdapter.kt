package at.fh.mappdev.loggingviewsandactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LessonAdapter(val clickListener: (lesson: Lesson) -> Unit): RecyclerView.Adapter<LessonViewHolder>() {

    private var lessonList = listOf<Lesson>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val lessonItemView = inflater.inflate(R.layout.item_lesson, parent, false)
        return LessonViewHolder(lessonItemView, clickListener)
    }

    override fun getItemCount(): Int {
        return lessonList.size;
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bindItem(lessonList[position])

    }

    fun updateList(newList: List<Lesson>) {
        lessonList = newList
        notifyDataSetChanged()
    }

}

class LessonViewHolder(itemView: View, val clickListener: (lesson: Lesson) -> Unit): RecyclerView.ViewHolder(itemView) {

    fun bindItem(lesson: Lesson) {
        itemView.findViewById<TextView>(R.id.item_lesson_name).text = lesson.name
        itemView.findViewById<TextView>(R.id.item_lesson_date).text = lesson.date
        itemView.setOnClickListener {
            clickListener(lesson)
        }
    }

}