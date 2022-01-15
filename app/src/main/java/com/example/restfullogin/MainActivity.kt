package com.example.restfullogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login_id: EditText = findViewById(R.id.login_id)
        val login_pw: EditText = findViewById(R.id.login_password)
        val login_bt: Button = findViewById(R.id.login_button)

        //retrofit object
        // baseURL: 서버 주소 입력, 자동적으로 응답값 JSON을 자바클래스로 변환
        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.219.101:8000") // 여기서 오류남 노트북이라서?
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val loginService = retrofit.create(LoginService::class.java)

        login_bt.setOnClickListener {
            val textid = login_id.text.toString()
            val textpw = login_pw.text.toString()

            loginService.requestLogin(textid, textpw).enqueue(object: Callback<Login> {
                // 통신에 성공
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val login = response.body()
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("알림")
                    dialog.setMessage("code : " + login?.code + " msg : "+login?.msg)
                    dialog.show()
                }
                // web 통신에 실패했을 때 실행
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("실패")
                    dialog.setMessage("실패했습니다")
                    dialog.show()
                }
            })


        }

    }
}