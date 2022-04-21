<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $user_name = $_POST['user_name'];
    $user_email = $_POST['user_email'];
    $user_address = $_POST['user_address'];
    $user_phone = $_POST['user_phone'];
    $user_id = $_POST['user_id'];

    require_once 'connect.php';

    $sql = "UPDATE user SET user_name='$user_name', user_email='$user_email', user_address='$user_address', user_phone='$user_phone' 
    WHERE user_id='$user_id'";

    $dbc = mysqli_connect('localhost', 'root', '', 'wihngeheng_mobile') or die('Error connecting to MySQL server');
    $check=mysqli_query($dbc,"select * from user where user_email='$user_email' and user_id!='$user_id'");
    $checkrows=mysqli_num_rows($check);

    if($checkrows<=0) {
        if(mysqli_query($conn, $sql)) {
            $result["success"] = "1";
            $result["message"] = "success";
    
            echo json_encode($result);
            mysqli_close($conn);
    
        } 
    } else {
            $result["success"] = "0";
            $result["message"] = "error";
    
            echo json_encode($result);
            mysqli_close($conn);
    }
         
  }

else{

   $result["success"] = "0";
   $result["message"] = "error!";
   echo json_encode($result);

   mysqli_close($conn);
}

?>
