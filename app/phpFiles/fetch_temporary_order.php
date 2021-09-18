<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

$conn = new mysqli("localhost", "root", "", "online_store_db");

$cmd = "SELECT id,name,price,email,quantity FROM temporary_order INNER JOIN electronic_products ON electronic_products.id=temporary_order.product_id WHERE email=?";

if($fetchTempOrder = $conn->prepare($cmd)) {

$fetchTempOrder->bind_param("s",$_GET["email"]);
$fetchTempOrder->execute();

$ordersResult = $fetchTempOrder->get_result();
$orders = array();

while($row = $ordersResult->fetch_assoc()) {
    array_push($orders, $row);
}
echo json_encode($orders);
}

else {
    $error = $conn->errno . ' ' . $conn->error;
    echo $error;  
}

$conn->close();