<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");

// check if user with email already exists, in which case, multiple accounts are not allowed
$emailCheckCmd = $connection->prepare("select * from app_users_table where email=?");
// get passed value and bind it to param
$emailCheckCmd->bind_param("s",$_GET["email"]);
$emailCheckCmd->execute();
$emailResult = $emailCheckCmd->get_result();

if($emailResult->num_rows == 0) { 
    // user DNE
    $sqlCommand = $connection->prepare("insert into app_users_table values (?, ?, ?)");
    $sqlCommand->bind_param("sss",$_GET["email"],$_GET["username"],$_GET["password"]); 
    $sqlCommand->execute();
    echo 'User has signed up successfully!';
}
 else {
    echo 'A user with this email address already exists!';
}

$connection->close();