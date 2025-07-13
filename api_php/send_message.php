<?php
include 'db_connect.php';

header('Content-Type: application/json');

$user_id = $_POST['user_id'] ?? '';
$message = $_POST['message'] ?? '';

if ($user_id == '' || $message == '') {
    echo json_encode(["status" => "fail", "message" => "User ID dan pesan wajib diisi."]);
    exit;
}

$sql = "INSERT INTO messages (user_id, message) VALUES (?, ?)";
$stmt = $conn->prepare($sql);
$stmt->bind_param("is", $user_id, $message);

if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Pesan berhasil dikirim."]);
} else {
    echo json_encode(["status" => "fail", "message" => "Gagal mengirim pesan."]);
}

$stmt->close();
$conn->close();
?>
