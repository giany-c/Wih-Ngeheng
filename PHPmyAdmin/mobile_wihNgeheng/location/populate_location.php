<?php
require_once "conn.php";
$sql = "SELECT * FROM location";
if(!$conn->query($sql)){
    echo "Error in connecting to database";
} else {
    $result = $conn->query($sql);
    if($result->num_rows>0){
        $return_arr['location'] = array();
        while($row = $result->fetch_array()){
            array_push($return_arr['location'], array(
                'location_id'=>$row['location_id'],
                'location_name'=>$row['location_name']
            ));
        }
        echo json_encode($return_arr);
    }
}
?>