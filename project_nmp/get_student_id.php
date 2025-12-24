<?php
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Headers: *");

    $conn = new mysqli("localhost","root","","project_nmp");

    $nrp = $_GET['nrp'] ?? '';

    $stmt = $conn->prepare("
        SELECT * FROM student
        WHERE nrp = ?
    ");
    $stmt->bind_param("s", $nrp);
    $stmt->execute();

    $result = $stmt->get_result();
    $data = [];

    while($row = $result->fetch_assoc()){
        $data[] = $row;
    }

    echo json_encode([
        "result" => "SUCCESS",
        "data" => $data
    ]);
?>