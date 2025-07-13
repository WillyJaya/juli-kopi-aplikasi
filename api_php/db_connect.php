<?php
$host = "localhost"; // tetap 'localhost' jika database berada di hosting yang sama
$user = "u481950604_Wijaya";
$pass = "Wijaya2025";
$dbname = "u481950604_Kai";

$conn = new mysqli($host, $user, $pass, $dbname);

if ($conn->connect_error) {
    die("Koneksi gagal: " . $conn->connect_error);
}
?>
