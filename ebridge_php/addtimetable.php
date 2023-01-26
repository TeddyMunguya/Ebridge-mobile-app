<?php

//connect to the database
//mysql_connect ("","gavsham_blood","blood@123") or die ('Cannot connect to MySQL: ' . mysql_error());
//mysql_select_db ("gavsham_blood") or die ('Cannot connect to the database: ' . mysql_error());


// check for required fields

if (isset($_POST['subject']) && isset($_POST['description']) && isset($_POST['image'])) {

  $subject = $_POST['subject'];
  $description = $_POST['description'];


  $base = $_POST['image'];

  $buffer = base64_decode($base);

  // include db connect class
  require_once 'db_connect.php';

  // connecting to db
  $db = new DB_CONNECT();

  $con = $db->getConString();

  $escapedString = mysqli_real_escape_string($con, $buffer);

  //$name="some";
  //$file="C:\Users\Innoapps4\Downloads/spinn.png";


  //query
  $result = mysqli_query($con, "INSERT INTO `timetable` (subject,description,image) VALUES('$subject','$description','$escapedString')") or die('Query is invalid: ' . mysqli_error($con));

  // check for empty result
  if ($result) {
    $response["success"] = 1;
    $response["message"] = "Inserted successfully";

    // echoing JSON response
    echo json_encode($response);
  } else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "Invalid details";

    // echo no users JSON
    echo json_encode($response);
  }
} else {
  $response["success"] = 0;
  $response["message"] = "Provide all required information";

  echo json_encode($response);
}
