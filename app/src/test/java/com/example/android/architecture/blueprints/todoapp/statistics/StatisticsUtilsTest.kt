package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test


class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero(){
        // create active task
        val tasks = listOf<Task>(
            Task("title", "desc", isCompleted = false)
        )
        // call your function
        val result = getActiveAndCompletedStats(tasks)

        /*
        // check the result with assertEquals
        assertEquals(result.completedTasksPercent, 0f)
        assertEquals(result.activeTasksPercent, 100f)
         */

        // check the result with assertThat(hamcrest)
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_empty_returnsZeros(){
        val result = getActiveAndCompletedStats(emptyList())
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_noActive_returnsZeroHundred(){
        val tasks = listOf<Task>(
            Task("task1","refactoring code",true)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompleteStats_error_returnsZeros(){
        val tasks = null
        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_noCompleted_returnsHundredZero(){
        val tasks = listOf<Task>(
            Task("task1","refactoring code",false)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_both_returnsFortySixty(){
        val tasks = listOf<Task>(
            Task("task1","refactoring code1",false),
            Task("task2","refactoring code2",false),
            Task("task3","refactoring code3",true),
            Task("task4","refactoring code4",true),
            Task("task5","refactoring code5",true)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))
    }
}