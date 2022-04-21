-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2021 at 05:04 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wihngeheng_mobile`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `employee_name` varchar(50) NOT NULL,
  `employee_address` varchar(255) NOT NULL,
  `employee_phone` varchar(15) NOT NULL,
  `employee_email` varchar(50) NOT NULL,
  `employee_position` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `employee_name`, `employee_address`, `employee_phone`, `employee_email`, `employee_position`) VALUES
(1, 'Adlin Belicia', 'Bambu Kuning 2 No 3-2, Cengkareng, Jakarta Barat', '085884736473', 'adlin@gmail.com', 'Cashier'),
(2, 'Glenys Mae', 'Jl. Imam Bonjol gang tanjung mas no c7, pontianak tenggara, pontianak, kalimantan barat', '089609136483', 'mae@gmail.com', 'Executive Chef'),
(3, 'Angel Caroyallita', 'T17/7 Nusaloka, BSD, Banten', '087882637483', 'angel@gmail.com', 'Pastry Chef'),
(4, 'Giany', 'Griya Paniki', '081227382647', 'giany@gmail.com', 'Sous Chef'),
(6, 'James', 'Jl. Kunyit Jahe', '081236453678', 'jamaah.drew@gmail.com', 'Delivery Man'),
(7, 'Ha Joon', 'Seoul', '088712345678', 'ha.joon@gmail.com', 'Waiter'),
(8, 'Benedict', 'Bambu Kuning', '081212121212', 'ben@gmail.com', 'Pastry Chef'),
(10, 'Kurniawan', 'Pekanbaru', '089677823211', 'kurni@mail.com', 'Waiter'),
(11, 'Bujeng', 'Jakarta', '081747384372', 'bujeng@gmail.com', 'Pastry Chef'),
(12, 'Joko', 'Bekasi Timur', '081212444332', 'joko@mail.com', 'Delivery Man');

-- --------------------------------------------------------

--
-- Table structure for table `favorit`
--

CREATE TABLE `favorit` (
  `favorit_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `favorit`
--

INSERT INTO `favorit` (`favorit_id`, `menu_id`) VALUES
(1, 1),
(3, 8),
(2, 9),
(6, 12),
(5, 14),
(4, 17);

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL,
  `location_name` varchar(50) NOT NULL,
  `location_address` varchar(255) NOT NULL,
  `location_region` varchar(50) NOT NULL,
  `location_phone` varchar(15) NOT NULL,
  `location_openinghours` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`location_id`, `location_name`, `location_address`, `location_region`, `location_phone`, `location_openinghours`) VALUES
