package com.minseok.seoulinoneway

import com.minseok.seoulinoneway.common.TimeHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {
    @Test
    fun test_isOver2PM() {
        assert(TimeHelper.isOver2PM())
    }

    @Test
    fun test_isCorrectDifferenceTodayAndYesterDay_ReturnsTrue() {
        val today = TimeHelper.getTodayAsString()
        val yesterday = TimeHelper.getYesterdayAsString()

        val difference = Integer.parseInt(today) - Integer.parseInt(yesterday)
        assert(difference == 1)
    }

    @Test
    fun test_isCorrectDifferenceTodayAndYesterDay_ReturnsFalse() {
        val today = TimeHelper.getTodayAsString()
        val yesterday = TimeHelper.getYesterdayAsString()

        val difference = Integer.parseInt(today) - Integer.parseInt(yesterday)
        assert(difference != 0)
    }
}
