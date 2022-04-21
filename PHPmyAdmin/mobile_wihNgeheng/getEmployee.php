<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

   $q=mysqli_query($connect, "SELECT * FROM employee");
   $response=array();
   
   if (mysqli_num_rows($q)>0){
	  $response["dataEmployee"]=array();
	  while($r=mysqli_fetch_array($q)){
          $employee=array();
          $employee["employee_id"]=$r["employee_id"];
          $employee["employee_name"]=$r["employee_name"];
          $employee["employee_address"]=$r["employee_address"];
          $employee["employee_phone"]=$r["employee_phone"];
          $employee["employee_email"]=$r["employee_email"];
          $employee["employee_position"]=$r["employee_position"];
          array_push($response["dataEmployee"],$employee);

	  }
      $response["success"]=1;
	  $response["message"]="Data employee berhasil dibaca";
      echo json_encode($response);
   }
   else{ 
	  $response["success"]= 0;
	  $response["message"]="Data kosong";
	  echo json_encode($response);

	}
	  
?>