(1, 'Muara Karang', 'Jl. Pluit Karang Utara Blok A4U No. 65A, Muara Karang, Penjaringan, Jakarta Utara', 'Jakarta', '081298153977', 'Open for 24 Hours'),
(2, 'Pantai Indah Kapuk', 'Pasar Plaza Pluit, Jakarta Utara', 'Jakarta', '087777660567', 'Senin - Minggu (06:00 - 24:00)'),
(3, 'Gading Serpong', 'Gading Serpong No 75', 'Tangerang', '0812345678906', 'Open for 24 Hours'),
(4, 'Alam Sutera', 'Alam Sutera', 'Tangerang', '081212121212', '10.00-21.00'),
(5, 'Kapuk Muara', 'Pantai Indah Kapuk', 'Jakarta', '081233322211', 'Open for 24 hours'),
(6, 'Suted', 'Serpong', 'Tangerang', '088712345678', '18.00 - 00.00'),
(7, 'Citra 6', 'Citra 6', 'Jakarta', '081212121212', 'Open for 24 Hours'),
(8, 'Pluit', 'Baywalk', 'Jakarta', '081231313234', 'Open for 24 Hours');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL,
  `menu_name` varchar(50) NOT NULL,
  `menu_category` varchar(30) NOT NULL,
  `menu_price` double NOT NULL,
  `menu_description` text NOT NULL,
  `menu_image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`menu_id`, `menu_name`, `menu_category`, `menu_price`, `menu_description`, `menu_image`) VALUES
(1, 'Hakau Udang', 'Steam', 21000, 'Udang dengan kulit beras', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/hakau.jpg'),
(2, 'Siomai Udang', 'Steam', 21000, 'Perpaduan udang, jamur, daun bawang, dan jahe', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/Siomai Udang_1623060450.jpeg'),
(3, 'Ceker Ayam Saus', 'Steam', 22000, 'Ceker ayam dibalur dengan saus pedas gurih', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/Ceker Ayam Saus_1623080213.jpeg'),
(4, 'Bakpao Telur Asin', 'Steam', 24000, 'Roti dengan isian kuning telur asin yang lumer dan nikmat', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/bapao.jpg'),
(5, 'Lumpia Steam', 'Steam', 21000, 'Perpaduan antara udang, daging ayam, dan kulit tahu', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/lumpiasteam.jpg'),
(6, 'Babi Goreng', 'Fried', 24000, 'Potongan daging babi goreng crispy', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/babigoreng.jpg'),
(7, 'Pangsit Udang', 'Fried', 21000, 'Perpaduan udang, daging babi, dan pangsit', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/wonton.jpg'),
(8, 'Lumpia Kulit Tahu', 'Fried', 21000, 'Perpaduan udang dan kulit tahu yang digoreng crispy', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/lumpia.jpg'),
(9, 'Mantau Crispy', 'Fried', 24000, 'Roti mantau goreng crispy', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/mantau.jpg'),
(10, 'Sup Lumpia', 'Soup', 21000, 'Perpaduan daging babi dan kuah kaldu', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/suppangsit.jpg'),
(11, 'Sup Tahu Pong', 'Soup', 21000, 'Dilengkapi tahu dan mie putih', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/suptahu.jpg'),
(12, 'Sup Wonton', 'Soup', 21000, 'Perpaduan pangsit isi, kuah kaldu dan mie kuning', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/suplumpia.jpg'),
(13, 'Nasi Putih', 'Steam', 6000, '-', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/nasiputih.jpg'),
(14, 'Nasi Paikut', 'Steam', 30000, 'Nasi dengan taburan daging babi cincang', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/nasipaikut.jpg'),
(15, 'Nasi Ayam', 'Steam', 30000, 'Nasi dengan taburan daging ayam goreng', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/nasiayam.jpg'),
(16, 'Mie Polos', 'Noodle', 15000, 'Yam mie kuning', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/miepolos.jpg'),
(17, 'Mie Ceker', 'Noodle', 28000, 'Kuah kaldu dengan isian mie kuning dan ceker ayam', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/mieceker.jpg'),
(18, 'Soft Drink', 'Drink', 10000, 'Coca cola, Fanta, Sprite', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/softdrink.jpg'),
(19, 'Jasmine Tea', 'Drink', 15000, 'Teh wangi beraroma bunga melati Jasmine', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/jasminetea.jpg'),
(20, 'Oolong Tea', 'Drink', 15000, 'Teh tradisional khas China', 'http://192.168.1.9/projectmobile/mobile_wihNgeheng/uploads/food/oolongtea.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `ordercustomer`
--

CREATE TABLE `ordercustomer` (
  `order_id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `telp` varchar(20) NOT NULL,
  `tanggal` varchar(10) NOT NULL,
  `lokasi` varchar(50) NOT NULL,
  `layanan` varchar(50) NOT NULL,
  `total` varchar(30) NOT NULL,
  `notes` varchar(700) NOT NULL,
  `coderef` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ordercustomer`
--

INSERT INTO `ordercustomer` (`order_id`, `nama`, `telp`, `tanggal`, `lokasi`, `layanan`, `total`, `notes`, `coderef`) VALUES
(1, 'Jaychou', '08125576482', '07/06/2021', 'Muara Karang', 'Dine In', '42000', '1 Hakau Udang, 1 Ceker Ayam', 'ZVBR5J'),
(3, 'mimi', '081212111212', '07/06/2021', 'Muara Karang', 'Take Away', '63000', '2 Siomai Udang, 1 Hakau Udang', '8D08B4'),
(4, 'Zoey', '081235556777', '07/06/2021', 'Muara Karang', 'Dine In', '63000', '1 Hakau Udang, 2 Siomai Udang', 'HL5PE8');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_address` varchar(255) NOT NULL,
  `user_phone` varchar(15) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_address`, `user_phone`, `user_email`, `user_password`, `user_role`) VALUES
(1, 'Admin', 'Bambu Kuning 2 No 32', '081234567837', 'admin@gmail.com', '$2y$10$tscchMQ4SHJsjqjnt49.VOo.A31oHPpptC8N/Y1ccTguECuVHP9jS', 'admin'),
(2, 'Glenys Mae', 'Pontianak', '089609136483', 'mae@mail.com', '$2y$10$dGE.4Zm6QP/VQBewNg988uxz6iRY072lpfZZq35y14QIcGBlYShkW', 'Customer'),
(3, 'Adlin', 'Jakarta Barat', '089482232112', 'adlin@mail.com', '$2y$10$GBlwVYXrceIDc/KABCEPCOMTsTRZjZgcWa6WuhWFlsyrhKLAh7c.S', 'Customer'),
(4, 'Jay Chou', 'Cluster Tesla 3 No. 14', '081922392842', 'jaychou@gmail.com', '$2y$10$w/n1vTA5yA5h983rfjBtLuVS0JQEEw14p0zmDT9p2LAajly8PGKvK', 'Customer'),
(5, 'Angel Caroy', 'BSD Nusaloka', '089293211221', 'angel@mail.com', '$2y$10$7rrfgFb.KHN1k9v9quKGzOdlif40anLVbRBVezuBwYPIDDZbEMQ2.', 'Customer'),
(6, 'James', 'Gading Serpong, Tangerang', '081224335433', 'james@mail.com', '$2y$10$dwN1oNF0WvyXFpY1HXhCC.cASPcIlCuxw2CjFWoZKyK4.6VUZNqOe', 'Customer'),
(7, 'Giany', 'Manado', '081533321111', 'giany@mail.com', '$2y$10$ZQiGEZAeKCQL06DST2eDwOMFGXc24g3wnvCcc46zuqZmMI2IP4B9e', 'Customer'),
(8, 'mimi', 'Ponti', '081212111212', 'mimi@mail.com', '$2y$10$Wyq5RXm5bJXy0o4OzC9CL.mqNv79CgIQWAbjhx7YcJQu7GvUAbkGy', 'Customer'),
(9, 'Zoey', 'Jakarta', '081235556777', 'zoey@mail.com', '$2y$10$hTiqp0lASI70oa4CaJlAtuG0SIQfSFLYNWf4U8RL8/Z3yQzEdfc/2', 'Customer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`);

--
-- Indexes for table `favorit`
--
ALTER TABLE `favorit`
  ADD PRIMARY KEY (`favorit_id`),
  ADD KEY `menu_id` (`menu_id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`location_id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- Indexes for table `ordercustomer`
--
ALTER TABLE `ordercustomer`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `favorit`
--
ALTER TABLE `favorit`
  MODIFY `favorit_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `location_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `ordercustomer`
--
ALTER TABLE `ordercustomer`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `favorit`
--
ALTER TABLE `favorit`
  ADD CONSTRAINT `favorit_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
