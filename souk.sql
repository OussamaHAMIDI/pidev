-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  lun. 21 mai 2018 à 16:24
-- Version du serveur :  10.1.31-MariaDB
-- Version de PHP :  7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `souk`
--

-- --------------------------------------------------------

--
-- Structure de la table `boutique`
--

CREATE TABLE `boutique` (
  `id` int(5) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `nom` varchar(50) NOT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `date_creation` datetime NOT NULL,
  `longitude` double NOT NULL,
  `altitude` double NOT NULL,
  `photo` mediumblob,
  `path_photo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `boutique`
--

INSERT INTO `boutique` (`id`, `id_user`, `nom`, `adresse`, `date_creation`, `longitude`, `altitude`, `photo`, `path_photo`) VALUES
(1, 39, 'ED-Dar', '8 Rue Sidi Ben Arous, Tunis, Tunisie', '2017-01-15 12:00:00', 10.1692177, 36.79942, NULL, '837dc267b01defa965206e5f8a7c24e2.jpeg'),
(2, 39, 'Dar El-Jam', 'Dar El Jem, Avenue Farhat Hached, El Jem, Tunisie', '2014-06-03 07:00:00', 10.7053177, 35.2983988, NULL, '5a1884335c3768ecaa935bb21a329589.jpeg'),
(3, 41, 'EKHO Design', '7 Rue El Andalous, Ariana, Tunisie', '2015-04-15 13:00:00', 10.1997363, 36.8545728, NULL, '8f0b82e6dbf91b6d363b856c89190fb4.jpeg'),
(5, 41, 'Les Beaux Art Romain', 'Maison de l\'Artisanat, P5, Denden, Tunisie', '2018-02-13 11:00:00', 10.1160673, 36.8017902, NULL, 'a10625f80c59ea1cea1e82aa9cfaafe8.jpeg'),
(6, 41, 'Caroantika', 'Maison de l\'Artisanat, P5, Denden, Tunisie', '2013-11-19 23:00:00', 10.1160673, 36.8017902, NULL, '7cfa3d21c8ebf6e3a36ed54471b5eb74.jpeg'),
(7, 36, 'Oriental design', 'Zone Touristiques, Rue de Djerba, Sousse, Tunisie', '2016-06-07 15:00:00', 11.3125984, 33.2687502, NULL, 'b37b8c7e8db00f800e46d3b9b2b08e51.jpeg');

-- --------------------------------------------------------

--
-- Structure de la table `evaluation`
--

CREATE TABLE `evaluation` (
  `id` int(5) NOT NULL,
  `id_boutique` int(5) DEFAULT NULL,
  `id_produit` int(5) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `note` int(11) NOT NULL,
  `date_creation` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `evaluation`
--

INSERT INTO `evaluation` (`id`, `id_boutique`, `id_produit`, `id_user`, `note`, `date_creation`) VALUES
(1, 3, NULL, 26, 1, '2018-02-28 00:00:00'),
(3, 3, NULL, 40, 3, '2018-02-28 00:00:00'),
(4, 6, NULL, 40, 1, '2018-02-28 00:00:00'),
(5, 2, NULL, 40, 2, '2018-02-28 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `id` int(5) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `date_creation` datetime NOT NULL,
  `date_livraison` datetime DEFAULT NULL,
  `total_ttc` decimal(7,3) NOT NULL,
  `frais_livraison` decimal(7,0) NOT NULL,
  `statut` varchar(20) NOT NULL,
  `mode_paiement` varchar(20) NOT NULL,
  `mode_livraison` varchar(20) DEFAULT NULL,
  `est_livre` tinyint(1) NOT NULL,
  `est_paye` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id`, `id_user`, `date_creation`, `date_livraison`, `total_ttc`, `frais_livraison`, `statut`, `mode_paiement`, `mode_livraison`, `est_livre`, `est_paye`) VALUES
(1, 26, '2018-03-01 00:00:00', '2018-03-15 00:00:00', '0.000', '0', 'Temporelle', 'Internet', 'sur place', 0, 0),
(2, 34, '2018-02-06 00:00:00', '2018-03-08 00:00:00', '0.000', '0', 'Temporelle', 'Internet', 'Sur place', 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(5) NOT NULL,
  `boutique` int(11) DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `libelle` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `prix` int(11) NOT NULL,
  `taille` varchar(255) NOT NULL,
  `couleur` varchar(255) NOT NULL,
  `texture` varchar(255) NOT NULL,
  `poids` int(11) NOT NULL,
  `date_creation` date NOT NULL,
  `quantite` int(11) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `boutique`, `reference`, `libelle`, `description`, `prix`, `taille`, `couleur`, `texture`, `poids`, `date_creation`, `quantite`, `path`) VALUES
