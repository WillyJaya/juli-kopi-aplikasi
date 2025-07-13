<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
header("Content-Type: application/json");

// Konfigurasi koneksi database
$host = "localhost"; // tetap 'localhost' jika database berada di hosting yang sama
$user = "u481950604_Wijaya";
$pass = "Wijaya2025";
$dbname = "u481950604_Kai";

// Membuat koneksi
$conn = new mysqli($host, $user, $pass, $dbname);

// Periksa koneksi
if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

// Query untuk mengambil data produk
$sql = "SELECT id, name, price FROM products"; // kolom 'image' sudah dihapus
$result = $conn->query($sql);

// Buat array untuk menampung data produk
$produk = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $produk[] = $row;
    }
} else {
    // Tambahkan ini untuk debug
    $produk = ["message" => "Data kosong"];
}
// Set header sebagai JSON
header('Content-Type: application/json');
echo json_encode($produk);

// Tutup koneksi
$conn->close();
?>
