<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

   $q=mysqli_query($connect, "SELECT * FROM ordercustomer");
   $response=array();
   
   if (mysqli_num_rows($q)>0){
   $response["dataOrder"]=array();
   while($data=mysqli_fetch_array($q)){
          $ordercustomer=array();
          $ordercustomer["nama"]=$data["nama"];
          $ordercustomer["telp"]=$data["telp"];
          $ordercustomer["tanggal"]=$data["tanggal"];
          $ordercustomer["lokasi"]=$data["lokasi"];
          $ordercustomer["layanan"]=$data["layanan"];
          $ordercustomer["total"]=$data["total"];
          $ordercustomer["notes"]=$data["notes"];
          $ordercustomer["coderef"]=$data["coderef"];
          array_push($response["dataOrder"],$ordercustomer);
     }
      $response["success"]=1;
   $response["message"]="Data order berhasil dibaca";
      echo json_encode($response);
   }
   else{ 
   $response["success"]= 0;
   $response["message"]="Data kosong";
   echo json_encode($response);
 }
   
?>