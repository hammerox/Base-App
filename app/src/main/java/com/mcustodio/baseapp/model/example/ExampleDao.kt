package com.mcustodio.baseapp.model.example

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mcustodio.baseapp.model.BaseDao
import io.reactivex.Flowable

@Dao
interface ExampleDao : BaseDao<Example> {

    @Query("SELECT * FROM Example")
    fun getAll() : Flowable<List<Example>>

    @Query("SELECT * FROM Example")
    fun getAllList() : List<Example>

    @Query("DELETE FROM Example")
    fun deleteAll()

}