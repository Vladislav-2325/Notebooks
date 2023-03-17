package com.example.notebook

import MyAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    var notes = mutableListOf<ElemNote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //тут подгружаем все зааметки из SHP
        Load()
    }

    override fun onStart() {
        super.onStart()

        setContentView(R.layout.activity_main)

        //получаем экземпляр RV задаём разметку и настраиваем адаптер
        var recyclierView: RecyclerView = findViewById(R.id.tasklist)
        recyclierView.layoutManager = LinearLayoutManager(this)
        recyclierView.adapter = MyAdapter(notes, {position -> onListItemClick(position)},{position ->onListItemLongClick(position)},{position->onChBoxClick(position)})


        //эта кнопка для того чтобы перейти в другую активность и создать заметку
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            // Переключение на доп активность создания заметки
            startActivityForResult(intent, 100)
        }
    }

}