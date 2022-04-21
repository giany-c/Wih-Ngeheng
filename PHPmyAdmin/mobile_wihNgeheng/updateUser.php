<?php
header('content-type:application/json;charset=utf-8');
include "conn.php";

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $user_id=$_POST['user_id'];
    $user_name=$_POST['user_name'];
	$user_address=$_POST['user_address'];
	$user_phone=$_POST['user_phone'];
	$user_email=$_POST['user_email'];
    $user_password = password_hash($_POST['user_password'], PASSWORD_BCRYPT);
	$user_role=$_POST['user_role'];

        $q=mysqli_query($connect, "UPDATE user SET user_name='$user_name', user_address='$user_address', user_phone = '$user_phone', user_email='$user_email', user_password='$user_password' WHERE user_id='$user_id'");

	   $response=array();
	   if($q)
	   {
		   $response["success"]=1;
		   $response["message"]="Data berhasil diupdate";
		   echo json_encode($response);
	   }
	   else
	   {
		   $response["success"]=0;
		   $response["message"]="Data gagal diupdate";
		   echo json_encode($response);
	   }
   }
   else
   { 
		   $response["success"]=-1;
		   $response["message"]="Data kosong";
		   echo json_encode($response);

   }
?>