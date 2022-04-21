<?php
require "DataBaseConfigg.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }
   
    function signUp($user, $user_name, $user_address, $user_phone, $user_email, $user_password, $user_role)
    {
        $user_name = $this->prepareData($user_name);
        $user_address = $this->prepareData($user_address);
        $user_phone = $this->prepareData($user_phone);
        $user_email = $this->prepareData($user_email);
        $user_password = $this->prepareData($user_password);
        $user_role = $this->prepareData($user_role);

        $user_password = password_hash($user_password, PASSWORD_BCRYPT);
        
        $dbc = mysqli_connect('localhost', 'root', '', 'wihngeheng_mobile') or die('Error connecting to MySQL server');
        $check=mysqli_query($dbc,"select * from user where user_email='$user_email'");
        $checkrows=mysqli_num_rows($check);

        if($checkrows>0) {
            echo "User already registered, ";
         } else {  
          //insert results from the form input
          $this->sql =
          "INSERT INTO " . $user . " (user_name, user_address, user_phone, user_email, user_password, user_role) VALUES ('" . $user_name . "','" . $user_address . "','" . $user_phone . "','" . $user_email . "','" . $user_password . "','" . $user_role . "')";
         if (mysqli_query($this->connect, $this->sql)) {
          return true;
        } else return false;
        }
    }

}

?>
