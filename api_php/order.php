<?php
// order.php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Koneksi database
    include 'db_connect.php';

    // Pastikan koneksi sukses
    if ($conn->connect_error) {
        echo json_encode([
            "status" => "error",
            "message" => "Koneksi gagal: " . $conn->connect_error
        ]);
        exit;
    }

    // Ambil dan validasi data dari POST
    $user_id    = isset($_POST['user_id']) ? (int) $_POST['user_id'] : 0;
    $product_id = isset($_POST['product_id']) ? (int) $_POST['product_id'] : 0;
    $quantity   = isset($_POST['quantity']) ? (int) $_POST['quantity'] : 0;

    if ($user_id > 0 && $product_id > 0 && $quantity > 0) {
        $order_date = date('Y-m-d H:i:s');

        // Query simpan pesanan
        $stmt = $conn->prepare("INSERT INTO orders (user_id, product_id, quantity, order_date) VALUES (?, ?, ?, ?)");
        if ($stmt) {
            $stmt->bind_param("iiis", $user_id, $product_id, $quantity, $order_date);
            if ($stmt->execute()) {
                echo json_encode([
                    "status" => "success",
                    "message" => "Pesanan berhasil disimpan"
                ]);
            } else {
                echo json_encode([
                    "status" => "error",
                    "message" => "Gagal mengeksekusi query: " . $stmt->error
                ]);
            }
            $stmt->close();
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "Gagal mempersiapkan query: " . $conn->error
            ]);
        }
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Data tidak lengkap atau tidak valid"
        ]);
    }

    $conn->close();
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Metode tidak diizinkan"
    ]);
}
?>
