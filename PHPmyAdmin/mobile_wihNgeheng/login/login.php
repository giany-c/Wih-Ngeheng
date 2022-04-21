<?php
require_once 'connect.php';

if (isset($_POST['user_email']) && isset($_POST['user_password'])){
    $user_email = $_POST['user_email'];
    $user_password = $_POST['user_password'];
    
    $sql = "SELECT * FROM user WHERE user_email = '$user_email'";

    $response = mysqli_query($conn, $sql);
    
    $result = array();
    $result['login'] = array();

    if(mysqli_num_rows($response) === 1){
        $row = mysqli_fetch_assoc($response);
        if (password_verify($user_password,  $row['user_password'])){
            $index['user_email'] = $row['user_email'];
            $index['user_role'] = $row['user_role'];
            $index['user_name'] = $row['user_name'];
            $index['user_address'] = $row['user_address'];
            $index['user_phone'] = $row['user_phone'];
            $index['user_id'] = $row['user_id'];
            
            array_push($result['login'], $index);
           
            $result['success'] = "1";
            $result['message'] = "success";

            $result['user_name'] = $index['user_name'];
            $result['user_address'] = $index['user_address'];
            $result['user_phone'] = $index['user_phone'];
            $result['user_id'] = $index['user_id'];
            $result['user_email'] = $index['user_email'];
            $result['role'] = $row['user_role'];

            echo json_encode($result);
            mysqli_close($conn);
        } else {
            $result['success'] = "0";
            $result['message'] = "error";

            echo json_encode($result);
            mysqli_close($conn);
        }

    } else {
        $result['success'] = "0";
        $result['message'] = "error";
        echo json_encode($result);

        mysqli_close($conn);
    }
}
?>