-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2017 at 07:05 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ecommdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `AdminId` int(11) NOT NULL AUTO_INCREMENT,
  `AdminName` varchar(45) NOT NULL,
  `AdminEmail` varchar(45) NOT NULL,
  `AdminPhone` varchar(45) NOT NULL,
  `AdminPassword` varchar(45) NOT NULL,
  PRIMARY KEY (`AdminId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`AdminId`, `AdminName`, `AdminEmail`, `AdminPhone`, `AdminPassword`) VALUES
(1, 'rishikesh', 'rishikesh@gmail.com', '987654323', 'Password1');

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE IF NOT EXISTS `card` (
  `cid` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `card_type` varchar(255) NOT NULL,
  `cart_number` varchar(255) NOT NULL,
  `card_cvv` int(5) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE IF NOT EXISTS `cart` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pid` int(10) NOT NULL,
  `uid` int(10) NOT NULL,
  `quantity` int(10) NOT NULL,
  `productName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `categoryName` varchar(45) NOT NULL,
  `categoryDetails` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`categoryName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`categoryName`, `categoryDetails`) VALUES
('Bangles', 'Good bangles'),
('Necklace', 'Good necklaces'),
('Pendent', 'Pendent Category'),
('Ring', 'Awesome rings');

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

CREATE TABLE IF NOT EXISTS `complaint` (
  `complaintId` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` varchar(45) NOT NULL,
  `complaintDetail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`complaintId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(45) NOT NULL,
  `customerPhone` int(11) DEFAULT NULL,
  `customerEmail` varchar(45) NOT NULL,
  `customerAddress` varchar(45) NOT NULL,
  `customerState` varchar(45) DEFAULT NULL,
  `customerCity` varchar(45) NOT NULL,
  `customerZip` int(11) NOT NULL,
  `customerPassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  UNIQUE KEY `customerEmail_UNIQUE` (`customerEmail`),
  UNIQUE KEY `customerPhone_UNIQUE` (`customerPhone`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerId`, `customerName`, `customerPhone`, `customerEmail`, `customerAddress`, `customerState`, `customerCity`, `customerZip`, `customerPassword`) VALUES
(1, 'Rishi', 2147483647, 'techierishi@gmail.com', 'unm', 'wb', 'kol', 576876, 'password'),
(2, 'Preethi Kumari', 12345588, 'preethi@gmail.com', '1st Main Road ', 'Tamilnadu', 'Hosur', 34589, 'password');

-- --------------------------------------------------------

--
-- Table structure for table `line_item`
--

CREATE TABLE IF NOT EXISTS `line_item` (
  `lid` int(10) NOT NULL AUTO_INCREMENT,
  `oid` int(10) NOT NULL,
  `pid` int(10) NOT NULL,
  `qty` int(10) NOT NULL,
  `productName` varchar(255) NOT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `line_item`
--

INSERT INTO `line_item` (`lid`, `oid`, `pid`, `qty`, `productName`) VALUES
(1, 1, 9, 2, ''),
(2, 2, 9, 2, ''),
(3, 3, 5, 1, ''),
(4, 3, 6, 2, ''),
(5, 3, 8, 2, ''),
(6, 4, 5, 2, '');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE IF NOT EXISTS `order` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `paymentId` int(11) NOT NULL,
  `customerId` int(11) NOT NULL,
  `orderStatus` varchar(45) NOT NULL,
  `paymentType` varchar(255) NOT NULL,
  `shipmentId` int(11) NOT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`orderId`, `paymentId`, `customerId`, `orderStatus`, `paymentType`, `shipmentId`) VALUES
(1, 0, 2, 'placed', 'cod', 0),
(2, 0, 2, 'placed', 'cod', 0),
(3, 0, 2, 'placed', 'cod', 0),
(4, 0, 2, 'placed', 'cod', 0);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE IF NOT EXISTS `payment` (
  `paymentId` int(11) NOT NULL AUTO_INCREMENT,
  `paymentDetail` varchar(45) NOT NULL,
  `customerId` int(11) NOT NULL,
  `paymentMode` varchar(45) NOT NULL,
  `cardType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`paymentId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `productId` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(45) NOT NULL,
  `categoryName` varchar(45) NOT NULL,
  `productAvailability` varchar(45) DEFAULT NULL,
  `productQuantity` int(11) DEFAULT '0',
  `productPrice` int(11) DEFAULT NULL,
  `supplierId` int(11) DEFAULT NULL,
  `productDetails` text NOT NULL,
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productId`, `productName`, `categoryName`, `productAvailability`, `productQuantity`, `productPrice`, `supplierId`, `productDetails`) VALUES
(5, 'Rani Har', 'Necklace', 'Available', 40, 45000, 0, 'null'),
(6, 'China Har', 'Necklace', 'Available', 30, 60000, 0, 'null'),
(7, 'Rani Har2', 'Necklace', 'Available', 40, 45000, 0, 'null'),
(8, 'Kar Har', 'Necklace', 'Available', 40, 560000, 0, 'null'),
(9, 'One Bangle', 'Bangles', 'Available', 40, 40000, 0, 'null');

-- --------------------------------------------------------

--
-- Table structure for table `product_image`
--

CREATE TABLE IF NOT EXISTS `product_image` (
  `imageId` int(11) NOT NULL AUTO_INCREMENT,
  `pId` int(11) NOT NULL,
  `imageName` varchar(255) NOT NULL,
  PRIMARY KEY (`imageId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `product_image`
--

INSERT INTO `product_image` (`imageId`, `pId`, `imageName`) VALUES
(7, 5, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(8, 5, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(9, 6, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(10, 6, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(11, 7, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(12, 7, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(13, 7, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(14, 8, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(15, 8, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(16, 9, 'a02961a1-4ed6-4f3b-a6b9-aa4688284b82designer-polki-bangl-designer-polki-bangles-250x250.jpg'),
(17, 9, '1288c651-0a05-42ba-8297-59d13149125732018BGLDPV1800_small.jpg'),
(18, 9, '76d3e3e8-84e0-448b-9c52-a29f35bae499d_1431610734.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `shipment`
--

CREATE TABLE IF NOT EXISTS `shipment` (
  `shipmentId` int(11) NOT NULL AUTO_INCREMENT,
  `shipmentAddress` varchar(45) NOT NULL,
  `shipmentType` varchar(45) NOT NULL,
  `shippedDate` datetime NOT NULL,
  PRIMARY KEY (`shipmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `supplierId` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(45) NOT NULL,
  `supplierAddress` varchar(45) NOT NULL,
  `supplierPhone` int(11) NOT NULL,
  `supplierEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
