-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2023 at 12:09 AM
-- Server version: 5.6.15-log
-- PHP Version: 5.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "SET GLOBAL time_zone = "+13:00";


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
  `Teacher_CIN` varchar(50) NOT NULL,
  `Module_ID` int(50) NOT NULL,
  `Status` varchar(55) NOT NULL,
  PRIMARY KEY (`Attendance_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`Attendance_ID`, `Student_ID`, `Teacher_CIN`, `Module_ID`, `Status`) VALUES
(2, 3, 'KB123456', 77, 'Present'),
(33, 22, 'KB660982', 7, 'absent'),
(4, 33, 'KB761923', 55, 'Present');

-- --------------------------------------------------------

--
-- Table structure for table `field`
--

CREATE TABLE IF NOT EXISTS `field` (
  `Field_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Name` varchar(99) NOT NULL,
  PRIMARY KEY (`Field_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `field`
--

INSERT INTO `field` (`Field_ID`, `Name`) VALUES
(1, 'Dev Info'),
(2, 'Dev Info'),
(4, 'Ges Ent'),
(6, 'DQ');

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE IF NOT EXISTS `module` (
  `Module_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Name` varchar(99) NOT NULL,
  `Field_ID` int(99) NOT NULL,
  `Description` varchar(1000) NOT NULL,
  PRIMARY KEY (`Module_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`Module_ID`, `Name`, `Field_ID`, `Description`) VALUES
(3, 'SQL', 1111, 'Database'),
(4, 'CSS', 2, ''),
(6, 'C#', 66, ''),
(10, 'HTML', 33, ''),
(8, 'Java', 11, 'Client Server'),
(9, 'PHP', 44, '');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `Student_ID` int(99) NOT NULL AUTO_INCREMENT,
  `Full_Name` varchar(99) NOT NULL,
  `Field_ID` int(55) NOT NULL,
  `Email` varchar(99) NOT NULL,
  `Phone_Number` varchar(99) NOT NULL,
  PRIMARY KEY (`Student_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`Student_ID`, `Full_Name`, `Field_ID`, `Email`, `Phone_Number`) VALUES
(13, 'Samira kara', 11, 'samira90@hotmail.com', '0677404510'),
(12, 'soukaina arkob', 22, 'soukaina22@gmail.com', '0693612180'),
(14, 'Mostafa Ark', 99, 'Mostafa55@gmail.com', '02147483647'),
(15, 'ManarKA', 55, 'ManarKA@gmail.com', '0214748355'),
(16, 'Younes Ark', 55, 'Younes55@gmail.com', '0699335683');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `Teacher_CIN` varchar(50) NOT NULL,
  `Full_Name` varchar(99) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone_Number` varchar(55) NOT NULL,
  PRIMARY KEY (`Teacher_CIN`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`Teacher_CIN`, `Full_Name`, `Email`, `Phone_Number`) VALUES
('KB456987', 'Salman Ark', 'Salman1@gmail.com', '0698345621'),
('kb678123', 'soukaina Ark', 'soukaina55@gmail.com', '0655229944'),
('kb556623', 'Younes Ark', 'Younes55@gmail.com', '0655221234');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
