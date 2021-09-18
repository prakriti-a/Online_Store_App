<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");

$selectBrandsCmd = $connection->prepare("select distinct brand from electronic_products"); // no repetitions
$selectBrandsCmd->execute();

$brandsResult = $selectBrandsCmd->get_result();
$brands = array();

while($row = $brandsResult->fetch_assoc()) {
    array_push($brands, $row);
}

echo json_encode($brands);

$connection->close();