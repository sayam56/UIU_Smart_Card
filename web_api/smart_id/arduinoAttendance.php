<?php
$t_name = "";
//for demo purposes we are gonna use CSI321 A as the course so room id would be 375r
date_default_timezone_set('Asia/Dhaka'); 

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
	# code...

	$r_tag = $_GET['r_tag']; //reader sends the rfid tag number
	$sec_rfid_reader = $_GET['sec_rfid_reader']; //reader sends where the reader is situated as in the reader location so from here we know the room number of where the class is happening


	//from the current time we should be able to determine which class is in progress at the moment

	//select sec_name from section where sec_start_time< (current time) and sec_end_time> (current_time) and sec_rfid_reader= $section_rfid_reader

	//then search for the t_id where sec_name = sec_name

	//make sure if there is a class at that date
	//select * from classdate where sec_name = (sec_name) and date=(current date)
	//if rowCount >0 taile insert

	

	//insert into attendance values (sec_name,t_id,s_id,date,time)

 

	$codeQuery='';
	$jsonVal="";
	$s_id="";
	$sec_nameRes="";
	$t_id="";

	$date = date("Y-m-d"); //contains current date in the given format
	$current_time = date("h:i:s:ms"); //contains the current ttime to the ms

/*	$testarr = array();*/

try{
    $conn=new PDO("mysql:host=localhost;dbname=smart_card;",'root','');
    
    
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}
catch(PDOException $e){
    
}


echo $date."<br>";
echo $current_time."<br>";

try{
	$qry= "SELECT r_id FROM rfid WHERE r_tag='".$r_tag."' ";
	$ridObj = $conn->query($qry);
	$ridTab = $ridObj->fetchAll();
		foreach ($ridTab as $key) {

			$s_id = $key[0];			

			break;
		}

	}/* outer try block ends here*/
catch(PDOException $e){
       
                }/*catch ends here*/


echo "s_id is: ".$s_id."<br>";
	

	try{

		$sql = "SELECT sec_name, t_id from section WHERE sec_start_time< '".$current_time."' AND sec_end_time> '".$current_time."' AND sec_rfid_reader = '".$sec_rfid_reader."' ";
		$secObj =$conn->query($sql);
		$secTab = $secObj->fetchAll();

		foreach ($secTab as $k) {
			$sec_nameRes = $k[0];
			$t_id = $k[1];
			break;
		}


	}/*try ends here*/
	catch(PDOException $ex){

	}/*catch ends here*/

echo "sec_name is: ".$sec_nameRes."<br>";
echo "t_id is : ".$t_id."<br>";



 try {
     $check="SELECT state FROM attendance_state WHERE t_id='$t_id' AND sec_name='$sec_nameRes' and date='$date' ORDER BY attStateID DESC LIMIT 1; ";
     $checkObj= $conn->query($check);

     if ($checkObj->rowCount() == 0) {
     	echo "Attendance Not Initiated";
     }
     else{

     	$checkTable= $checkObj->fetchAll();
		 foreach ($checkTable as $key) {
	          
	           if ($key[0] == 'false') {
	          	echo "Attendance Closed";

	          }
	          else{
	          	echo 'attendance ready';

          	try{
				$classDate= "SELECT * FROM classdate WHERE sec_name = '".$sec_nameRes."' and date='".$date."' ";
				$cdObj = $conn->query($classDate);
				
				if ($cdObj->rowCount() == 0) {
					echo "class not in session";

				}else {
					echo "class in session";
					$insert = $conn->prepare("INSERT INTO attendance(`sec_name`, `t_id`, `s_id`, `date`) values('$sec_nameRes','$t_id','$s_id','$date')");
					try{
			                $insert->execute();
			                 echo "insertion success";
			                }
			                catch(PDOException $ex)
			                {
			                ?>
			                <script>
			                    window.alert("Database insertion error");
			                </script>
			            	<?php
			            	}
				}

				}/* outer try block ends here*/
			catch(PDOException $e){
			       
			                }/*catch ends here*/
	          } /*attendance readyy*/
	          		




	          }/*attendance initiated foreach*/
	          


     }/*rowcount else*/
     
	        

     
   } catch (PDOException $e) {
     echo $e;
   }/*check query catch*/








} /*get if*/

?>