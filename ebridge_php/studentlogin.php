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
 
 //$username = "ra";
 
   // include db connect class
    require_once 'db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    $con = $db->getConString();

      // echoing JSON response
		 $result = mysqli_query($con, "SELECT * FROM studentregister WHERE username='$username'") or die(mysqli_error($con));
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["Login"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["name"] = $row["name"];
        $product["mail"] = $row["email"];
        $product["phone"] = $row["mobile"];
        $product["department"] = $row["department"];
        $product["username"] = $row["username"];
        $product["password"] = $row["password"];


        // push single product into final response array
        array_push($response["Login"], $product);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
}
		
    } else {
        // failed to insert row
        $response["success"] = 0;
       $response["Login"] = "Invalid username or password!";
 
        // echoing JSON response
        echo json_encode($response);
    }
    