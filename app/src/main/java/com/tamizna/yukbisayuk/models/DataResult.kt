package com.tamizna.yukbisayuk.models

class DataResult<T>(val state: State, val data: T?, val errorMessage: String?) {
    sealed class State {
        object LOADING : State()
        object SUCCESS : State()
        object ERROR : State()
        object UNAUTHORIZED : State()
    }
}