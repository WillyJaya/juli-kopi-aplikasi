<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include 'db_connect.php';

header('Content-Type: application/json');

// Ambil data dari POST dan hilangkan whitespace
$name = trim($_POST['name'] ?? '');
$email = trim($_POST['email'] ?? '');
$password = trim($_POST['password'] ?? '');

// Validasi field kosong
if (empty($name) || empty($email) || empty($password)) {
    echo json_encode(["status" => "fail", "message" => "Semua field wajib diisi."]);
    exit;
}

// Cek apakah email sudah terdaftar dengan prepared statement
$checkStmt = $conn->prepare("SELECT id FROM users WHERE email = ?");
$checkStmt->bind_param("s", $email);
$checkStmt->execute();
$checkStmt->store_result();

if ($checkStmt->num_rows > 0) {
    echo json_encode(["status" => "fail", "message" => "Email sudah terdaftar."]);
    $checkStmt->close();
    exit;
}
$checkStmt->close();

// Hash password sebelum disimpan
$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// Simpan user baru
$insertStmt = $conn->prepare("INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
if (!$insertStmt) {
    echo json_encode(["status" => "fail", "message" => "Gagal mempersiapkan pernyataan."]);
    exit;
}

$insertStmt->bind_param("sss", $name, $email, $hashedPassword);

if ($insertStmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Registrasi berhasil."]);
} else {
    echo json_encode(["status" => "fail", "message" => "Gagal menyimpan data."]);
}

$insertStmt->close();
$conn->close();
?>
