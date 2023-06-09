-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2023 at 11:36 PM
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
  `Teacher_ID` int(50) NOT NULL,
  `Semester` varchar(50) NOT NULL,
  `Module_ID` int(50) NOT NULL,
  `Status` varchar(55) NOT NULL,
  `Date` varchar(55) NOT NULL,
  PRIMARY KEY (`Attendance_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`Attendance_ID`, `Student_ID`, `Teacher_ID`, `Semester`, `Module_ID`, `Status`, `Date`) VALUES
(2, 3, 5, 'ff', 77, 'ee', '34'),
(3, 8, 9, 'ss', 33, 'rr', '5');

-- --------------------------------------------------------

--
-- Table structure for table `field`
--

CREATE TABLE IF NOT EXISTS `field` (
  `Field_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Name` varchar(99) NOT NULL,
  PRIMARY KEY (`Field_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `field`
--

INSERT INTO `field` (`Field_ID`, `Name`) VALUES
(1, 'gg'),
(2, 'ffffffffffffff'),
(4, 'gg'),
(5, 'ee');

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE IF NOT EXISTS `module` (
  `Module_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Name` varchar(99) NOT NULL,
  `Field_ID` int(99) NOT NULL,
  PRIMARY KEY (`Module_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`Module_ID`, `Name`, `Field_ID`) VALUES
(3, 'hhhh', 1111),
(4, 'souka', 2),
(5, 'fou', 5),
(6, 'gg', 66),
(7, 'ff', 55);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `Student_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Full_Name` varchar(99) NOT NULL,
  `Field_ID` int(55) NOT NULL,
  `Email` varchar(99) NOT NULL,
  `Phone_Number` int(99) NOT NULL,
  PRIMARY KEY (`Student_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`Student_ID`, `Full_Name`, `Field_ID`, `Email`, `Phone_Number`) VALUES
(5, 'ttttt', 3333, 'bbbbbbbbbb', 22222222),
(6, 'jjjjj', 66666666, 'ggggggg', 33333333),
(10, 'ttttt', 4444, 'hhhhh', 999999);

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `Teacher_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Full_Name` varchar(99) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone_Number` varchar(55) NOT NULL,
  PRIMARY KEY (`Teacher_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`Teacher_ID`, `Full_Name`, `Email`, `Phone_Number`) VALUES
(2, 'gggjjjj', 'ttttt', '989898');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
