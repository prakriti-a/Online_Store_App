<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");

$insertTempOrder = $connection->prepare("insert into temporary_order values (?, ?, ?)");
$insertTempOrder->bind_param("sii", $_GET["email"], $_GET["product_id"], $_GET["quantity"]);
$insertTempOrder->execute();

$connection->close();