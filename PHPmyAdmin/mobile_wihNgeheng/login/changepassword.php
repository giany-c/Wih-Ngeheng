<?php
require_once 'connect.php';

$oldpass = $_POST['oldpass'];
$newpass = $_POST['newpass'];
$confirmpass = $_POST['confirmpass'];
$email = $_POST['email'];
$sql = "SELECT * FROM user WHERE user_email = '$email'";
    $query = mysqli_query($conn, $sql);
    if(mysqli_num_rows($query) === 1){
        if($newpass == $confirmpass){
            $row = mysqli_fetch_assoc($query);
            if (password_verify($oldpass,  $row['user_password'])){
                $newpass = password_hash($newpass, PASSWORD_BCRYPT);
                $confirmpass = password_hash($confirmpass, PASSWORD_BCRYPT);
                $update = "UPDATE user SET user_password = '$newpass' WHERE user_email = '$email'";
                $res = mysqli_query($conn,$update);
                if($res){
                    echo "Password succesfully change";
                } else {
                    echo "Error!!!";
                }
            } else {
                echo "Wrong current password";
            }
        } else {
            echo "Password missmatch";
        }
    } else {
        echo "Wrong current email";
    }
    
?>

