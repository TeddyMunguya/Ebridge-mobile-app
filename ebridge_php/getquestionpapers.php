<?php

//connect to the database
//mysql_connect ("localhost","gavsham_blood","blood@123") or die ('Cannot connect to MySQL: ' . mysql_error());
//mysql_select_db ("gavsham_blood") or die ('Cannot connect to the database: ' . mysql_error());


// check for required fields

// include db connect class
    require_once 'db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    $con = $db->getConString();
    
//query
$result = mysqli_query($con, "SELECT * FROM `questions` ") or die ('Query is invalid: ' . mysqli_error($con));

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["questiondetails"] = array();
 //$row = mysql_fetch_array($result);
   while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $product = array();

        $product["subject"]=$row["subject"];
        $product["description"]=$row["description"];
        $product["image"] =base64_encode( $row["image"]);
        

        //  $imagedata=$row["file"];
       
        // push single product into final response array
        array_push($response["questiondetails"], $product);

   };
     
   
   
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["questiondetails"] = "Invalid details";
 
    // echo no users JSON
    echo json_encode($response);
}
