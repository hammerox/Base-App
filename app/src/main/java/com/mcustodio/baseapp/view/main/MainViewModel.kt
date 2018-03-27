package com.mcustodio.baseapp.view.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import com.mcustodio.baseapp.model.example.Example
import com.mcustodio.baseapp.repository.resistance.ResistanceRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {


    var counter : LiveData<Int>
    var resistances : LiveData<List<Example>>

    private val repository by lazy { ResistanceRepository(app) }



    init {
        val resistanceFlow = repository.getDatabase()
        counter = LiveDataReactiveStreams.fromPublisher(resistanceFlow.map { it.size })
        resistances = LiveDataReactiveStreams.fromPublisher(resistanceFlow)
    }


    fun insert(example: Example) = repository.insert(example)

    fun update(example: Example) = repository.update(example)

    fun delete(example: Example) = repository.delete(example)

    fun clearAll() = repository.clearAll()

}