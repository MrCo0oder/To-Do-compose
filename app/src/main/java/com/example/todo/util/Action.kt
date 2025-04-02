package com.example.todo.util

sealed class Action {
    data object Add : Action()
    data object Update : Action()
    data object Delete : Action()
    data object DeleteAll : Action()
    data object Undo : Action()
    data object NoAction : Action()
}