-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 03, 2023 at 05:20 PM
-- Server version: 5.6.15-log
-- PHP Version: 5.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sattendance`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE IF NOT EXISTS `attendance` (
  `Attendance_ID` int(55) NOT NULL,
  `Student_ID` int(55) NOT NULL,
  `Teacher_ID` varchar(50) NOT NULL,
  `Semester` varchar(50) NOT NULL,
  `Module` varchar(50) NOT NULL,
  `Status` int(55) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`Attendance_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `field`
--

CREATE TABLE IF NOT EXISTS `field` (
  `Field_ID` int(99) NOT NULL,
  `Name` varchar(99) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE IF NOT EXISTS `module` (
  `Module_ID` int(99) NOT NULL,
  `Name` varchar(99) NOT NULL,
  `Teacher_ID` int(99) NOT NULL,
  `Field_ID` int(99) NOT NULL,
  `Semester` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `Student_ID` int(99) NOT NULL,
  `Full_Name` varchar(99) NOT NULL,
  `Date_of_Birth` int(11) NOT NULL,
  `Field_ID` int(55) NOT NULL,
  `Email` varchar(99) NOT NULL,
  `Phone_Number` int(99) NOT NULL,
  PRIMARY KEY (`Student_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `Teacher_ID` int(99) NOT NULL,
  `Full_Name` varchar(99) NOT NULL,
  `E-mail` int(99) NOT NULL,
  `Module` int(99) NOT NULL,
  `Status` int(99) NOT NULL,
  PRIMARY KEY (`Teacher_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
