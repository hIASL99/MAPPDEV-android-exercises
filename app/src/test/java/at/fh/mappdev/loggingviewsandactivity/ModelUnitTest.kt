package at.fh.mappdev.loggingviewsandactivity

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

class ModelUnitTest {
    @Test
    fun averageForEmptyRates_isCorrect() {
        // test whether the average is 0.0 when ratings are empty
        val lesson = Lesson(id = "Id",
                            name = "Name",
                            date = "Date",
                            topic = "Topic",
                            type = LessonType.LECTURE,
                            lecturers = listOf<Lecturer>(),
                            imageUrl = "ImageURL",
                            ratings = mutableListOf<LessonRating>()
                            )
        val rating = lesson.ratingAverage()
        assertEquals(rating, 0.0 , 0.1)



    }

    @Test
    fun averageForNonEmptyRates_isCorrect() {
        // check whether the average is correct for a non-empty list of ratings
        val ratings = mutableListOf<LessonRating>(LessonRating(1.0, "One"),
            LessonRating(5.0,"Five")
        )
        val lesson = Lesson(id = "Id",
            name = "Name",
            date = "Date",
            topic = "Topic",
            type = LessonType.LECTURE,
            lecturers = listOf<Lecturer>(),
            imageUrl = "ImageURL",
            ratings = ratings
        )
        val rating = lesson.ratingAverage()
        assertEquals(rating, 3.0 , 0.1)
    }

}