<?php
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Headers: *");
    header("Content-Type: application/json");

    try {
        $c = new mysqli("localhost", "root", "", "project_nmp");
    } catch (Exception $e) {
        echo json_encode([
            "result" => "ERROR",
            "message" => $e->getMessage()
        ]);
        die();
    }

    $c->set_charset("UTF8");

    $nrp = $_POST['nrp'] ?? '';

    if ($nrp == '') {
        echo json_encode([
            "result" => "ERROR",
            "message" => "NRP tidak boleh kosong!"
        ]);
        die();
    }

    $sql = "INSERT INTO my_friends (nrp) VALUES (?)";
    $stmt = $c->prepare($sql);
    $stmt->bind_param("s", $nrp);

    if ($stmt->execute()) {
        echo json_encode([
            "result" => "SUCCESS",
            "message" => "Data berhasil ditambahkan!"
        ]);
    } else {
        echo json_encode([
            "result" => "ERROR",
            "message" => $stmt->error
        ]);
    }

    $stmt->close();
    $c->close();
?>
