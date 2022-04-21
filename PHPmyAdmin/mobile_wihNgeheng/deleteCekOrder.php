<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

// $_POST['coderef']="AFSGH";

if(isset($_POST['coderef'])){
    $coderef=$_POST['coderef'];

    $q=mysqli_query($connect,"DELETE FROM ordercustomer WHERE coderef='$coderef'");
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