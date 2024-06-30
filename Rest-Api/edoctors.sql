-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 10, 2022 at 05:45 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edoctors`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `patient_id` varchar(255) NOT NULL,
  `doctor_id` varchar(255) NOT NULL,
  `payment_status` tinyint(1) NOT NULL DEFAULT 0,
  `link` varchar(255) NOT NULL,
  `serial` int(5) NOT NULL,
  `date` varchar(15) NOT NULL,
  `appointment_status` int(2) NOT NULL DEFAULT 0,
  `rating` float NOT NULL DEFAULT -1,
  `review` varchar(1024) NOT NULL DEFAULT 'not reviewed',
  `review_date` varchar(30) DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`patient_id`, `doctor_id`, `payment_status`, `link`, `serial`, `date`, `appointment_status`, `rating`, `review`, `review_date`) VALUES
('+8801708924486', '12345', 0, '+8801708924486123451631857791493', 10, '2022-01-22', 0, 3.2, 'hhhh', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451631857798720', 2, '2021-8-17', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451631857804715', 3, '2021-8-17', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451638793626190', 11, '2021-11-6', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451638793631936', 12, '2021-11-6', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451642056071214', 14, '2022-01-13', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451642056364086', 15, '2022-0-13', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '12345', 0, '+8801708924486123451647006718122', 1, '2022-03-11', 0, -1, 'not reviewed', '2022-03-11 19:51:58'),
('+8801708924486', '12345', 0, '+8801708924486123451647006856456', 2, '2022-03-11', 0, -1, 'not reviewed', '2022-03-11 19:54:16'),
('+8801708924486', '12345', 0, '+8801708924486123451649479505514', 1, '2022-04-09', 0, -1, 'not reviewed', '2022-04-09 10:45:05'),
('+8801708924486', '12345', 0, '+8801708924486123452022-01-13', 13, '2022-0-13', 0, -1, 'not reviewed', '2022-01-24 13:11:25'),
('+8801708924486', '1234567', 0, '+880170892448612345671638799323342', 1, '2022-01-24', 0, 3, 'i really liked his way of treatment ', '2022-01-24'),
('+8801708924486', '12456', 0, '+8801708924486124561631857824852', 1, '2021-8-17', 0, -1, 'not reviewed', '2022-01-24 13:11:25');

--
-- Triggers `appointments`
--
DELIMITER $$
CREATE TRIGGER `rating_update` AFTER UPDATE ON `appointments` FOR EACH ROW UPDATE doctors
    SET rating = (SELECT AVG(rating) FROM appointments WHERE doctors.bmdc = appointments.doctor_id and appointments.rating !=-1) WHERE bmdc = New.doctor_id
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `appointments_offline`
--

CREATE TABLE `appointments_offline` (
  `patient_id` varchar(255) NOT NULL,
  `doctor_id` varchar(255) NOT NULL,
  `serial` int(5) NOT NULL,
  `date` varchar(15) NOT NULL,
  `rating` float NOT NULL DEFAULT -1,
  `review` varchar(1024) NOT NULL DEFAULT 'not reviewed',
  `review_date` varchar(30) DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `appointments_offline`
--

INSERT INTO `appointments_offline` (`patient_id`, `doctor_id`, `serial`, `date`, `rating`, `review`, `review_date`) VALUES
('+8801708924486', '12345', 1, '2022-03-13', -1, 'not reviewed', '2022-03-12 07:14:55');

-- --------------------------------------------------------

--
-- Table structure for table `blogs`
--

CREATE TABLE `blogs` (
  `doctor_id` varchar(15) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(2048) NOT NULL,
  `image_url` varchar(1024) NOT NULL,
  `date` varchar(15) NOT NULL,
  `ts` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `blogs`
--

INSERT INTO `blogs` (`doctor_id`, `title`, `description`, `image_url`, `date`, `ts`) VALUES
('1234567', 'ece cricket team 2', 'post 2', 'images/blogs/1234567ece cricket team 2.jpeg', '12/28/2021', '2021-12-29'),
('1234567', 'ece cricket team 2ddddghddh', 'post 2', 'images/blogs/1234567ece cricket team 2ddddghddh.jpeg', '12/30/2021', '2021-12-30'),
('1234567', 'ece cricket team 2dddjggjf', 'post 2bdhfjffjfj', 'images/blogs/1234567ece cricket team 2dddjggjf.jpeg', '12/30/2021', '2021-12-30'),
('1234567', 'ece cricket team 2gcgchchvyv', 'post 2', 'images/blogs/1234567ece cricket team 2gcgchchvyv.jpeg', '12/30/2021', '2021-12-30'),
('1234567', 'yxyguvu', 'cyvuuc jvvviuvuvivuvuvuv\nafmkmksamfkam\nasj nfs', 'images/blogs/1234567yxyguvu.jpeg', '12/29/2021', '2021-12-29');

-- --------------------------------------------------------

--
-- Table structure for table `current_serial`
--

CREATE TABLE `current_serial` (
  `doctor_id` varchar(255) NOT NULL,
  `serial` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `current_serial`
--

INSERT INTO `current_serial` (`doctor_id`, `serial`) VALUES
('12345', 0),
('1234567', 1),
('12456', 0);

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `bmdc` varchar(10) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `speciality` varchar(255) NOT NULL,
  `designation` varchar(255) NOT NULL,
  `qualifications` varchar(255) NOT NULL,
  `bio` varchar(1024) NOT NULL,
  `fee` varchar(5) NOT NULL DEFAULT '0',
  `rating` float NOT NULL DEFAULT 0,
  `image_url` varchar(255) NOT NULL DEFAULT 'not set',
  `status` tinyint(1) NOT NULL DEFAULT 0,
  `max_patients` int(3) NOT NULL DEFAULT 50,
  `password` varchar(1024) NOT NULL DEFAULT '12345'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`first_name`, `last_name`, `bmdc`, `phone`, `speciality`, `designation`, `qualifications`, `bio`, `fee`, `rating`, `image_url`, `status`, `max_patients`, `password`) VALUES
('Md.', '', '12345', '+8801708924486', 'Corona Specialist', 'Medical, Dhaka Medical College', 'MBBS,FCPS(DMC)', 'I am a vla bla bla', '500', 3.2, 'images/doctors/12345.jpeg', 1, 50, '12345'),
('Tanvir', '', '123456', '01708924486', 'Corona Specialist', 'Medical officer, Dhaka Medical College', 'MBBS,FCPS(DMC)', 'I am a vla bla bla', '800', 0, 'not set', 0, 50, '12345'),
('Tanvir', 'Ahmmad', '1234567', '+8801521455494', 'abcd', 'cd', 'qy', 'bio', '500', 3, 'not set', 1, 50, '$2a$10$OzFzN0d9N2h/3BS.w.dnf.sCFEF73umiRLrl5.CzWh4V6XWfBnuFS'),
('Ahmmad', '', '12456', '01708924486', 'Corona Specialist', 'Medical officer, Dhaka Medical College', 'MBBS,FCPS(DMC)', 'I am a vla bla bla', '1000', 0, 'not set', 0, 50, '12345');

-- --------------------------------------------------------

--
-- Table structure for table `prescriptions`
--

CREATE TABLE `prescriptions` (
  `appointment_id` varchar(255) NOT NULL,
  `medicines` varchar(1024) NOT NULL,
  `date` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `prescriptions`
--

INSERT INTO `prescriptions` (`appointment_id`, `medicines`, `date`) VALUES
('+880170892448612345671638799323342', 'med1#r1#r0#r1#r28#nmed2#r0#r0#r1#r28#n', '2021-12-09');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `phone` varchar(20) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `age` int(3) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `password` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT 'not set'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`phone`, `first_name`, `last_name`, `email`, `age`, `gender`, `password`, `image_url`) VALUES
('+8801521455494', 'Tanvir', 'Ahmmad', 'tanvirahmed1024@gmail.com', 19, 'Male', '$2a$10$leCjRUhYGdt1lv4USU5AoexmaRDbGBYHysMfhXnYWxmhBsIx7c7Na', 'not set'),
('+8801708924486', 'Tanvir', 'Ahmmad', 'tanvirahmed1024@gmail.com', 23, 'Male', '$2a$10$b7IQJ5D6q8/ABqoY0YEOT.0F1YPMsQZ3zBpwz8mPMqpy84/Pt6fTW', 'images/+8801708924486.jpeg'),
('+8801922370223', 'tanvir', 'ahmmad', 't@gmail.com', 22, 'male', '$2b$10$pv9kj.tGq3PW/dyxG4p9AON4eBiTa8Dpreap9a0GgbNIAzPRvoJOe', 'not set');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`link`),
  ADD KEY `appointments_ibfk_1` (`patient_id`),
  ADD KEY `appointments_ibfk_2` (`doctor_id`);

--
-- Indexes for table `appointments_offline`
--
ALTER TABLE `appointments_offline`
  ADD PRIMARY KEY (`patient_id`,`doctor_id`,`date`);

--
-- Indexes for table `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`doctor_id`,`title`);

--
-- Indexes for table `current_serial`
--
ALTER TABLE `current_serial`
  ADD PRIMARY KEY (`doctor_id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`bmdc`);

--
-- Indexes for table `prescriptions`
--
ALTER TABLE `prescriptions`
  ADD PRIMARY KEY (`appointment_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`phone`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `users` (`phone`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`bmdc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `blogs`
--
ALTER TABLE `blogs`
  ADD CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`bmdc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `current_serial`
--
ALTER TABLE `current_serial`
  ADD CONSTRAINT `current_serial_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`bmdc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `prescriptions`
--
ALTER TABLE `prescriptions`
  ADD CONSTRAINT `prescriptions_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`link`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
