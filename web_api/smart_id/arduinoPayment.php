<?php
	$payment_id;
  try{
        $conn=new PDO("mysql:host=localhost;dbname=smart_card;",'root','');
        echo "<script>console.log('connection successful');</script>";
        
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
    catch(PDOException $e){
  		echo $e;
    }


if ($_SERVER['REQUEST_METHOD'] == 'GET') {
	# code...

	$r_tag = $_GET['r_tag']; //reader sends the rfid tag number
	$vendor_id = $_GET['vendor_id']; //reader sends where the reader is situated as 

	echo "r_tag: ".$r_tag." vendor id is: ".$vendor_id.'<br>';

	try{
	$qry= "SELECT MAX(payment_id) FROM payment_state WHERE vendor_id='$vendor_id' ";
	$pidObj = $conn->query($qry);
	$pidTab = $pidObj->fetchAll();
		foreach ($pidTab as $key) {

			$payment_id = $key[0];
			
			break;
		}

	}/* outer try block ends here*/
catch(PDOException $e){
       
                }/*catch ends here*/

                echo "max payment_id is: ".$payment_id.'<br>';
                echo "vendor_id is: ".$vendor_id.'<br>';




        try{
        	$up_sql = "UPDATE payment_state SET r_tag='$r_tag', payment_state='complete' WHERE payment_id='$payment_id' "  ;
				$up_obj = $conn->prepare($up_sql)->execute();

				} //bairer try

				catch(PDOException $ex){
					echo " update failed";
            
        } //bairer catch



}
?>