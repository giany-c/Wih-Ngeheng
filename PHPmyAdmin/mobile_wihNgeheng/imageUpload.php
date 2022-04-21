<?php
include "conn.php";
// $servername = "localhost";
// $username = "root";
// $password = "";
// $dbname = "wihngeheng_mobile";
// $dbname = "testgambar";

// $connect = mysqli_connect($servername, $username, $password, $dbname);
$target_dir = "uploads/food";

// $_POST['image']="test";
// $_POST['email']="hiliw";
// $_POST['id']="1";


// $email = $_POST['email'];
$menu_id = $_POST['menu_id'];
$menu_image = $_POST['menu_image'];
if(!file_exists($target_dir)){
    mkdir($target_dir,0777,true);
}

$target_dir = $target_dir."/".time().".jpeg";
echo $target_dir;
if(file_put_contents($target_dir,base64_decode($menu_image))){
    // $q=mysqli_query($connect,"UPDATE keranjang SET bukti='$target_dir' WHERE id='$id'");
    $q=mysqli_query($connect,"INSERT INTO menu (menu_image) VALUES ('$target_dir')");
    echo json_encode([
        "Message" => "File berhasil di upload",
        "Status" => "OK",
        "file_path" => $target_dir
    ]);
} else {
    echo json_encode([
        "Message" => "Maaf, file gagal di upload",
        "Status" => "Error"
    ]);
}

?>

<!-- 
include 'conn.php';
$target_dir = "upload/buktiTF";

// $_POST['image']="test";
// $_POST['email']="roy";
// $_POST['id_user']="9";

$image = $_POST['image'];
$email = $_POST['email'];
$id = $_POST['id_order'];

if(!file_exists($target_dir)){
    mkdir($target_dir,0777,true);
}

$target_dir = $target_dir."/".$email."_".time().".jpeg";
if(file_put_contents($target_dir,base64_decode($image))){
    $q=mysqli_query($connect,"UPDATE keranjang SET bukti='$target_dir' WHERE id='$id'");
    echo json_encode([
        "Message" => "File berhasil di upload",
        "Status" => "OK"
    ]);
} else {
    echo json_encode([
        "Message" => "Maaf, file gagal di upload",
        "Status" => "Error"
    ]);
} -->