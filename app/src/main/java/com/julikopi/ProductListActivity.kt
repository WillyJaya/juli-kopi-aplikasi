package com.julikopi

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ProductListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // Inisialisasi ListView dari layout
        val listView = findViewById<ListView>(R.id.listViewProducts)

        // URL API dari produk
        val url = "https://julikopi.web.id/api/products.php"

        // Request queue dari Volley
        val queue = Volley.newRequestQueue(this)

        // Membuat request untuk mengambil data produk
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    // Logging response untuk debugging
                    Log.d("API_RESPONSE", response)

                    val jsonArray = JSONArray(response)
                    val list = ArrayList<String>()

                    // Parsing setiap item produk dari JSON
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val id = obj.getInt("id")
                        val name = obj.getString("name")
                        val price = obj.getInt("price")
                        list.add("ID: $id | $name - Rp$price")
                    }

                    // Menampilkan data ke ListView
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
                    listView.adapter = adapter

                } catch (e: Exception) {
                    Log.e("JSON_ERROR", "Error parsing JSON", e)
                }
            },
            { error ->
                // Logging error saat request gagal
                Log.e("API_ERROR", "Request error", error)
            }
        )

        // Menambahkan request ke dalam queue
        queue.add(request)
    }
}
