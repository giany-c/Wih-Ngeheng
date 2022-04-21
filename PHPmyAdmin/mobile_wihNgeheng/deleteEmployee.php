<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['employee_id'])){
    $employee_id=$_POST['employee_id'];

    $q=mysqli_query($connect,"DELETE FROM employee WHERE employee_id='$employee_id'");
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