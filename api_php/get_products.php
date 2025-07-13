<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include 'db_connect.php';

header('Content-Type: application/json');

$sql = "SELECT id, name, price FROM products ORDER BY id DESC";
$result = $conn->query($sql);

if (!$result) {
    echo json_encode([
        "status" => "error",
        "message" => "Query error: " . $conn->error
    ]);
    exit;
}

$products = [];

while ($row = $result->fetch_assoc()) {
    $products[] = $row;
}

echo json_encode([
    "status" => "success",
    "products" => $products
]);

$conn->close();
?>
