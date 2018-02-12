-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 11, 2018 at 12:53 PM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `souk`
--

-- --------------------------------------------------------

--
-- Table structure for table `boutique`
--

DROP TABLE IF EXISTS `boutique`;
CREATE TABLE IF NOT EXISTS `boutique` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `id_user` int(5) NOT NULL,
  `id_produit_boutique` int(5) DEFAULT NULL,
  `nom` varchar(50) NOT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `date_creation` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_produit_boutique` (`id_produit_boutique`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `boutique`
--

INSERT INTO `boutique` (`id`, `id_user`, `id_produit_boutique`, `nom`, `adresse`, `date_creation`) VALUES
(2, 2, NULL, 'boub', NULL, '2018-02-01 00:00:00'),
(4, 1, NULL, 'hello', '36rue', '2018-02-11 13:36:35'),
(7, 1, NULL, 'hello', '36rue', '2018-02-11 13:42:50'),
(8, 1, NULL, 'hello', '36rue', '2018-02-11 13:46:42'),
(9, 1, NULL, 'hello', '36rue', '2018-02-11 13:47:58'),
(10, 1, NULL, 'hello', '36rue', '2018-02-11 13:48:54'),
(11, 1, NULL, 'hello', '36rue', '2018-02-11 13:50:20'),
(12, 1, NULL, 'hello', '36rue', '2018-02-11 13:52:11'),
(13, 1, NULL, 'hello', '36rue', '2018-02-11 13:52:47');

-- --------------------------------------------------------

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE IF NOT EXISTS `evaluation` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `id_boutique` int(5) DEFAULT NULL,
  `id_produit` int(5) DEFAULT NULL,
  `id_user` int(5) NOT NULL,
  `note` int(2) NOT NULL COMMENT 'int 1 à 10',
  `date_creation` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_boutique` (`id_boutique`),
  KEY `id_user` (`id_user`),
  KEY `id_produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `id_user` int(5) NOT NULL,
  `date_creation` datetime NOT NULL,
  `date_livraison` datetime NOT NULL,
  `total_ttc` decimal(7,3) NOT NULL,
  `frais_livraison` decimal(7,0) NOT NULL,
  `statut` varchar(20) NOT NULL,
  `mode_paiement` varchar(20) NOT NULL,
  `est_livre` tinyint(1) NOT NULL,
  `est_paye` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `reference` varchar(50) NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `prix` decimal(7,3) NOT NULL,
  `taille` varchar(50) DEFAULT NULL,
  `couleur` varchar(50) DEFAULT NULL,
  `texture` varchar(50) DEFAULT NULL,
  `poids` decimal(7,3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`id`, `reference`, `libelle`, `description`, `prix`, `taille`, `couleur`, `texture`, `poids`) VALUES
(2, 'jkgugu', 'checheya', 'mezyena', '2.500', 'M', 'rouge', 'kamraya', '2.500'),
(3, 'jkgugu', 'checheya', 'mezyena', '2.500', 'M', 'rouge', 'kamraya', '2.500');

-- --------------------------------------------------------

--
-- Table structure for table `produit_boutique`
--

DROP TABLE IF EXISTS `produit_boutique`;
CREATE TABLE IF NOT EXISTS `produit_boutique` (
  `id_boutique` int(5) NOT NULL,
  `id_produit` int(5) NOT NULL,
  `date_ajout` datetime NOT NULL,
  PRIMARY KEY (`id_boutique`,`id_produit`),
  KEY `id_boutique` (`id_boutique`),
  KEY `id_produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produit_boutique`
--

INSERT INTO `produit_boutique` (`id_boutique`, `id_produit`, `date_ajout`) VALUES
(2, 2, '2018-02-01 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `produit_panier`
--

DROP TABLE IF EXISTS `produit_panier`;
CREATE TABLE IF NOT EXISTS `produit_panier` (
  `id_panier` int(5) NOT NULL,
  `id_produit` int(5) NOT NULL,
  PRIMARY KEY (`id_panier`,`id_produit`),
  KEY `id_panier` (`id_panier`),
  KEY `id_produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `reclamation`
--

