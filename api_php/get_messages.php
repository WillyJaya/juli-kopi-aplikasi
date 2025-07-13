<?php
include 'db_connect.php';

header('Content-Type: application/json');

$user_id = $_GET['user_id'] ?? '';

if ($user_id == '') {
    echo json_encode(["status" => "fail", "message" => "User ID diperlukan."]);
    exit;
}

$sql = "SELECT id, message, sent_at FROM messages WHERE user_id = ? ORDER BY sent_at DESC";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

$messages = [];

while ($row = $result->fetch_assoc()) {
    $messages[] = $row;
}

echo json_encode([
    "status" => "success",
    "messages" => $messages
]);

$stmt->close();
$conn->close();
?>
