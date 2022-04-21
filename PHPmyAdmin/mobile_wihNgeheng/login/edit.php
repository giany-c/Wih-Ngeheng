<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $user_name = $_POST['user_name'];
    $user_email = $_POST['user_email'];
    $user_address = $_POST['user_address'];
    $user_phone = $_POST['user_phone'];
    $user_id = $_POST['user_id'];
    
    $user_id = $_POST['user_password'];
    $user_password = password_hash($user_password, PASSWORD_BCRYPT);
    

    require_once 'connect.php';

    $sql = "UPDATE user SET user_name='$user_name', user_email='$user_email', user_address='$user_address', user_phone='$user_phone', user_password='$user_password' WHERE user_id='$user_id' ";

    if(mysqli_query($conn, $sql)) {

          $result["success"] = "1";
          $result["message"] = "success";

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
