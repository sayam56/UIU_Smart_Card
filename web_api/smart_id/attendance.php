<?php
$t_name = "";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	# code...

	$sec_name = $_POST['sec_name'];
	$s_id = $_POST['s_id'];

	$codeQuery='';
	$jsonVal="";
	$count = 0;
	$present=0;
	$absent=0;

	$date = date("Y-m-d");
	

/*	$testarr = array();*/

try{
    $conn=new PDO("mysql:host=localhost;dbname=smart_card;",'root','');
    
    
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}
catch(PDOException $e){
    
}

try{
	$qry= "SELECT date FROM `classdate` WHERE sec_name='".$sec_name."' ";
	$dateObj = $conn->query($qry);
	$dateTab = $dateObj->fetchAll();
	$temp=0;
	

		foreach ($dateTab as $key) {

			$sql = "SELECT date FROM attendance WHERE sec_name='".$sec_name."' AND s_id='".$s_id."' GROUP BY date ";
			$c_nameObj = $conn->query($sql);
			$nameTab = $c_nameObj->fetchAll();

			foreach ($nameTab as $k) {


				if ($key[0] == $k[0]) {
					$present++;
					$jsonVal = $jsonVal.$k[0].""."+"; 


				}

			}

			
			if ($key[0] <= $date) {
				//echo $key[0]."<br>";
				$temp++;
			}
				
				/*array_push($testarr, $temp);*/
		}

		$absent = $temp-$present;
 

	}/* outer try block ends here*/
catch(PDOException $e){
       
                }/*catch ends here*/


	$pieces = explode("+", $jsonVal);

	/*echo "<br>"; print_r($pieces); echo "<br>";
	echo count($pieces);*/
	/*echo '<pre>'; print_r($pieces); echo '</pre>';*/


	for ($i=0; $i < count($pieces)-1 ; $i++) { 

		$result[$count] = [
			
			"date" => $pieces[$i],

			"present" => $present,
			"absent" => $absent

		];

		$count++;

	}

	if (empty($jsonVal)) 
	{
		$result["success"] = "0";
		$result["message"] = "failed";

		echo json_encode($result);


		
	}
	else
	{


		echo json_encode($result);

	}
}

?>