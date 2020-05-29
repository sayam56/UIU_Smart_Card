<?php
$t_name = "";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	# code...

	$s_id = $_POST['s_id'];
	$codeQuery='';
	$jsonVal="";
	$count = 0;

/*	$testarr = array();*/

try{
    $conn=new PDO("mysql:host=localhost;dbname=smart_card;",'root','');
    
    
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}
catch(PDOException $e){
    
}

try{
	$qry= "SELECT c_code FROM studentjcourse WHERE s_id='".$s_id."' ";
	$c_codeObj = $conn->query($qry);
	$codeTab = $c_codeObj->fetchAll();

	

		foreach ($codeTab as $key) {

			/*$temp = array();*/
			$jsonVal = $jsonVal.$key[0].""."+"; //ekhane c_code gelo

			/*$temp['c_code'] = $key[0];*/

			$sql = "SELECT c_name FROM course WHERE c_code='".$key[0]."' ";
			$c_nameObj = $conn->query($sql);
			$nameTab = $c_nameObj->fetchAll();

			foreach ($nameTab as $k) {

				$jsonVal = $jsonVal.$k[0].""."+"; //ekhane c_name gelo
				/*$temp['c_name'] = $k[0];*/
			}

			$sqll = "SELECT sec_name FROM studentjsection WHERE c_code='".$key[0]."' AND s_id='".$s_id."' ";
			$sec_nameObj = $conn->query($sqll);
			$secTab = $sec_nameObj->fetchAll();

			foreach ($secTab as $kk) {

				$jsonVal = $jsonVal.$kk[0].""."+"; //ekhane section gelo
				/*$temp['section'] = $kk[0];*/
			}

				/*array_push($testarr, $temp);*/
		}



	}/* outer try block ends here*/
catch(PDOException $e){
       
                }/*catch ends here*/


	

	

	$pieces = explode("+", $jsonVal);

	/*echo "<br>"; print_r($pieces); echo "<br>";
	echo count($pieces);*/
	/*echo '<pre>'; print_r($pieces); echo '</pre>';*/


	for ($i=0; $i < count($pieces)-1 ; $i=$i+3) { 

		$result[$count] = [
			/*"c_code" => $pieces[$i],*/
			"c_name" => $pieces[$i+1],
			"section" => $pieces[$i+2]
		];

		/*$result["c_code".$i] = $pieces[$i];
		$result["c_name".$i] = $pieces[$i+1];
		$result["section".$i] = $pieces[$i+2];
*/
		/*echo json_encode($result);*/

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