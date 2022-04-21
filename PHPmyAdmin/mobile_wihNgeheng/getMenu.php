<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

   $q=mysqli_query($connect, "SELECT * FROM menu");
   $response=array();
   
   //menu_id, menu_name, menu_category, menu_price, menu_description, menu_image

   if (mysqli_num_rows($q)>0){
	  $response["dataMenu"]=array();
	  while($r=mysqli_fetch_array($q)){
          $menu=array();
          $menu["menu_id"]=$r["menu_id"];
          $menu["menu_name"]=$r["menu_name"];
          $menu["menu_category"]=$r["menu_category"];
          $menu["menu_price"]=$r["menu_price"];
          $menu["menu_description"]=$r["menu_description"];
          $menu["menu_image"]=$r["menu_image"];
          array_push($response["dataMenu"],$menu);
      }
      $response["success"]=1;
	  $response["message"]="Data menu berhasil dibaca";
      echo json_encode($response);
   }
   else{ 
	  $response["success"]= 0;
	  $response["message"]="Data kosong";
	  echo json_encode($response);

	}
	  
?>
