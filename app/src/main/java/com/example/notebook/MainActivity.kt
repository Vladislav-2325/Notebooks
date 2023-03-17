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
    //эта чтобы забрать результат из второй активности заметок
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val newnote = data?.extras?.getSerializable("NewNote") as ElemNote?
            if (newnote != null) {
                when(requestCode) {
                    100->{
                        Toast.makeText(
                            this@MainActivity,
                            "New note created! ${newnote.theme}",
                            Toast.LENGTH_SHORT
                        ).show()
                        notes.add(newnote)
                    }
                    200-> {
                        val index = data?.extras?.getInt("EditIndex")
                        if (index!=null) {
                            Toast.makeText(
                                this@MainActivity,
                                "Note was updated! ${newnote.theme}",
                                Toast.LENGTH_SHORT
                            ).show()
                            notes[index] = newnote
                        }
                    }
                }
                Save()
            }
        }
    }
    //эта чтобы обработать обычные нажатия как модификацию записки
    private fun onListItemClick(position: Int) {
        val intent = Intent(this, MainActivity2::class.java)
        Toast.makeText(this, "Edited "+ position, Toast.LENGTH_SHORT).show()
        intent.putExtra("EditNote",notes[position] as Serializable)
        intent.putExtra("Index",position)
        // Переключение на доп активность создания заметки для модификации
        startActivityForResult(intent, 200)
    }
    //эта чтобы обработать долгие нажатия как удаление записки
    private fun onListItemLongClick(position: Int) {
        notes.removeAt(position)
        Toast.makeText(this, "Deleted "+ position, Toast.LENGTH_SHORT).show()
        onStart()
    }

    private fun onChBoxClick(position: Int) {
        when (notes[position].fav)
        {
            true-> notes[position].fav = false
            false-> notes[position].fav = true
        }
        notes.sortByDescending { it.fav }
        onStart()
    }

    private fun Save(){
        val sharedPreference =  getSharedPreferences("Notes", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("Notes", Gson().toJson(notes));
        editor.commit();
    }

    private fun Load(){
        val sharedPreference =  getSharedPreferences("Notes", Context.MODE_PRIVATE)
        val notes_str =  sharedPreference.getString("Notes",Gson().toJson(notes))
        val sType = object : TypeToken<MutableList<ElemNote>>() { }.type
        notes = Gson().fromJson<MutableList<ElemNote>>(notes_str, sType)
    }

    override fun onDestroy() {
        super.onDestroy()
        //сохранение заметок в SHP
        Save()
    }
}