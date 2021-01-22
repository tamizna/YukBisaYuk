package com.tamizna.yukbisayuk.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tamizna.yukbisayuk.models.User

@Dao
interface UserDao {
    @Query("SELECT * from users WHERE user_name = (:user_name) AND password = (:password)")
    fun getUser(user_name: String, password : String) : List<User>

    @Insert
    fun insertUser(data : User)
}