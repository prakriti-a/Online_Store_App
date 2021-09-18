-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 18, 2021 at 07:47 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `online_store_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `app_users_table`
--

CREATE TABLE `app_users_table` (
  `email` varchar(40) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `app_users_table`
--

INSERT INTO `app_users_table` (`email`, `username`, `password`) VALUES
('jake@gmail.com', 'jake', 'jake'),
('terry@gmail.com', 'terry', 'terry'),
('amy@gmail.com', 'amy', 'amy');

-- --------------------------------------------------------

--
-- Table structure for table `electronic_products`
--

CREATE TABLE `electronic_products` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  `brand` varchar(30) NOT NULL,
  `image` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `electronic_products`
--

INSERT INTO `electronic_products` (`id`, `name`, `price`, `brand`, `image`) VALUES
(1, 'iPhone', 30000, 'Apple', 'iphone.png'),
(2, 'iMac', 22000, 'Apple', 'imac.png'),
(3, 'iPad', 34500, 'Apple', 'ipad.png'),
(4, 'iPod Nano', 18900, 'Apple', 'ipodnano.png'),
(5, 'iPod Shuffle', 18000, 'Apple', 'ipodshuffle.png'),
(6, 'iPod Touch', 20500, 'Apple', 'ipodtouch.png'),
(7, 'MacBook Air', 32000, 'Apple', 'macbookair.png'),
(8, 'MacBook Pro', 34500, 'Apple', 'macbookpro.png'),
(9, 'Mac Pro', 29500, 'Apple', 'macpro.png'),
(10, 'PS4', 28000, 'Sony', 'ps4.png'),
(11, 'PS4 Pro', 31500, 'Sony', 'ps4pro.png'),
(12, 'XBox One S', 27500, 'Microsoft', 'xboxones.png'),
(13, 'XBox One X', 30250, 'Microsoft', 'xboxonex.png'),
(14, 'Apple TV', 34700, 'Apple', 'appletv.png'),
(15, 'Apple Watch', 23750, 'Apple', 'applewatch.png');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `invoice_num` int(15) NOT NULL,
  `invoice_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `email` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoice_num`, `invoice_time`, `email`) VALUES
(1, '2021-05-23 18:29:59', 'jake@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_details`
--

CREATE TABLE `invoice_details` (
  `invoice_num` int(15) NOT NULL,
  `product_id` int(20) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invoice_details`
--

INSERT INTO `invoice_details` (`invoice_num`, `product_id`, `quantity`) VALUES
(1, 5, 3);

-- --------------------------------------------------------

--
-- Table structure for table `temporary_order`
--

CREATE TABLE `temporary_order` (
  `email` varchar(40) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`invoice_num`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `invoice_num` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
