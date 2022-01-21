package at.fh.mappdev.loggingviewsandactivity

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRatesALessonTest {
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }
    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun clickingLessonsButton_shouldLaunchLessonListActivity() {
        onView(withId(R.id.open_lessons)).perform(click())
        intended(hasComponent(LessonListActivity::class.java.name))

        Thread.sleep(2000)
        onView(withId(R.id.lesson_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<LessonViewHolder>(hasDescendant(withText("Lecture 1")), click())
            )
        onView(
            withId(
                R.id.lesson_name
            )
        ).check(
            ViewAssertions.matches(
                withText("Lecture 1")
            )
        )
        intended(hasComponent(LessonRatingActivity::class.java.name))

        onView(withId(R.id.rating_btn_note)).perform(click())
        intended(hasComponent(LessonNoteActivity::class.java.name))
    }
}