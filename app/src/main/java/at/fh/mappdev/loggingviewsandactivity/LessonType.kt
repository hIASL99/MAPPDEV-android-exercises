package at.fh.mappdev.loggingviewsandactivity

import com.squareup.moshi.JsonClass


enum class LessonType(val description: String) {
    LECTURE("Lecture"),
    PRACTICAL("Practical")
}

@JsonClass(generateAdapter = true)
class LessonRating(val ratingValue: Double, val feedback: String)

@JsonClass(generateAdapter = true)
class Lesson(
    val id: String, val name: String, val date: String, val topic: String,
    val type: LessonType, val lecturers: List<Lecturer>, val ratings: MutableList<LessonRating>
) {
    fun ratingAverage(): Double {
        var returnvalue = 0.0
        if (ratings.size > 0){
            returnvalue = ratings.sumOf { it.ratingValue } / ratings.size
        }


        return returnvalue
    }
}
@JsonClass(generateAdapter = true)
class Lecturer(val name: String)


