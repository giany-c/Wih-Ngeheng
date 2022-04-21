<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wihngeheng_mobile";
// $dbname = "testgambar";

// Create connection
// $connect = new mysqli($servername, $username, $password, $dbname);
$connect = mysqli_connect($servername, $username, $password, $dbname);

$conn=mysqli_connect($servername,$username,$password,$dbname);
if(!$conn)
    die("Connection failed :".mysqli_connect_errno);

// Check connection
// if (!$connect) {
//   die("Connection failed: " . mysqli_connect_error());
// }
// echo "Connected successfully";
// $connect = mysqli_connect($servername, $username, $password) or die ("Koneksi Gagal!");
// mysqli_select_db($connect, $dbname) or die ("Database belum siap!");
?>

