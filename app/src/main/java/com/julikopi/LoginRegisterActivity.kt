package com.julikopi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class LoginRegisterActivity : AppCompatActivity() {

    // Login View
    private lateinit var loginLayout: LinearLayout
    private lateinit var edtEmailLogin: EditText
    private lateinit var edtPasswordLogin: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSwitchToRegister: TextView
 
    // Register View
    private lateinit var registerLayout: LinearLayout
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvSwitchToLogin: TextView

    private val BASE_URL_LOGIN = "https://julikopi.web.id/api/login.php"
    private val BASE_URL_REGISTER = "https://julikopi.web.id/api/register.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // Initialize Login View
        loginLayout = findViewById(R.id.loginLayout)
        edtEmailLogin = findViewById(R.id.edtEmailLogin)
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        tvSwitchToRegister = findViewById(R.id.tvSwitchToRegister)

        // Initialize Register View
        registerLayout = findViewById(R.id.registerLayout)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvSwitchToLogin = findViewById(R.id.tvSwitchToLogin)

        // Default tampil login
        showLoginLayout()

        btnLogin.setOnClickListener { view ->
            val email = edtEmailLogin.text.toString().trim()
            val password = edtPasswordLogin.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showSnackbar(view, "Email dan Password wajib diisi")
            } else {
                loginUser(email, password, view)
            }
        }

        btnRegister.setOnClickListener { view ->
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showSnackbar(view, "Semua field wajib diisi")
            } else {
                registerUser(name, email, password, view)
            }
        }

        tvSwitchToRegister.setOnClickListener {
            showRegisterLayout()
        }

        tvSwitchToLogin.setOnClickListener {
            showLoginLayout()
        }
    }

    private fun showLoginLayout() {
        loginLayout.visibility = View.VISIBLE
        registerLayout.visibility = View.GONE
    }

    private fun showRegisterLayout() {
        loginLayout.visibility = View.GONE
        registerLayout.visibility = View.VISIBLE
    }

    private fun loginUser(email: String, password: String, view: View) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.POST, BASE_URL_LOGIN,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    if (status == "success") {
                        showSnackbar(view, "Login berhasil")

                        // Pindah ke MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showSnackbar(view, message)
                    }
                } catch (e: Exception) {
                    showSnackbar(view, "Terjadi kesalahan saat parsing data")
                }
            },
            {
                showSnackbar(view, "Login gagal, cek koneksi internet")
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        queue.add(stringRequest)
    }

    private fun registerUser(name: String, email: String, password: String, view: View) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.POST, BASE_URL_REGISTER,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    val message = jsonResponse.getString("message")

                    if (status == "success") {
                        showSnackbar(view, "Registrasi berhasil, silakan login")
                        showLoginLayout()
                    } else {
                        showSnackbar(view, message)
                    }
                } catch (e: Exception) {
                    showSnackbar(view, "Terjadi kesalahan saat parsing data")
                }
            },
            {
                showSnackbar(view, "Registrasi gagal, cek koneksi internet")
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        queue.add(stringRequest)
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
