<?php

include('kon.php');

$stmt = $conn->prepare("SELECT * FROM menu ORDER BY menu_id");

$stmt ->execute();

$stmt -> bind_result($menu_id, $menu_name, $menu_category, $menu_price, $menu_description, $menu_image);

$menu = array();

while($stmt ->fetch()){

    $temp = array();

 $temp['menu_id'] = $menu_id;
 $temp['menu_name'] = $menu_name;
 $temp['menu_category'] = $menu_category;
 $temp['menu_price'] = $menu_price;
 $temp['menu_description'] = $menu_description;
 $temp['menu_image'] = $menu_image;

 array_push($menu,$temp);
 }

 echo json_encode($menu);

?>