(1, 3, 'ch002', 'Chachia', 'Chachia rouge ', 30, 'M', 'Rouge', 'Laine', 0, '2018-02-06', 0, 'nbdsqhbqsdpk.jpg'),
(2, 3, 'jb005', 'Jobba', 'jobba tradionelle', 50, 'L', 'Beige', 'Cotton', 0, '2018-01-10', 0, 'bjgfjsqdfnldksnfn oin.jpg'),
(5, 3, 'ch003', 'Chachia B', 'chachia blanche', 35, '', 'blanche', 'Laine', 0, '2018-03-01', 0, 'lksqndlknqlkfnlvns.jpg'),
(6, 3, 'bb22', 'balgha', 'balgha traditionnel', 60, '44', 'noir', '', 0, '2018-02-26', 0, 'nsjqndqsndljknqsld.jpg'),
(7, 3, 'bb8', 'Balgha', 'balgha femme', 45, '37', 'jaune', 'cuir', 0, '0000-00-00', 0, 'dnqsodblnqksndkqn.jpg'),
(11, 5, 'co75', 'couffin', '', 68, 'moyen', '', '', 0, '0000-00-00', 0, 'jfbsdlfknqsd.jpg'),
(12, 5, 'co88', 'coffin', '', 50, 'petit', '', '', 0, '0000-00-00', 0, 'ndjksqndkjnqslkjdnqls.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `produit_panier`
--

CREATE TABLE `produit_panier` (
  `id_panier` int(5) NOT NULL,
  `id_produit` int(5) NOT NULL,
  `reference` varchar(50) NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `prix` float NOT NULL,
  `taille` varchar(10) NOT NULL,
  `couleur` varchar(50) NOT NULL,
  `texture` varchar(50) NOT NULL,
  `poids` float NOT NULL,
  `quantite_vendu` float NOT NULL,
  `poids_vendu` float NOT NULL,
  `prix_vente` float NOT NULL,
  `date_ajout` datetime NOT NULL,
  `livree` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `produit_panier`
--

INSERT INTO `produit_panier` (`id_panier`, `id_produit`, `reference`, `libelle`, `description`, `prix`, `taille`, `couleur`, `texture`, `poids`, `quantite_vendu`, `poids_vendu`, `prix_vente`, `date_ajout`, `livree`) VALUES
(1, 1, 'ch002', 'Chachia', 'Chachia rouge', 30, 'M', 'Rouge', 'Laine', 0, 1, 0, 30, '2018-02-28 00:00:00', 0),
(2, 2, 'jb005', 'Jobba', 'Jobba tradionnelle', 50, 'L', 'Beige', 'Cotton', 0, 1, 0, 50, '2018-02-27 00:00:00', 0);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `id` int(5) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_produit` int(5) DEFAULT NULL,
  `id_boutique` int(5) DEFAULT NULL,
  `description` varchar(1000) NOT NULL,
  `date_creation` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`id`, `id_user`, `id_produit`, `id_boutique`, `description`, `date_creation`) VALUES
(3, 41, 1, 2, 'Le vendeur est méchant', '2018-04-19 00:00:00'),
(4, 40, 6, 6, 'j\'ai acheté une assiette cassée !', '2018-04-19 00:00:00'),
(5, 40, 1, 3, 'j\'ai acheté un koftan déchiré ! je veux l\'échanger au plut tôt possible', '2018-04-19 00:00:00'),
(6, 40, 6, 6, 'j\'ai acheté une assiette cassée !', '2018-04-19 00:00:00'),
(7, 40, 1, 5, 'j\'ai acheté un koftan déchiré ! je veux l\'échanger au plut tôt possible', '2018-04-19 00:00:00'),
(8, 40, 6, 6, 'j\'ai acheté une assiette cassée !', '2018-04-19 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

CREATE TABLE `stock` (
  `id_produit` int(5) NOT NULL,
  `id_boutique` int(5) NOT NULL,
  `quantitee` decimal(7,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tombola`
--

CREATE TABLE `tombola` (
  `id` int(11) NOT NULL,
  `id_gagnant` int(11) DEFAULT NULL,
  `id_artisan` int(11) NOT NULL,
  `titre` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `date_ajout` datetime NOT NULL,
  `date_tirage` datetime DEFAULT NULL,
  `path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_modification` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `tombola`
--

INSERT INTO `tombola` (`id`, `id_gagnant`, `id_artisan`, `titre`, `description`, `date_ajout`, `date_tirage`, `path`, `date_modification`) VALUES
(22, 40, 39, 'ZAREMDINI', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2018-03-07 21:44:30', '2018-04-09 02:43:02', '3e8f1f826d3e80f52add92f4025ab0b67c09ad87.jpeg', '2018-04-09 02:33:24'),
(23, NULL, 39, 'CÉRAMIK ART', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum..', '2018-04-08 04:03:29', '2018-04-18 18:24:26', '19185fee8aa39d2085a432b4090956dd881e9559.jpg', '2018-04-13 10:17:48'),
(42, 40, 41, 'EL FANTAR', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto.', '2018-04-22 00:49:54', '2018-05-04 10:30:08', 'b39f2e72eccb91dbadca189c0dadce8f7fe219b6.jpeg', '2018-05-03 22:28:12'),
(58, 44, 41, 'EL Houch', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto.', '2018-05-04 02:27:25', '2018-05-04 04:20:13', '6942c95c48ffb0000d2781e9b46edf78f62c3d99.jpeg', NULL),
(59, NULL, 41, 'teeeeee65464646kjbkv', 'khvhlvh khvhlvh khvhlvh khvhlvh khvhlvh khvhlvh khvhlvh khvhlvh', '2018-05-10 18:23:41', '2018-05-11 18:22:11', '13b2af8d0f5f7d36dbd1ce1107e8e7e09622f278.jpeg', '2018-05-10 18:25:29');

-- --------------------------------------------------------

--
-- Structure de la table `tombola_participants`
--

CREATE TABLE `tombola_participants` (
  `id` int(11) NOT NULL,
  `id_participant` int(11) DEFAULT NULL,
  `id_tombola` int(11) DEFAULT NULL,
  `date_inscription` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `tombola_participants`
--

INSERT INTO `tombola_participants` (`id`, `id_participant`, `id_tombola`, `date_inscription`) VALUES
(13, 40, 22, '2018-04-09 15:17:26'),
(26, 34, 23, '2018-04-13 10:21:11'),
(27, 40, 23, '2018-04-13 11:20:45'),
(32, 40, 42, '2018-05-03 22:29:57'),
(35, 44, 58, '2018-05-04 02:42:00'),
(36, 40, 58, '2018-05-04 02:42:00'),
(37, 34, 58, '2018-05-04 02:42:00');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(5) NOT NULL,
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
  `roles` longtext NOT NULL COMMENT '(DC2Type:array)',
  `type` enum('Administrateur','Artisan','Client') NOT NULL,
  `etat` enum('Pending','Active','Inactive','Connected','Disconnected','Deleted') NOT NULL,
  `adresse` varchar(150) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `date_naissance` date NOT NULL,
  `sexe` varchar(10) NOT NULL,
  `path_photo_profil` varchar(255) NOT NULL,
  `path_photo_permis` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `type`, `etat`, `adresse`, `tel`, `nom`, `prenom`, `date_naissance`, `sexe`, `path_photo_profil`, `path_photo_permis`) VALUES
(5, 'HamdiMegdiche', 'HamdiMegdiche', 'hamdi.megdiche@esprit.tn', 'hamdi.megdiche@esprit.tn', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', '2018-05-21 15:21:51', 'NS7W72', NULL, 'a:1:{i:0;s:10:\"ROLE_ADMIN\";}', 'Administrateur', 'Connected', '22 rue des rêves, cité la Gazelle', '29201242', 'Hamdi', 'Megdiche', '1995-11-25', 'Male', '33eae475a8b927cb0c4a2bd283ab90d6c6a6e999.jpg', NULL),
(26, 'azza', 'azza', 'azza.daghmouri@esprit.tnc', 'azza.daghmouri@esprit.tnc', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', '2018-05-20 19:06:41', '0580PH', NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Client', 'Disconnected', 'Centre ville', '58852231', 'Daghmouri', 'Azza', '1995-11-24', 'Femelle', '33eae475a8b927cb0c4a2bd283ab90d6c6a6e9e5.jpeg', NULL),
(34, 'montassar', 'montassar', 'mohamedmontassar.laribi@esprit.tn', 'mohamedmontassar.laribi@esprit.tn', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', NULL, 'D5MRPG', NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Client', 'Active', 'riadh landalos', '27451358', 'Laaribi', 'Med Montassar', '1995-01-20', 'Male', '33eae475a8b927cb0c4a2bd283ab90d6c6a6e9e9.jpeg', NULL),
(37, 'oussema', 'oussema', 'oussama.hamidi@esprit.tn', 'oussama.hamidi@esprit.tn', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', '2018-04-13 10:20:48', NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Client', 'Disconnected', 'ghazela', '54476969', 'Hmidi', 'Oussama', '1991-03-07', 'Male', 'f084c2c5a642e4f04be71578a8d7f5a73345a326.jpg', NULL),
(39, 'imen', 'imen', 'imen.benabderrahmen@esprit.tn', 'imen.benabderrahmen@esprit.tn', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', '2018-05-04 02:59:10', NULL, NULL, 'a:1:{i:0;s:12:\"ROLE_ARTISAN\";}', 'Artisan', 'Connected', 'Cité olympique', '96476000', 'BenAbdelrahmann', 'imen', '1993-06-25', 'Femelle', '33eae475a8b927cb0c4a2bd283ab90d6c6a6e9e2.jpeg', NULL),
(40, 'faten', 'faten', 'faten.hmouda@esprit.tn', 'faten.hmouda@esprit.tn', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', '2018-05-04 11:15:36', 'AlvXcEM8QT8fuY6MDx6ndaxRm-cVtU7aZRGcJLeMpYY', NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Client', 'Connected', 'Aouina', '98756645', 'Hmdouda', 'Faten', '1955-01-01', 'Homme', '33eae475a8b927cb0c4a2bd283ab90d6c6a6e9e3.jpeg', NULL),
(41, 'zouzou', 'zouzou', 'azza.daghmouri@esprit.tn', 'azza.daghmouri@esprit.tn', 1, NULL, '$2y$10$B/rYaY7oYsUODdwG3lzwHuvYBBwkqrKGmTxK4PAfjKI9cG./5QphG', '2018-05-10 18:28:09', 'eaFrEJ7Lst91lQyflCRG3pYkNA59BXoZEIg-UnaD8MQ', NULL, 'a:1:{i:0;s:12:\"ROLE_ARTISAN\";}', 'Artisan', 'Connected', 'Mutuelle ville', '27744292', 'Daghmouri', 'Azaa', '1995-01-01', 'Homme', '33eae475a8b927cb0c4a2bd283ab90d6c6a6e9e5.jpeg', NULL),
(44, 'hamdi', 'hamdi', 'hamdi.megdiche@esprit.tnk', 'hamdi.megdiche@esprit.tnk', 1, NULL, '$2y$10$oEEx8y5G2br40g2fa6TGMuRKZabVfYMla8V10e3qzHmvLHGxBvmDy', '2018-04-13 00:12:26', 'NS7W78', NULL, 'a:1:{i:0;s:11:\"ROLE_CLIENT\";}', 'Client', 'Disconnected', '22 rue des rêves, cité la gazelle', '29201242', 'Megdiche', 'Hamdi', '1995-11-25', 'Male', '68845fbb4adde21d564806f45773393fe6c09c75.jpg', NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `boutique`
--
ALTER TABLE `boutique`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_boutique` (`id_boutique`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_29A5EC27A1223C54` (`boutique`);

--
-- Index pour la table `produit_panier`
--
ALTER TABLE `produit_panier`
  ADD PRIMARY KEY (`id_panier`,`id_produit`),
  ADD KEY `id_panier` (`id_panier`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_boutique` (`id_boutique`);

--
-- Index pour la table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id_produit`,`id_boutique`),
  ADD KEY `fk_boutique_id2` (`id_boutique`),
  ADD KEY `id_produit` (`id_produit`);

--
-- Index pour la table `tombola`
--
ALTER TABLE `tombola`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_artisan` (`id_artisan`),
  ADD KEY `id_gagnant` (`id_gagnant`);

--
-- Index pour la table `tombola_participants`
--
ALTER TABLE `tombola_participants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_tombola` (`id_tombola`),
  ADD KEY `id_participant` (`id_participant`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `username_canonical` (`username_canonical`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `email_canonical` (`email_canonical`),
  ADD UNIQUE KEY `confirmation_token` (`confirmation_token`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `boutique`
--
ALTER TABLE `boutique`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `evaluation`
--
ALTER TABLE `evaluation`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `panier`
--
ALTER TABLE `panier`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `tombola`
--
ALTER TABLE `tombola`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT pour la table `tombola_participants`
--
ALTER TABLE `tombola_participants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `FK_1323A5756B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_1323A575F7384557` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_1323A575F7384558` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `FK_24CC0DF26B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `produit_panier`
--
ALTER TABLE `produit_panier`
  ADD CONSTRAINT `fk_idPanier1` FOREIGN KEY (`id_panier`) REFERENCES `panier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_produit_id7` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `FK_CE6064046B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_CE606404F7384557` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_CE606404F7384558` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `fk_boutique_id2` FOREIGN KEY (`id_boutique`) REFERENCES `boutique` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_produit_id` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `tombola`
--
ALTER TABLE `tombola`
  ADD CONSTRAINT `FK_3C12E3C5DBA0A1EC` FOREIGN KEY (`id_gagnant`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_3C12E3C5E2FDA3A3` FOREIGN KEY (`id_artisan`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `tombola_participants`
--
ALTER TABLE `tombola_participants`
  ADD CONSTRAINT `FK_90A4DB30CF8DA6E6` FOREIGN KEY (`id_participant`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_90A4DB30E28F4AB5` FOREIGN KEY (`id_tombola`) REFERENCES `tombola` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
