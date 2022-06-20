package com.example.filmbase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.filmbase.MainActivity
import com.example.filmbase.R
import com.example.filmbase.firebase.DbOperations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val et_login = findViewById<EditText>(R.id.et_login)
        val et_pass = findViewById<EditText>(R.id.et_password)

        findViewById<TextView>(R.id.tv_register).setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        findViewById<Button>(R.id.bt_login).setOnClickListener {
            when{
                TextUtils.isEmpty(et_login.text.toString().trim{it <= ' '})->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_pass.text.toString().trim{it <= ' '})->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else->{

                val email:String = et_login.text.toString().trim{it <= ' '}
                val password:String = et_pass.text.toString().trim{it <= ' '}

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                            task->
                        if(task.isSuccessful){

                            Toast.makeText(
                                this@LoginActivity,
                                "You are logged succesfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("userID",FirebaseAuth.getInstance().currentUser?.uid?:"no ID")
                            intent.putExtra("email",email)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(
                                this@LoginActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            }
        }
    }
}