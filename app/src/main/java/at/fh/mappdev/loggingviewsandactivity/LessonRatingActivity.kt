package at.fh.mappdev.loggingviewsandactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import at.fh.mappdev.loggingviewsandactivity.LessonListActivity.Companion.EXTRA_LESSON_ID
import at.fh.mappdev.loggingviewsandactivity.LessonRepository.lessonById
import at.fh.mappdev.loggingviewsandactivity.LessonRepository.rateLesson

class LessonRatingActivity : AppCompatActivity() {

    companion object{
        val EXTRA_ADDED_OR_EDITED_RESULT = "EXTRA_ADDED_OR_EDITED_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_rating)

        val lessonID = intent.getStringExtra(EXTRA_LESSON_ID)
        var lessonIdAsInt = 0

        if (lessonID != null){

            lessonById(
                lessonID,
                success = {
                    // handle success
                    val thislesson = it
                    val textView = findViewById<TextView>(R.id.lesson_rating_header)
                    textView.text = thislesson.name;
                    lessonIdAsInt = lessonID.toInt()
                },
                error = {
                    // handle error
                    Log.e("API ERROR","API ERROR")
                }
            )
        }


        findViewById<Button>(R.id.rate_lesson).setOnClickListener {
            val ratingBar = findViewById<RatingBar>(R.id.lesson_rating_bar)
            val feedback = findViewById<EditText>(R.id.lesson_feedback)
            val ratedLesson = LessonRating(ratingBar.rating.toDouble(), feedback.text.toString())
            //rateLesson(lessonIdAsInt, ratedLesson)

            rateLesson(
                lessonIdAsInt,
                ratedLesson,
                success = {
                    // handle success
                    val thislesson = it
                    val textView = findViewById<TextView>(R.id.lesson_rating_header)
                    textView.text = thislesson.name;
                },
                error = {
                    // handle error
                    Log.e("API ERROR","API ERROR")
                }
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_ADDED_OR_EDITED_RESULT, "Added")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }

    }


}