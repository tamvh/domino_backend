-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 09, 2017 at 10:18 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `domino_monitor`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL,
  `account_name` varchar(64) NOT NULL,
  `password` varchar(64) DEFAULT '123456',
  `account_type` tinyint(3) NOT NULL DEFAULT '0',
  `reset` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_id`, `account_name`, `password`, `account_type`, `reset`, `status`) VALUES
(1, 'admin', '*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9', 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tb_email_list`
--

CREATE TABLE `tb_email_list` (
  `id` int(11) NOT NULL,
  `email` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL DEFAULT '',
  `phone` varchar(32) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_email_list`
--

INSERT INTO `tb_email_list` (`id`, `email`, `name`, `phone`) VALUES
(1, 'tamvh@vng.com.vn', 'Võ Huy Tâm', '0935954858');

-- --------------------------------------------------------

--
-- Table structure for table `tb_email_log`
--

CREATE TABLE `tb_email_log` (
  `id` int(11) NOT NULL,
  `email` varchar(64) NOT NULL,
  `subject` text NOT NULL,
  `content` text NOT NULL,
  `time_send_email` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `account_name_app_id` (`account_name`);

--
-- Indexes for table `tb_email_list`
--
ALTER TABLE `tb_email_list`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_email_log`
--
ALTER TABLE `tb_email_log`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tb_email_list`
--
ALTER TABLE `tb_email_list`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `tb_email_log`
--
ALTER TABLE `tb_email_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
