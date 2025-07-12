package com.julikopi

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject // ✅ Tambahkan import ini

class OrderActivity : AppCompatActivity() {

    private lateinit var etProductId: EditText
    private lateinit var etUserId: EditText
    private lateinit var etQuantity: EditText
    private lateinit var etOrderDate: EditText
    private lateinit var btnOrder: Button

    private val produkList = ArrayList<String>()
    private val produkIdList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Inisialisasi komponen dari layout XM
        etUserId = findViewById(R.id.etUserId)
        etProductId = findViewById(R.id.etProductId)
        etQuantity = findViewById(R.id.etQuantity)
        etOrderDate = findViewById(R.id.etOrderDate)
        btnOrder = findViewById(R.id.btnOrder)

        ambilDataProduk()

        btnOrder.setOnClickListener {
            val userId = etUserId.text.toString().trim()
            val productId = etProductId.text.toString().trim()
            val quantity = etQuantity.text.toString().trim()
            val orderDate = etOrderDate.text.toString().trim()

            if (userId.isNotEmpty() && productId.isNotEmpty() &&
                quantity.isNotEmpty() && orderDate.isNotEmpty()) {

                if (quantity.toIntOrNull() != null && productId.toIntOrNull() != null) {
                    kirimPesanan(userId, productId.toInt(), quantity.toInt(), orderDate)
                } else {
                    Toast.makeText(this, "Jumlah harus berupa angka", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ambilDataProduk() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://julikopi.web.id/api/get_products.php" // ✅ endpoint GET produk

        val request = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("ProdukResponse", "Response dari server: $response")
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getString("status") == "success") {
                        val jsonArray = jsonObject.getJSONArray("products")
                        produkList.clear()
                        produkIdList.clear()

                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            val id = obj.getInt("id")
                            val nama = obj.getString("name")

                            produkIdList.add(id)
                            produkList.add(nama)
                            Log.d("ProdukItem", "ID: $id, Nama: $nama")
                        }

                    } else {
                        Toast.makeText(this, "Gagal load data produk", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Log.e("ProdukError", "Parsing error: ${e.message}")
                    Toast.makeText(this, "Gagal parsing data produk", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("ProdukError", "Volley error: ${error.message}")
                Toast.makeText(this, "Gagal mengambil data produk", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }

    private fun kirimPesanan(userId: String, productId: Int, quantity: Int, orderDate: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://julikopi.web.id/api/order.php" // ✅ endpoint POST pesanan

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                Log.d("OrderResponse", "Pesanan berhasil: $response")
                Toast.makeText(this, "Pesanan berhasil dikirim", Toast.LENGTH_SHORT).show()
                etQuantity.text.clear()
                etOrderDate.text.clear()
            },
            { error ->
                Log.e("OrderError", "Gagal mengirim pesanan: ${error.message}")
                Toast.makeText(this, "Gagal mengirim pesanan", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["user_id"] = userId
                params["product_id"] = productId.toString()
                params["quantity"] = quantity.toString()
                params["order_date"] = orderDate
                return params
            }
        }

        queue.add(request)
    }
}