DROP TABLE IF EXISTS `reclamation`;
CREATE TABLE IF NOT EXISTS `reclamation` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `id_user` int(5) NOT NULL,
  `id_produit` int(5) DEFAULT NULL,
  `id_boutique` int(5) DEFAULT NULL,
  `description` varchar(500) NOT NULL,
  `date_creation` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_produit` (`id_produit`),
  KEY `id_boutique` (`id_boutique`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reclamation`
--

INSERT INTO `reclamation` (`id`, `id_user`, `id_produit`, `id_boutique`, `description`, `date_creation`) VALUES
(21, 2, NULL, 2, 'maaasset', '2018-02-11 13:46:42'),
(23, 2, NULL, 2, 'maaasset', '2018-02-11 13:47:59'),
(25, 2, NULL, 2, 'maaasset', '2018-02-11 13:48:54'),
(27, 2, NULL, 2, 'maaasset', '2018-02-11 13:50:21'),
(29, 2, NULL, 2, 'maaasset', '2018-02-11 13:52:11'),
(30, 2, 2, NULL, 'hloww barcha', '2018-02-11 13:52:12'),
(31, 2, NULL, 2, 'maaasset', '2018-02-11 13:52:47'),
(32, 2, 2, NULL, 'hloww barcha', '2018-02-11 13:52:47');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `id_produit` int(5) NOT NULL,
  `id_boutique` int(5) NOT NULL,
  `quantitee` decimal(7,3) NOT NULL,
  PRIMARY KEY (`id_produit`,`id_boutique`),
  KEY `fk_boutique_id2` (`id_boutique`),
  KEY `id_produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `username` varchar(180) NOT NULL,
  `username_canonical` varchar(180) NOT NULL,
  `email` varchar(180) NOT NULL,
  `email_canonical` varchar(180) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext NOT NULL,
  `type` varchar(20) NOT NULL,
  `etat` varchar(20) NOT NULL,
  `adresse` varchar(150) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `date_naissance` datetime NOT NULL,
  `sexe` varchar(10) NOT NULL,
  `photo_profil` mediumblob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `confirmation_token` (`confirmation_token`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `type`, `etat`, `adresse`, `tel`, `nom`, `prenom`, `date_naissance`, `sexe`, `photo_profil`) VALUES
(1, 'Honoloulou', 'Honoloulou', 'Honoloulou.ettaher@esprit.tn', 'Honoloulou.ettaher@esprit.tn', 0, '5487', 'HonoloulouHonoloulou', '2018-02-11 12:21:37', NULL, NULL, 'ra9assa', 'Client', 'Connected', '', '', 'Honoloulou', 'Honoloulou', '1996-03-14 00:00:00', 'Femme', NULL),
(2, 'imen', 'imn', 'imen.benabderrahmen@esprit.tn', 'imen.ba', 0, 'kdkdkjdjdjd', 'imenba', '2018-02-01 00:00:00', NULL, '2018-02-01 00:00:00', '', 'Client', 'Active', 'Cite Olympique', '96476000', 'benabderrahmen', 'imen', '2018-02-01 00:00:00', 'fem', NULL),
(4, 'HamdiMegdiche', 'HamdiMegdiche', 'Honoloulou.ettaher@esprit.tn', 'Honoloulou.ettaher@esprit.tn', 1, NULL, 'HonoloulouHonoloulou', '2018-02-11 00:31:46', NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Client', 'Connected', '', '', 'Honoloulou', 'Honoloulou', '1996-03-14 00:00:00', 'Femme', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `boutique`
--
ALTER TABLE `boutique`
  ADD CONSTRAINT `fk_idProduitBoutique_B` FOREIGN KEY (`id_produit_boutique`) REFERENCES `produit_boutique` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idUser_B` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `FK_idBoutique` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idUser` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_produit_id3` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `fk_user_id2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `produit_boutique`
--
ALTER TABLE `produit_boutique`
  ADD CONSTRAINT `fk_boutique_id4` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_produit_id4` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `produit_panier`
--
ALTER TABLE `produit_panier`
  ADD CONSTRAINT `FK_id_panier` FOREIGN KEY (`id_panier`) REFERENCES `panier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_id_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `fk_idBoutique_R` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idProduit_R` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idUser_R` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `fk_boutique_id2` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_produit_id` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
