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
 
 //$username = "hello";
 
   // include db connect class
    require_once 'db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    $con = $db->getConString();

      // echoing JSON response
		 $result = mysqli_query($con, "DELETE  FROM facultyregister WHERE username='$username'") or die(mysqli_error($con));
 
    // success
    $response["success"] = 1;
 
   
}
		 
   ?>