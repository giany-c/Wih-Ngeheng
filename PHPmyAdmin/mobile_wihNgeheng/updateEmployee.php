<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";
  	
	// $_POST['id']='11';
	// $_POST['nama']='Bambang';
   	// $_POST['alamat']='Jl Central Park';
   
       if (isset($_POST['employee_id']) && isset($_POST['employee_name']) && isset($_POST['employee_address']) && isset($_POST['employee_phone']) && isset($_POST['employee_email']) && isset($_POST['employee_position']))
   {
	   $employee_id=$_POST['employee_id'];
	   $employee_name=$_POST['employee_name'];
	   $employee_address=$_POST['employee_address'];
	   $employee_phone=$_POST['employee_phone'];
	   $employee_email=$_POST['employee_email'];
	   $employee_position=$_POST['employee_position'];

	   $q=mysqli_query($connect, "UPDATE employee SET employee_name='$employee_name',       
                                                        employee_address='$employee_address',
                                                        employee_phone='$employee_phone',
                                                        employee_email='$employee_email',
                                                        employee_position='$employee_position' WHERE employee_id='$employee_id'");
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