<?php
    header("Access-Control-Allow-Origin: *");
	
    try{
        $c = new mysqli("localhost", "root", "", "project_nmp");
    } catch(Exception $e) {
        $info = array(
            "result"  => "Error",
            "message" => $e->getMessage()
        );
        echo json_encode($info);
        die();
    }

    $c->set_charset("UTF8");
	$sql = "SELECT * FROM student s inner join my_friends mf on s.nrp=mf.nrp ";
    $stmt = $c->prepare($sql);
    $stmt->execute();
    $result =$stmt->get_result();
        $array = array();
    if ($result->num_rows > 0) {
            while ($row = $result -> fetch_assoc()) {
                $array[] = $row;
            }
            echo json_encode(array('result' => 'SUCCESS', 'data' => $array));
        } else {
            echo json_encode(array('result'=> 'ERROR', 'message' => 'No data found'));
            die();
        }
?>