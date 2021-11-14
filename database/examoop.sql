-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2021 at 05:51 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `examoop`
--

-- --------------------------------------------------------

--
-- Table structure for table `icecream`
--

CREATE TABLE `icecream` (
  `ID` char(5) NOT NULL,
  `BuyerName` varchar(200) NOT NULL,
  `IceCreamType` varchar(50) NOT NULL,
  `Flavor` varchar(20) NOT NULL,
  `Topping` varchar(100) NOT NULL,
  `Qty` int(11) NOT NULL,
  `TotalPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `icecream`
--

INSERT INTO `icecream` (`ID`, `BuyerName`, `IceCreamType`, `Flavor`, `Topping`, `Qty`, `TotalPrice`) VALUES
('FY001', 'Hesti', 'Frozen Yogurt', 'Strawberry', 'Oreo', 2, 30000),
('FY002', 'Owen', 'Frozen Yogurt', 'Kiwi', 'Almond, Oreo', 4, 82000),
('GL001', 'Winda', 'Gelato', 'Hazelnut', 'Nata De Coco, Oreo', 2, 38000),
('GL002', 'Pasto', 'Gelato', 'Dark Chocolate', 'Nata De Coco, Oreo, Almond', 1, 24500),
('GL004', 'Winda', 'Gelato', 'Hazelnut', 'Nata de Coco,Oreo', 1, 38000),
('PO001', 'Januar', 'Popsicle', 'Vanilla', '-', 5, 35000),
('PO002', 'Yuni', 'Popsicle', 'Cookies', '-', 3, 21000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `icecream`
--
ALTER TABLE `icecream`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
