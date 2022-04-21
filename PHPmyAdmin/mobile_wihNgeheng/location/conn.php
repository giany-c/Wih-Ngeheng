<?php
$server="localhost";
$username="root";
$password="";
$database="wihngeheng_mobile";
$conn=mysqli_connect($server,$username,$password,$database);
if(!$conn)
    die("Connection failed :".mysqli_connect_errno);
?>