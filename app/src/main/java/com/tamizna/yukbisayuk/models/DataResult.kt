package com.tamizna.yukbisayuk.models

class DataResult<T>(val state: State, val data: T?, val errorMessage: String?) {
    enum class State {
        LOADING,
        SUCCESS,
        ERROR,
        UNAUTHORIZED
    }
}