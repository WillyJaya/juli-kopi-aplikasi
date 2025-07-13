<?php
include 'db_connect.php';

header('Content-Type: application/json');

$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';

if ($email == '' || $password == '') {
    echo json_encode(["status" => "fail", "message" => "Email dan password wajib diisi."]);
    exit;
}

$sql = "SELECT id, name, password FROM users WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows == 1) {
    $user = $result->fetch_assoc();
    if (password_verify($password, $user['password'])) {
        echo json_encode([
            "status" => "success",
            "message" => "Login berhasil.",
            "user" => [
                "id" => $user['id'],
                "name" => $user['name'],
                "email" => $email
            ]
        ]);
    } else {
        echo json_encode(["status" => "fail", "message" => "Password salah."]);
    }
} else {
    echo json_encode(["status" => "fail", "message" => "Email tidak ditemukan."]);
}

$stmt->close();
$conn->close();
?>
