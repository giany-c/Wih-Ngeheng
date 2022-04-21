<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
    
    $user_id = $_POST['user_id'];
    require_once 'connect.php';

    $sql = "SELECT * FROM user WHERE user_id='$user_id' ";
    $response = mysqli_query($conn, $sql);
    $result = array();
    $result['read'] = array();
    if( mysqli_num_rows($response) === 1 ) {
        if ($row = mysqli_fetch_assoc($response)) {
             $h['user_name']        = $row['user_name'] ;
             $h['user_email']       = $row['user_email'] ;
             $h['user_address']     = $row['user_address'] ;
             $h['user_phone']       = $row['user_phone'] ;
             array_push($result["read"], $h);
             $result["success"] = "1";
             echo json_encode($result);
        }
   }
 }else {
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 
     mysqli_close($conn);
 }
 
 ?>