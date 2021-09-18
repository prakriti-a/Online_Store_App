<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");

$selectCommand = $connection->prepare("select price, quantity from electronic_products inner join invoice_details on "
        . "electronic_products.id = invoice_details.product_id where invoice_details.invoice_num=?");
$selectCommand->bind_param("i", $_GET["invoice_num"]);
$selectCommand->execute();

$result = $selectCommand->get_result();
$totalPrice = 0;

while($row = $result->fetch_assoc()) {
    $totalPrice = $totalPrice + ($row["price"] * $row["quantity"]);
}

echo $totalPrice;

$connection ->close();