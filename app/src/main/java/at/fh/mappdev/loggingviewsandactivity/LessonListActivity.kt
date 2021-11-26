package at.fh.mappdev.loggingviewsandactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LessonListActivity : AppCompatActivity() {
    companion object {
        val EXTRA_LESSON_ID = "LESSON_ID_EXTRA"
    }
    val lessonAdapter = LessonAdapter() {
        //Toast.makeText(this, "Lesson with name: ${it.id} has been clicked", Toast.LENGTH_LONG).show()

        val intent = Intent(this, LessonRatingActivity::class.java)
        intent.putExtra(EXTRA_LESSON_ID, it.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_list)

        lessonAdapter.updateList(LessonRepository.lessonsList())
        val recyclerView = findViewById<RecyclerView>(R.id.lesson_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = lessonAdapter
    }
}