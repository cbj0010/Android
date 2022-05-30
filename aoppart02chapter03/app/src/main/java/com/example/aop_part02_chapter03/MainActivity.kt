package com.example.aop_part02_chapter03

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }
    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false  //changepassword버튼을 누른경우에 다른 작업을 할 수 없게 예외처리


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {

            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener   //이렇게 해줘야 oncreate에서 나가는건지 람다 함수에서 나가는건지 알 수 있움
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                //패스워드 성공
               startActivity(Intent(this, DiarayActivity::class.java))  //password가 정상적으로 맞을때 실행
            } else {
                showErrorAlertDialog()
                //실패
            }
        }

        changePasswordButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswordMode) { //true일때
                //번호를 저장하는 기능
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            } else {
                //changepasswordmode가 활성화 :: 비밀번호가 맞는지를 체크

                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    //패스워드 성공
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()

                    changePasswordButton.setBackgroundColor(Color.RED)  //활성화가 된 것을 알려주기 위한 색 변환

                } else {
                    showErrorAlertDialog()
                    //실패
                }
            }


        }
    }

    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못 되었습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}