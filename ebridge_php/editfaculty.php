  <?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['username'])) {

 $username = $_POST['username'];
 $name = $_POST['name'];
 $mobile= $_POST['mobile'];
 $email= $_POST['mail'];
 $depart= $_POST['depart'];


 //$username = "ted";
 //$name ="ted";
 //$mobile= "999999999";
// $email= "ted@gmail.com";
// $depart= "IT";
 
 //$username = "ra";
 
   // include db connect class
    require_once 'db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    $con = $db->getConString();

      // echoing JSON response
		 $result = mysqli_query($con, "UPDATE facultyregister SET name='$name',mobile='$mobile',email='$email',department='$depart' WHERE username='$username'") or die(mysqli_error($con));
 
// check for empty result

		
   } else {
        // failed to insert row
        $response["success"] = 0;
       $response["facultyedit"] = "Invalid username!";
 
        // echoing JSON response
        echo json_encode($response);
    }
    