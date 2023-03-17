package com.example.notebook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val edited = intent?.extras?.getSerializable("EditNote") as ElemNote?
        val index = intent?.extras?.getInt("Index")

        if (edited != null && index!=null)
        {
            findViewById<EditText>(R.id.textView1).setText(edited.theme)
            findViewById<EditText>(R.id.textView2).setText(edited.desc)
            findViewById<EditText>(R.id.textView3).setText(edited.content)
        }

        val fab: View = findViewById(R.id.fab2)
        fab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if (index!=null)
            {
                intent.putExtra("EditIndex",index)
                intent.putExtra("NewNote",ElemNote(
                    findViewById<EditText>(R.id.textView1).text.toString(),
                    findViewById<EditText>(R.id.textView2).text.toString(),
                    findViewById<EditText>(R.id.textView3).text.toString(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd':'MM':'yyyy")).toString(),edited!!.fav) as Serializable)
            }
            else
            {
                // переключение на главную активность
                intent.putExtra("NewNote",ElemNote(
                    findViewById<EditText>(R.id.textView1).text.toString(),
                    findViewById<EditText>(R.id.textView2).text.toString(),
                    findViewById<EditText>(R.id.textView3).text.toString(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd':'MM':'yyyy")).toString()) as Serializable)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}