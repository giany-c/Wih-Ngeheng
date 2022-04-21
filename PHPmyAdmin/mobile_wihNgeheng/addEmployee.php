<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

//    $_POST['employee_name'] = "Adlin Benedict";
//    $_POST['employee_address'] = "Jl. Kedoya Raya";
//    $_POST['employee_phone'] = "085887483649";
//    $_POST['employee_email'] = "adlin.benedict@gmail.com";
//    $_POST['employee_position'] = "Manager";

//employee_id, employee_name, employee_address, employee_phone, employee_email, employee_position
if (isset($_POST['employee_name']) && isset($_POST['employee_address']) && isset($_POST['employee_phone']) && isset($_POST['employee_email']) && isset($_POST['employee_position']))
   {
	   $employee_name=$_POST['employee_name'];
	   $employee_address=$_POST['employee_address'];
	   $employee_phone=$_POST['employee_phone'];
	   $employee_email=$_POST['employee_email'];
	   $employee_position=$_POST['employee_position'];
	   
	   //$q=mysql_query("insert into mahasiswa(nama,alamat) values ('$nama','$alamat')");
	   $q=mysqli_query($connect,"INSERT INTO employee(employee_name, employee_address, employee_phone, employee_email, employee_position) VALUES('$employee_name','$employee_address', '$employee_phone','$employee_email', '$employee_position')");
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