<?php
$host = "localhost"; // tetap 'localhost' jika database berada di hosting yang sama
$user = "u481950604_Wijaya";
$pass = "Wijaya2025";
$dbname = "u481950604_Kai";

$conn = new mysqli($host, $user, $password, $db);
if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}

$sql = "SELECT * FROM orders ORDER BY id DESC";
$result = $conn->query($sql);

$data = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $data[] = $row;
    }
    echo json_encode($data);
} else {
    echo json_encode([]);
}
$conn->close();
?>
