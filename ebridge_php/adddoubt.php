<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['subject'])) {
    // include db connect class
    require_once 'db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    $con = $db->getConString();

    $subject = $_POST['subject'];
    $description = $_POST['description'];
    $username = $_POST['username'];

    // $subject="2-1 java";
    //  $description="What is Java";
    // $file="C:\Users\Downloads/6.png";




    // mysql inserting a new row
    $result = mysqli_query($con, "INSERT INTO `doubts`(subject,description,username) VALUES('$subject','$description','$username')");


    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Doubt successfully created.";

        echo json_encode($response);
    } else {
        $response["success"] = 2;
        $response["message"] = "Could not add doubt. Please try again later";

        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
