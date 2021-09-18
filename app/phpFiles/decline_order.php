<?php

$conn = new mysqli("localhost", "root", "", "online_store_db");

if($deleteTempOrder = $conn->prepare("delete from temporary_order where email=?")) {
$deleteTempOrder->bind_param("s",$_GET["email"]);
$deleteTempOrder->execute();
}

else {
    $error = $conn->errno . ' ' . $conn->error;
    echo $error;
}

$conn->close();