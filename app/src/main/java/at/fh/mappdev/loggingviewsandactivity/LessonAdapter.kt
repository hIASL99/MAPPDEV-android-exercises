package at.fh.mappdev.loggingviewsandactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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
        itemView.findViewById<RatingBar>(R.id.item_lesson_avg_rating_bar).rating = lesson.ratingAverage().toFloat()
        itemView.findViewById<TextView>(R.id.item_lesson_topic).text = lesson.topic
        itemView.findViewById<TextView>(R.id.item_lesson_lecturers).text = lesson.lecturers.joinToString { it.name }
        itemView.findViewById<TextView>(R.id.item_lesson_rating_count).text = lesson.ratings.size.toString()
        val imageView = itemView.findViewById<ImageView>(R.id.item_lesson_imageView)
        Glide.with(itemView)
            .load(lesson.imageUrl)
            .into(imageView)
        itemView.setOnClickListener {
            clickListener(lesson)
        }
    }

}