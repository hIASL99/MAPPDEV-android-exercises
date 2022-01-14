package at.fh.mappdev.loggingviewsandactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fh.mappdev.loggingviewsandactivity.LessonRatingActivity.Companion.EXTRA_ADDED_OR_EDITED_RESULT
import com.squareup.moshi.Moshi


class LessonListActivity : AppCompatActivity() {
    companion object {
        val EXTRA_LESSON_ID = "LESSON_ID_EXTRA"
        val ADD_OR_EDIT_RATING_REQUEST = 1
    }
    private val viewModel: LessonListViewModel by viewModels()

    val lessonAdapter = LessonAdapter() {
        Toast.makeText(this, "Lesson with name: ${it.id} has been clicked", Toast.LENGTH_LONG).show()

        val intent = Intent(this, LessonRatingActivity::class.java)
        intent.putExtra(EXTRA_LESSON_ID, it.id)
        startActivityForResult(intent, ADD_OR_EDIT_RATING_REQUEST)
    }

    fun updateList(){
        /*LessonRepository.lessonsList(
            success = {
                // handle success
                lessonAdapter.updateList(it)
            },
            error = {
                // handle error
                Log.e("API ERROR","API ERROR")
            }
        )*/
        viewModel.refresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_list)
        updateList()
        // lessonAdapter.updateList(LessonRepository.lessonsList())
        val recyclerView = findViewById<RecyclerView>(R.id.lesson_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = lessonAdapter
        parseJson()
        SleepyAsyncTask().execute()

        viewModel.lessons.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    lessonAdapter.updateList(it.value)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_OR_EDIT_RATING_REQUEST && resultCode == Activity.RESULT_OK) {
            val resultExtra = data?.getStringExtra(EXTRA_ADDED_OR_EDITED_RESULT) ?: return
            //lessonAdapter.updateList(LessonRepository.lessonsList())
            updateList()
            Log.e("RESULT_EXTRA", "Result: ${resultExtra}")


        }
    }

    fun parseJson (){
        val json = """
            {
                "id": "1",
                "name": "Lecture 0",
                "date": "09.10.2019",
                "topic": "Introduction",
                "type": "LECTURE",
                "lecturers": [
                    {
                        "name": "Lukas Bloder"
                    },
                    {
                        "name": "Peter Salhofer"
                    }
                ],
                "ratings": []
            }
        """
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Lesson>(Lesson::class.java)
        val result = jsonAdapter.fromJson(json)
        Log.e("JSON NAME",result?.name.toString())
    }
}