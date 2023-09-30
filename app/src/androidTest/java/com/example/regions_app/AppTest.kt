package com.example.regions_app

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.regions_app.ui.main.MainActivity
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Test

class AppTest {

    // Взято с https://medium.com/@miloszlewandowski/espresso-matcher-for-imageview-made-easy-with-android-ktx-977374ca3391
    private fun Drawable.tinted(@ColorInt tintColor: Int? = null, tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) =
        apply {
            setTintList(tintColor?.toColorStateList())
            setTintMode(tintMode)
        }

    private fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)

    private fun Int.toColorStateList() = ColorStateList.valueOf(this)

    fun withDrawable(@DrawableRes id: Int, @ColorRes tint: Int? = null, tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $id")
            tint?.let { description.appendText(", tint color id: $tint, mode: $tintMode") }
        }

        override fun matchesSafely(view: View): Boolean {
            val context = view.context
            val tintColor = tint?.toColor(context)
            val expectedBitmap = context.getDrawable(id)?.tinted(tintColor, tintMode)?.toBitmap()

            return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
        }
    }
    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)
        // Переход на экран региона
        onView(withText("Москва")).perform(click())

        // Лайкаем регион
        onView(withId(R.id.ivRegionLiked))
            .perform(click())
        pressBack()

        // Проверка иконки
        onView(withId(R.id.ivRegionLiked)).check(matches(withDrawable(R.drawable.ic_heart_on)))
    }
}