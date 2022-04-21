<?php

include('kon.php');
$stmt = $conn->prepare("SELECT m.menu_id, m.menu_name, m.menu_image, m.menu_category, m.menu_price, m.menu_description
FROM menu m, favorit f
WHERE m.menu_id = f.menu_id;");

$stmt ->execute();

$stmt -> bind_result($menu_id, $menu_name, $menu_image, $menu_category, $menu_price, $menu_description);

$menu = array();

while($stmt ->fetch()){

    $temp = array();

    $temp['menu_id'] = $menu_id;
    $temp['menu_name'] = $menu_name;
    $temp['menu_image'] = $menu_image;
    $temp['menu_category'] = $menu_category;
    $temp['menu_price'] = $menu_price;
    $temp['menu_description'] = $menu_description;

 array_push($menu,$temp);
 }

 echo json_encode($menu);

?>