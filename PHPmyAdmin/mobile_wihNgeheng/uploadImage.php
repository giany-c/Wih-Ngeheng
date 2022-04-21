
<?php
//    header('content-type:application/json;charset=utf-8');
//    include "conn.php";

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "wihngeheng_mobile";
// $dbname = "testgambar";

$connect = mysqli_connect($servername, $username, $password);

if($connect){
    $image = $_POST["image"];
//    $name = $_POST["name"];
   $sql = "INSERT INTO imageinfo ('image') VALUES ('$image')";
   $upload_path = "uploads/$name.jpg";

    if (mysqli_query($sql))
    {
        file_put_contents($upload_path, base64_decode($image));
        echo json_encode(array('response' -> 'Image Uploaded Successfully'));

    }
    else{
        echo json_encode(array('response'->'Image upload failed'));
    }
} else {
    echo json_encode(array('response'->'Not connected'));
}
mysqli_close($connect);


?>