package com.mcustodio.baseapp.repository.example

import android.content.Context
import com.mcustodio.baseapp.model.example.Example
import com.mcustodio.baseapp.repository.BaseRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class ExampleRepository(context: Context) : BaseRepository(context) {


    fun getDatabase() : Flowable<List<Example>> {
        return Flowable.just(database.resistanceDao())
                .flatMap { it.getAll() }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
    }


    fun insert(example: Example) {
        Observable.just(database.resistanceDao())
                .doOnNext { it.insert(example) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }


    fun update(example: Example) {
        Observable.just(database.resistanceDao())
                .doOnNext { it.update(example) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }


    fun delete(example: Example) {
        Observable.just(database.resistanceDao())
                .doOnNext { it.delete(example) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }


    fun clearAll() {
        Observable.just(database.resistanceDao())
                .doOnNext { it.deleteAll() }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}