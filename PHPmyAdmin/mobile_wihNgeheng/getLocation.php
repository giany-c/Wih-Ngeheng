<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

   $q=mysqli_query($connect, "SELECT * FROM location");
   $response=array();
   
   //location_id, location_name, location_address, location_region, location_phone, location_openinghours

   if (mysqli_num_rows($q)>0){
	  $response["dataLocation"]=array();
	  while($r=mysqli_fetch_array($q)){
          $location=array();
          $location["location_id"]=$r["location_id"];
          $location["location_name"]=$r["location_name"];
          $location["location_address"]=$r["location_address"];
          $location["location_region"]=$r["location_region"];
          $location["location_phone"]=$r["location_phone"];
          $location["location_openinghours"]=$r["location_openinghours"];
          array_push($response["dataLocation"],$location);

	  }
      $response["success"]=1;
	  $response["message"]="Data location berhasil dibaca";
      echo json_encode($response);
   }
   else{ 
	  $response["success"]= 0;
	  $response["message"]="Data kosong";
	  echo json_encode($response);

	}
	  
?>
