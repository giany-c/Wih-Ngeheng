<?php
   header('content-type:application/json;charset=utf-8');
   include "conn.php";
   

          //location_id, location_name, location_address, location_region, location_phone, location_openinghours
    if (isset($_POST['location_id']) && isset($_POST['location_name']) && isset($_POST['location_address']) && isset($_POST['location_region']) && isset($_POST['location_phone']) && isset($_POST['location_openinghours']))
	{
		$location_id=$_POST['location_id'];
		$location_name=$_POST['location_name'];
		$location_address=$_POST['location_address'];
		$location_region=$_POST['location_region'];
		$location_phone=$_POST['location_phone'];
		$location_openinghours=$_POST['location_openinghours'];

		$q=mysqli_query($connect, "UPDATE location SET location_name='$location_name',       
															location_address='$location_address',
															location_region='$location_region',
															location_phone='$location_phone',
															location_openinghours='$location_openinghours'
															WHERE location_id='$location_id'");
		$response=array();
		if($q)
		{
			$response["success"]=1;
			$response["message"]="Data berhasil diupdate";
			echo json_encode($response);
		}
		else
		{
			$response["success"]=0;
			$response["message"]="Data gagal diupdate";
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