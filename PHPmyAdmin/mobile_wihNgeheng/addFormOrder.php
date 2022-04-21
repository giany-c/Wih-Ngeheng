<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";
   
	if (isset($_POST['nama']) && isset($_POST['telp']) && isset($_POST['tanggal']) && isset($_POST['lokasi']) && isset($_POST['layanan']) && isset($_POST['total']) && isset($_POST['notes']) && isset($_POST['coderef']))
   {
	   $nama = $_POST['nama'];
	   $telp = $_POST['telp'];
	   $tanggal = $_POST['tanggal'];
	   $lokasi = $_POST['lokasi'];
       $layanan = $_POST['layanan'];
	   $total = $_POST['total'];
       $notes = $_POST['notes'];
	   $coderef = $_POST['coderef'];

	   $sql = "INSERT INTO ordercustomer(nama,telp,tanggal,lokasi,layanan,total,notes,coderef) VALUES ('$nama','$telp','$tanggal','$lokasi','$layanan','$total', '$notes', '$coderef')";
	   $q = mysqli_query($connect,$sql);
	   $response=array();
	   
	   if($q)
	   {
		   $response["success"]=1;
		   $response["message"]="data berhasil ditambah";
		   echo json_encode($response);
	   }
    else
	   {
		   $response["success"]=0;
		   $response["message"]="data gagal ditambah";
		   echo json_encode($response);
	   }
   }
    else
    { 
            $response["success"]=-1;
            $response["message"]="data kosong";
            echo json_encode($response);
    }

?>
