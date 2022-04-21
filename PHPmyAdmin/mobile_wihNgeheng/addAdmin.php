<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

//    $_POST['admin_name'] = "Adlin Benedict";
//    $_POST['admin_email'] = "adlin.benedict@gmail.com";
//    $_POST['admin_password'] = "admin";

//admin_id, admin_name, admin_email,admin_password

if (isset($_POST['admin_name']) && isset($_POST['admin_email']) && isset($_POST['admin_password']))
   {
	   $admin_name=$_POST['admin_name'];
	   $admin_email=$_POST['admin_email'];
       $admin_password = password_hash($_POST['admin_password'], PASSWORD_BCRYPT);
	   
	   //$q=mysql_query("insert into mahasiswa(nama,alamat) values ('$nama','$alamat')");
	   $q=mysqli_query($connect,"INSERT INTO admin(admin_name, admin_email, admin_password) VALUES('$admin_name', '$admin_email', '$admin_password')");
       $response=array();
	   
	   if($q)
	   {
		   $response["success"]=1;
		   $response["message"]="Data berhasil ditambah";
		   echo json_encode($response);
	   }
	   else
	   {
		   $response["success"]=0;
		   $response["message"]="Data gagal ditambah";
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
