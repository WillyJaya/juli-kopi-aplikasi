package com.julikopi

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val listView = findViewById<ListView>(R.id.listViewHistory)
        val queue = Volley.newRequestQueue(this)
        val url = "https://julikopi.web.id/api/history.php"

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    val list = ArrayList<String>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)

                        val userId = obj.getString("user_id")
                        val namaProduk = obj.getString("nama_produk")
                        val jumlah = obj.getString("jumlah")
                        val tanggal = obj.getString("order_date")

                        val itemText = "User ID: $userId\nProduk: $namaProduk\nJumlah: $jumlah\nTanggal: $tanggal"
                        list.add(itemText)
                    }

                    val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
                    listView.adapter = adapter
                } catch (e: JSONException) {
                    Toast.makeText(this, "Gagal parsing data!", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this, "Gagal mengambil data dari server", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            })

        queue.add(request)
    }
}
