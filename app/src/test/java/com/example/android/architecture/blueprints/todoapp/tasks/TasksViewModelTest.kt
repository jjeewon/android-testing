package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
viewmodel 테스트를 local test로 하는 이유
viewmodel 코드가 안드로이드 프레임워크나 OS에 의존하고 있으면 안됨
 */

// @RunWith는 default test runner를 바꿔주는 역할을 함
// test runner : 테스트를 돌리는 JUnit component
// AndroidJunit4 test runner : instrumented test냐 local test냐에 따라 다르게 테스트할 수 있게 해줌
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest{

    // InstantTaskExecutorRule이란?
    // Junit Rule이고 이걸 쓰려면 @get:Rule 어노테이션 붙여야 함
    // Junit Rule은 모든 아키텍처 컴포넌트가 관련된 백그라운드 잡을 '동일한 스레드'에서 돌아가게 해줌
    // 따라서 테스트 결과가 동기화(synchronously) 됨
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject unter test
    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel(){
        // 1. Given a fresh TasksViewModel
        // TasksViewModel(application: Application) : AndroidViewModel(application)
        // TaskViewModel은 application context를 construtor에 가지고 있음
        // local 테스트는 액티비티랑 UI가 다 준비된 상태로 하는게 아닌데 어찌 가져올까??
        // -> AndroidX Test library를 쓰면 됨!
        // AndroidX Test library는 테스트용으로 쓸 수 있는 Application, Activity 같은 컴포넌트
        // 들을 제공하는 class와 method들을 가지고 있음!
        // 따라서 local test를 하는데 simulated Android framework 클래스들이 필요하다면, AndroidX Test 라이브러리 이용하면 돼
        tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
        // AndroidX Test란?
        // -> 테스팅을 위한 라이브러리들을 모아놓은 것
        // ApplicationProvider.getApplicationContext() = app context를 부여하는 AndroidX Test의 함수
        // AndroidX Test의 장점
        // -> local test와 instrumented test 둘 다에서 쓸 수 있음
        // -> instrumented test 에서는 에뮬/실기기 킬 때 실제 Application context를 제공함
        // -> local test 에서는 simulate된 안드로이드 환경을 이용하여 Application context를 제공함
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {

        /*
        LifecycleOwner(Activity, Fragment) 없는 상황에서 Livedata observation 만드는 방법 : observeForever 메소드 쓰기
        observeForever : LiveData가 LifecycleOwner 없이 계~속 observe되게 해줌
        observeForever 쓸거면 마지막에 observer를 제거해줘야 observer leak 막을 수 있음
         */
        val observer = Observer<Event<Unit>> {}
        tasksViewModel.newTaskEvent.observeForever(observer)
        // Robolectric란?
        // Robolectric이 제공하는 local test에서 AndroidX Test가 쓰는 simulate된 안드로이드 환경
        // 테스트용 simulated android environment를 만들어주는 라이브러리임
        // 실제 기기나 에뮬 쓰는 것보다 빠름

        // 3. Then the new task event is triggered
        // TODO test LiveData
        // LiveData를 테스트하는 방법
        // 1. InstantTaskExecutorRule 쓰기, 2. LiveData observation 보장
        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), (not(nullValue())))
        // getContentIfNotHandled : one time event에 대해 처음 호출되면 content주고,
        // 그 이후에 호출된 것들은 null을 반환해줌
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), (not(nullValue())))
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}