<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");

$fetchProductsCmd = $connection->prepare("select * from electronic_products where brand=?");
$fetchProductsCmd->bind_param("s", $_GET["brand"]);
$fetchProductsCmd->execute();

$productsResult = $fetchProductsCmd->get_result();
$products = array();

while($row = $productsResult->fetch_assoc()) {
    array_push($products, $row);
}

echo json_encode($products);

$connection->close();