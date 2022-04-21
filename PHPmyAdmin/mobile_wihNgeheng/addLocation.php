<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

//    $_POST['location_name'] = "Muara Karang";
//    $_POST['location_address'] = "Jl. Pluit Karang Utara Blok A4U No. 65A, Muara Karang, Penjaringan, Jakarta Utara";
//    $_POST['location_region'] = "Jakarta";
//    $_POST['location_phone'] = "081298153977";
//    $_POST['location_openinghours'] = "Open for 24 Hours";

//location_id, location_name, location_address, location_region, location_phone, location_openinghours
if (isset($_POST['location_name']) && isset($_POST['location_address']) && isset($_POST['location_region']) && isset($_POST['location_phone']) && isset($_POST['location_openinghours']))
   {
	   $location_name=$_POST['location_name'];
	   $location_address=$_POST['location_address'];
	   $location_region=$_POST['location_region'];
	   $location_phone=$_POST['location_phone'];
	   $location_openinghours=$_POST['location_openinghours'];
	   
	   //$q=mysql_query("insert into mahasiswa(nama,alamat) values ('$nama','$alamat')");
	   $q=mysqli_query($connect,"INSERT INTO location(location_name, location_address, location_region, location_phone, location_openinghours) VALUES('$location_name','$location_address', '$location_region', '$location_phone','$location_openinghours')");
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