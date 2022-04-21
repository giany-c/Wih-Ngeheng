<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['menu_id'])){
    $menu_id=$_POST['menu_id'];

    $q=mysqli_query($connect,"DELETE FROM menu WHERE menu_id='$menu_id'");
    $response=array();

    if($q){
        $response["success"]=1;
        $response["message"]="Data berhasil delete";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]="data gagal di hapus";
        echo json_encode($response);
    }
}else{
    $response["success"]=-1;
    $response["message"]="Data kosong";
    echo json_encode($response);
}
?>