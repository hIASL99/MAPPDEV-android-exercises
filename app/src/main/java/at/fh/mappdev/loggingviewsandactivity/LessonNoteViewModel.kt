package at.fh.mappdev.loggingviewsandactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class LessonNoteViewModel(application: Application): AndroidViewModel(application) {
    //ViewModel implementation here

    private val noteId = MutableLiveData<String>()

    val note: LiveData<LessonNote?> = Transformations.switchMap(noteId) {
        LessonRepository.findLessonNoteByIdLiveData(getApplication(), it.toString())
    }
    fun findLessonNoteById(id:String ){
        noteId.value = id
    }
}