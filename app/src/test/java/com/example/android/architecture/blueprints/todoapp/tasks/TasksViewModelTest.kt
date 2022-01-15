package com.example.android.architecture.blueprints.todoapp.tasks

import org.junit.Test

/*
viewmodel 테스트를 local test로 하는 이유
viewmodel 코드가 안드로이드 프레임워크나 OS에 의존하고 있으면 안됨
 */
class TasksViewModelTest{
    @Test
    fun addNewTask_setsNewTaskEvent() {
        // 1. Given a fresh TasksViewModel
        // TasksViewModel(application: Application) : AndroidViewModel(application)
        // TaskViewModel은 application context를 construtor에 가지고 있음
        // local 테스트는 액티비티랑 UI가 다 준비된 상태로 하는게 아닌데 어찌 가져올까??
        // -> AndroidX Test library를 쓰면 됨!
        // AndroidX Test library는 테스트용으로 쓸 수 있는 Application, Activity 같은 컴포넌트
        // 들을 제공하는 class와 method들을 가지고 있음!
        // 따라서 local test를 하는데 simulated Android framework 클래스들이 필요하다면, AndroidX Test 라이브러리 이용하면 돼



        // 2. When adding a new task

        // 3. Then the new task event is triggered
    }
}