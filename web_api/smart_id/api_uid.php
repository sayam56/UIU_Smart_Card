<?php
$t_name = "";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	# code...

	$r_tag = $_POST['r_tag'];


	$db_name = "smart_card";
	$user_name = "root";

	$conn = mysqli_connect("localhost",$user_name,"",$db_name );


	/*require_once 'db_conenct.php'*/

	$qry= "SELECT r_role FROM rfid WHERE r_tag='".$r_tag."' ";
	$res = mysqli_query($conn,$qry);

    while ($row = $res->fetch_assoc())
	{
    	foreach($row as $value) {
    		$result["role"] = $value;
    		$t_name .= $value;
    	}
	}


	if ($t_name == "Student") {
		$sql = $qry= "SELECT s_name, s_id, s_email, s_password FROM student WHERE s_tag='".$r_tag."' ";
		$ress = mysqli_query($conn,$qry) or die($conn->error);
	}
	else{
		$sql = $qry= "SELECT t_name, t_id, t_email, t_password FROM teacher WHERE t_tag='".$r_tag."' ";
		$ress = mysqli_query($conn,$qry) or die($conn->error);
	}


	$jsonVal="";

	while ($row = $ress->fetch_assoc())
	{
    	foreach($row as $value) {
    		
    		$jsonVal = $jsonVal.$value." "."+";
    	}
	}

	$pieces = explode("+", $jsonVal);

	$result["name"] = $pieces[0];
	$result["id"] = $pieces[1];
	$result["email"] = $pieces[2];
	$result["pass"] = $pieces[3];	

	if ($ress) 
	{
		
		$result["success"] = "1";
		$result["message"] = "success";


		echo json_encode($result);

		mysqli_close($conn);
		
	}
	else
	{
		$result["success"] = "0";
		$result["message"] = "failed";

		echo json_encode($result);

		mysqli_close($conn);

	}
}

?>