package at.fh.mappdev.loggingviewsandactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.fh.mappdev.loggingviewsandactivity.LessonListActivity.Companion.EXTRA_LESSON_ID
import at.fh.mappdev.loggingviewsandactivity.LessonRepository.lessonById

class LessonRatingActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_rating)



        val lessonID = intent.getStringExtra(EXTRA_LESSON_ID)

        if (lessonID != null){
            val thislesson = lessonById(lessonID.toString())
            val textView = findViewById<TextView>(R.id.lesson_rating_header)
            textView.text = thislesson?.name;
        }

    }

}