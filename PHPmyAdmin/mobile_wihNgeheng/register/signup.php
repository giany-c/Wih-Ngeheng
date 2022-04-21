<?php
require "DataBasee.php";

$db = new DataBase();
if (isset($_POST['user_name']) && isset($_POST['user_address']) && isset($_POST['user_phone']) && isset($_POST['user_email']) && isset($_POST['user_password']) && isset($_POST['user_role'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("user", $_POST['user_name'], $_POST['user_address'], $_POST['user_phone'], $_POST['user_email'], $_POST['user_password'], $_POST['user_role'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
