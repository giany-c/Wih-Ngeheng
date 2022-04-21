<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";

//    $_POST['menu_name'] = "Hakau Udang";
//    $_POST['menu_category'] = "Steam";
//    $_POST['menu_price'] = "21000";
//    $_POST['menu_description'] = "Udang";
//    $_POST['menu_image'] = "uploads/choipan.jpg";

//menu_id, menu_name, menu_category, menu_price, menu_description, menu_image
if (isset($_POST['menu_name']) && isset($_POST['menu_category']) && isset($_POST['menu_price']) && isset($_POST['menu_description']) && isset($_POST['menu_image'])){
	   $menu_name=$_POST['menu_name'];
	   $menu_category=$_POST['menu_category'];
	   $menu_price=$_POST['menu_price'];
	   $menu_description=$_POST['menu_description'];
	   $menu_image=$_POST['menu_image'];

	   $target_dir = "uploads/food";
	   if(!file_exists($target_dir)){
		   mkdir($target_dir, 0777,true);
	   }
	   $target_dir = $target_dir."/".$menu_name."_".time().".jpeg";

	   if(file_put_contents($target_dir,base64_decode($menu_image))){
	   	$sql=mysqli_query($connect,"INSERT INTO menu(menu_name, menu_category, menu_price, menu_description, menu_image) VALUES('$menu_name','$menu_category', '$menu_price', '$menu_description','http://192.168.1.9/projectmobile/mobile_wihNgeheng/$target_dir')");

		if($sql){
			$response["success"]=1;
		   	$response["message"]="Data berhasil ditambah";
		   	echo json_encode($response);
		} else {
			$response["success"]= -2;
		   	$response["message"]="Data gagal ditambah";
		   	echo json_encode($response);
		}
	   } else {
		$response["success"]=0;
		$response["message"]="Data gagal ditambah";
		echo json_encode($response);
	   }
	} else {
		$response["success"]=-1;
		$response["message"]="Data kosong";
		echo json_encode($response);
	}
	   
//$q=mysql_query("insert into mahasiswa(nama,alamat) values ('$nama','$alamat')");
	//    $q=mysqli_query($connect,"INSERT INTO menu(menu_name, menu_category, menu_price, menu_description) VALUES('$menu_name','$menu_category', '$menu_price', '$menu_description')");
//        $response=array();
	   
// 	   if($q)
// 	   {
// 		   $response["success"]=1;
// 		   $response["message"]="Data berhasil ditambah";
// 		   echo json_encode($response);
// 	   }
// 	   else
// 	   {
// 		   $response["success"]=0;
// 		   $response["message"]="Data gagal ditambah";
// 		   echo json_encode($response);
// 	   }
//    }
// else
//    { 
// 		   $response["success"]=-1;
// 		   $response["message"]="Data kosong";
// 		   echo json_encode($response);
//    }
?>	   
