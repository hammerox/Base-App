package com.mcustodio.baseapp.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.mcustodio.baseapp.R
import com.mcustodio.baseapp.model.example.Example
import kotlinx.android.synthetic.main.activity_counter.*
import java.util.*
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.dialog_counter.view.*


class MainActivity : AppCompatActivity() {


    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val counterAdapter = MainAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
        setButtonClickListener()
        setRecyclerView()
        observeCounter()
        observeResistanceList()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_counter, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuitem_counter_delete -> viewModel.clearAll()
        }
        return true
    }


    private fun setButtonClickListener() {
        fab_counter_add.setOnClickListener {
            askForDescription()
        }
    }


    private fun setRecyclerView() {
        list_counter_resistances.layoutManager = LinearLayoutManager(this)
        list_counter_resistances.adapter = counterAdapter
        ViewCompat.setNestedScrollingEnabled(list_counter_resistances, false)
        counterAdapter.onItemClick = onCardItemClick
        counterAdapter.onItemLongClick = onCardItemLongClick
    }


    private fun observeCounter() {
        viewModel.counter.observe(this, Observer {
            text_counter.text = it?.toString() ?: "0"
        })
    }


    private fun observeResistanceList() {
        viewModel.resistances.observe(this, Observer {
            it?.let { counterAdapter.data = it }
        })
    }


    private val onCardItemClick : ((Example) -> Unit) = { resistance ->
        askForDescription(resistance)
    }


    private val onCardItemLongClick : ((Example) -> Unit) = { resistance ->
        val options = listOf("Deletar")
        MaterialDialog.Builder(this)
                .items(options)
                .itemsCallback { _, _, position, _ ->
                    when (position) {
                        0 -> viewModel.delete(resistance)
                    }
                }
                .show()
    }


    // If example == null -> insert
    // If example != null -> update
    private fun askForDescription(example: Example? = null) {
        val dialog = MaterialDialog.Builder(this)
                .title("Descreva")
                .customView(R.layout.dialog_counter, false)
                .positiveText("OK")
                .onPositive { dialog, _ ->
                    val description = dialog.view.edit_counterdialog_description.text.toString()
                    createOrUpdateResistance(example, description)
                }
                .build()
        dialog.customView?.edit_counterdialog_description?.setText(example?.description)
        dialog.show()
    }


    private fun createOrUpdateResistance(example: Example?, description: String?) {
        if (example != null) {
            example.description = description
            viewModel.update(example)
        } else {
            val newResistance = Example()
            newResistance.date = Calendar.getInstance().time
            newResistance.description = description
            viewModel.insert(newResistance)
        }
    }
}
