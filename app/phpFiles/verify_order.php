<?php

$conn = new mysqli("localhost", "root", "", "online_store_db");

$getTempOrdersCommand = $conn->prepare("select * from temporary_order where email=?");
$getTempOrdersCommand->bind_param("s", $_GET["email"]);
$getTempOrdersCommand->execute();
$tempOrdersResult = $getTempOrdersCommand->get_result();

$insertInvoiceCommand = $conn->prepare("insert into invoice(email) values(?)"); // insert with email value
$insertInvoiceCommand->bind_param("s", $_GET["email"]);
$insertInvoiceCommand->execute();

$getLatestInvoiceNumCmd = $conn->prepare("select max(invoice_num) as latest_invoice_num from invoice where email=?"); 
        // max means latest invoice_num value -> auto incremented
$getLatestInvoiceNumCmd->bind_param("s", $_GET["email"]);
$getLatestInvoiceNumCmd->execute();
$InvoiceNumResult = $getLatestInvoiceNumCmd->get_result();
$row_invoiceNum = $InvoiceNumResult->fetch_assoc();


while ($row = $tempOrdersResult->fetch_assoc()) { // runs for each row in temp_order for the email
    $insertInvDetailsCmd = $conn->prepare("insert into invoice_details values (?,?,?)");
    $insertInvDetailsCmd->bind_param("iii", $row_invoiceNum["latest_invoice_num"], $row["product_id"], $row["quantity"]);
    $insertInvDetailsCmd->execute();
    
    $deleteTempOrderCmd = $conn->prepare("delete from temporary_order where email=?");
    $deleteTempOrderCmd->bind_param("s", $_GET["email"]);
    $deleteTempOrderCmd->execute();  
}

echo $row_invoiceNum["latest_invoice_num"];

// new invoice is generated every time this file/url is run

$conn->close();