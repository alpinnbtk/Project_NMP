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

    $sql = "DELETE FROM my_friends";
    $stmt = $c->prepare($sql);

    if ($stmt->execute()) {
        echo json_encode([
            "result" => "SUCCESS",
            "message" => "Semua data my_friends berhasil dihapus!"
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
