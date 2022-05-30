package com.example.aop_part02_chapter03

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiarayActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())   //main thread에 연결된 heandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(
            detailPreferences.getString(
                "detail",
                ""
            )
        ) //diaryEdittext에 입력되어 있는 값중에서 detail이라고 저장 된 값을 가져온다

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", diaryEditText.text.toString())  //수정된 값을 detail에 넣어주는 것
            }
            Log.d("detail", "SAVE!!! ${diaryEditText.text.toString()}")
        }


        diaryEditText.addTextChangedListener {//임시저장 기능

            Log.d("diaryActivity", "TextChange::$it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 1000) //1초 마다 실행
        }
    }

}