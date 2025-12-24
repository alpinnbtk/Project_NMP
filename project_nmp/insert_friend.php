<?php
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Headers: *");
    header("Content-Type: application/json");

    $c = new mysqli("localhost", "root", "", "project_nmp");
    $c->set_charset("UTF8");

    $nrp = $_GET['nrp'] ?? '';

    if ($nrp == '') {
        echo json_encode([
            "result" => "ERROR",
            "message" => "NRP tidak boleh kosong"
        ]);
        exit();
    }

    $check = $c->prepare("SELECT COUNT(*) FROM my_friends WHERE nrp = ?");
    $check->bind_param("s", $nrp);
    $check->execute();
    $check->bind_result($exists);
    $check->fetch();
    $check->close();

    if ($exists > 0) {
        $count = $c->query("SELECT COUNT(*) AS total FROM my_friends");
        $row = $count->fetch_assoc();

        echo json_encode([
            "result" => "SUCCESS",
            "already_friend" => true,
            "total_friend" => (int)$row['total']
        ]);
        exit();
    }

    $insert = $c->prepare("INSERT INTO my_friends (nrp) VALUES (?)");
    $insert->bind_param("s", $nrp);
    $insert->execute();
    $insert->close();

    $count = $c->query("SELECT COUNT(*) AS total FROM my_friends");
    $row = $count->fetch_assoc();

    echo json_encode([
        "result" => "SUCCESS",
        "already_friend" => false,
        "total_friend" => (int)$row['total']
    ]);

    $c->close();
?>