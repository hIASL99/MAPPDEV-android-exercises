package at.fh.mappdev.loggingviewsandactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import at.fh.mappdev.loggingviewsandactivity.LessonListActivity.Companion.EXTRA_LESSON_ID
import at.fh.mappdev.loggingviewsandactivity.LessonRepository.lessonById
import at.fh.mappdev.loggingviewsandactivity.LessonRepository.rateLesson
import com.bumptech.glide.Glide

class LessonRatingActivity : AppCompatActivity() {

    companion object{
        val EXTRA_ADDED_OR_EDITED_RESULT = "EXTRA_ADDED_OR_EDITED_RESULT"
        val EXTRA_LESSON_NAME = "EXTRA_LESSON_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_rating)

        val lessonID = intent.getStringExtra(EXTRA_LESSON_ID)
        var lessonIdAsInt = 0
        var lessonName = ""
        if (lessonID != null){

            lessonById(
                lessonID,
                success = {
                    // handle success
                    val thislesson = it
                    title = thislesson.name
                    lessonName = thislesson.name
                    lessonIdAsInt = lessonID.toInt()

                    // image loading
                    val imageView = findViewById<ImageView>(R.id.lesson_image)

                    Glide.with(this)
                        .load(thislesson.imageUrl)
                        .into(imageView)

                    // text in view
                    findViewById<TextView>(R.id.lesson_name).text = title;
                    findViewById<RatingBar>(R.id.lesson_avg_ratingBar).rating = it.ratingAverage().toFloat();
                    val rating = it.ratingAverage().format(2)
                    findViewById<TextView>(R.id.lesson_avg_ratingText).text = rating

                    val feedback = findFirstEntry(it.ratings)


                    findViewById<TextView>(R.id.lesson_ViewFeedback).text = feedback
                },
                error = {
                    // handle error
                    Log.e("API ERROR","API ERROR")
                }
            )
        }

        findViewById<Button>(R.id.rating_btn_note).setOnClickListener {
            val noteIntent = Intent(this, LessonNoteActivity::class.java)
            noteIntent.putExtra(EXTRA_LESSON_ID, lessonID)
            noteIntent.putExtra(EXTRA_LESSON_NAME, lessonName)
            startActivityForResult(noteIntent, LessonNoteActivity.ADD_NOTE_REQUEST)
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
    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun findFirstEntry(list:List<LessonRating>, i:Int = 0):String{
        if(list.size <= i) return ""

        if(list[i].feedback != "") return list[i].feedback

        return findFirstEntry(list, i+1)
    }

}