<?php
header('content-type:application/json;charset=utf-8');
include "conn.php";

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $user_id=$_POST['user_id'];
    
    $q=mysqli_query($connect, "SELECT * FROM user WHERE user_id = '$user_id'");
    $response=array();
    $response['read'] = array();

    if (mysqli_num_rows($q)>0){
        $response["dataUser"]=array();
        while($r=mysqli_fetch_array($q)){
            $user=array();
            $user["user_id"]=$r["user_id"];
            $user["user_name"]=$r["user_name"];
            $user["user_address"]=$r["user_address"];
            $user["user_phone"]=$r["user_phone"];
            $user["user_email"]=$r["user_email"];
            // $user_password = password_hash($_POST['user_password'], PASSWORD_DEFAULT);
            $user["user_password"]=$r["user_password"];
            $user["user_role"]=$r["user_role"];
            array_push($response["dataUser"],$user);
  
        }
        $response["success"]=1;
	    $response["message"]="Data user berhasil dibaca";
        echo json_encode($response);
   }
   else{ 
	    $response["success"]= 0;
	    $response["message"]="Data kosong";
	    echo json_encode($response);
	}



}
?>