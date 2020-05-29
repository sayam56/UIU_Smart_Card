<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

		$conn=new PDO("mysql:host=localhost;dbname=smart_card;",'root','');
		echo "<script>console.log('connection successful');</script>";

	$r_tag = $_POST['r_tag'];

	$sql= "SELECT r_role FROM rfid WHERE r_tag='".$r_tag."' ";

	$object=$conn->query($sql);

    $role = $object->fetchAll();

    


	if ($object->rowCount() == 0 ) {
		
		$result["success"] = "0";
		$result["message"] = "failed";

		echo json_encode($result);
		
	}else{
		

		/*foreach ($role as $key) 
		{
    		$result["role"] =  $key[0];
		}*/

		$result["success"] = "1";
		$result["message"] = "success";


		echo json_encode($result);

	}
}

?>