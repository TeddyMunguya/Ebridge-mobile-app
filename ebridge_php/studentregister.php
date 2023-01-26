<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['username'])) {

    $name = $_POST['name'];
    $phone = $_POST['phone'];
    $email = $_POST['mail'];
    $department=$_POST['department'];
    $username = $_POST['username'];
    $password = $_POST['password'];


   // $name = "rrr";
  //  $phone = "5495834593";
  //  $email = "r@gmail.com";
  //  $department="CSE";
  //  $username = "ra";
  //  $password ="1234";
 
    // include db connect class
    require_once 'db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    $con = $db->getConString();
 
    // mysql inserting a new row
    $result = mysqli_query($con, "INSERT INTO studentregister(name,mobile,email,department,username,password) VALUES('$name','$phone', '$email','$department','$username','$password')");
 

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Student successfully added.";
        
        echo json_encode($response);
 
  }
  else
  {
    $response["success"] = 0;
    $response["message"] = "an error occured. Please try again.";

    echo json_encode(($response));
  }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
