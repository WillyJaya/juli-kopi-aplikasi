<?php
$host = "localhost"; // tetap 'localhost' jika database berada di hosting yang sama
$user = "u481950604_Wijaya";
$pass = "Wijaya2025";
$dbname = "u481950604_Kai";

// Buat koneksi
$conn = new mysqli($host, $user, $pass, $dbname);

// Cek koneksi
if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

// Query gabung tabel orders dan products, tambahkan user_id jika memang ada di tabel orders
$sql = "
    SELECT 
        o.id,
        o.user_id,
        p.name AS product_name,
        o.quantity,
        o.order_date
    FROM orders o
    JOIN products p ON o.product_id = p.id
    ORDER BY o.order_date DESC
";

$result = $conn->query($sql);

// Set header agar JSON
header('Content-Type: application/json');

$data = [];

if ($result && $result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $data[] = [
            'user_id' => $row['user_id'],
            'nama_produk' => $row['product_name'],
            'jumlah' => $row['quantity'],
            'order_date' => $row['order_date']
        ];
    }
}

// Output JSON
echo json_encode($data);

// Tutup koneksi
$conn->close();
?>
