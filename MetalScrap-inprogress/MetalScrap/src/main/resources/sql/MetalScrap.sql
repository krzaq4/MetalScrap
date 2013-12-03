-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Wersja serwera:               5.1.71-community - MySQL Community Server (GPL)
-- Serwer OS:                    Win64
-- HeidiSQL Wersja:              8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Zrzut struktury tabela metalscrap.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `flat_no` varchar(255) DEFAULT NULL,
  `house_no` varchar(255) DEFAULT NULL,
  `postal` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBB979BF4300343AE` (`company`),
  CONSTRAINT `FKBB979BF4300343AE` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.address: ~6 rows (około)
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`id`, `country`, `district`, `flat_no`, `house_no`, `postal`, `street`, `company`, `city`) VALUES
	(1, 'Polska', NULL, '4', '55', '22-000', 'Kropka', NULL, 'Warszawa'),
	(2, 'Polska', NULL, '6', '66', '45-666', 'Witosa', NULL, 'Opole'),
	(3, 'Polska', NULL, '9', '77', '22-987', 'Wiejska', NULL, 'Opole'),
	(4, 'Polska', NULL, '4', '55', '55-234', 'Wirtualna', NULL, 'Bielsko'),
	(5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(6, 'Polska', NULL, '6', '66', '55-256', 'Witosa', NULL, 'Opole');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.auction
CREATE TABLE IF NOT EXISTS `auction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `best_price` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `start_price` double DEFAULT NULL,
  `winner` bigint(20) DEFAULT NULL,
  `status` bigint(20) DEFAULT NULL,
  `owner` bigint(20) DEFAULT NULL,
  `delivery_time` varchar(255) DEFAULT NULL,
  `invoice` bit(1) DEFAULT NULL,
  `delivery_type` bigint(20) DEFAULT NULL,
  `payment_method` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD88CDE43C8680770` (`winner`),
  KEY `FKD88CDE43E2875FBB` (`status`),
  KEY `FKD88CDE43FDAFF9E4` (`owner`),
  KEY `FKD88CDE434D3B4FBF` (`delivery_type`),
  KEY `FKD88CDE43D13A2F75` (`payment_method`),
  CONSTRAINT `FKD88CDE434D3B4FBF` FOREIGN KEY (`delivery_type`) REFERENCES `delivery_type` (`id`),
  CONSTRAINT `FKD88CDE43C8680770` FOREIGN KEY (`winner`) REFERENCES `company` (`id`),
  CONSTRAINT `FKD88CDE43D13A2F75` FOREIGN KEY (`payment_method`) REFERENCES `payment_method` (`id`),
  CONSTRAINT `FKD88CDE43E2875FBB` FOREIGN KEY (`status`) REFERENCES `auction_status` (`id`),
  CONSTRAINT `FKD88CDE43FDAFF9E4` FOREIGN KEY (`owner`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.auction: ~1 rows (około)
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` (`id`, `best_price`, `description`, `end_date`, `start_date`, `start_price`, `winner`, `status`, `owner`, `delivery_time`, `invoice`, `delivery_type`, `payment_method`, `name`, `number`) VALUES
	(1, NULL, NULL, '2013-11-27', '2013-11-26', NULL, NULL, NULL, NULL, '11', NULL, NULL, 1, 'aaa', '111');
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.auction_status
CREATE TABLE IF NOT EXISTS `auction_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.auction_status: ~3 rows (około)
/*!40000 ALTER TABLE `auction_status` DISABLE KEYS */;
INSERT INTO `auction_status` (`id`, `code`, `name`) VALUES
	(1, 1, 'new'),
	(2, 2, 'started'),
	(3, 3, 'finished');
/*!40000 ALTER TABLE `auction_status` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.catalogue_commodities
CREATE TABLE IF NOT EXISTS `catalogue_commodities` (
  `commodity_catalogue_id` bigint(20) NOT NULL,
  `commodities_id` bigint(20) NOT NULL,
  `commodity_id` bigint(20) NOT NULL,
  `catalogues_id` bigint(20) NOT NULL,
  PRIMARY KEY (`commodity_id`,`catalogues_id`),
  KEY `FKF8E2A5B5A7B7744E` (`commodity_id`),
  KEY `FKF8E2A5B55124D90B` (`commodity_catalogue_id`),
  KEY `FKF8E2A5B51EE4D630` (`commodities_id`),
  KEY `FKF8E2A5B58490FCD8` (`catalogues_id`),
  CONSTRAINT `FKF8E2A5B51EE4D630` FOREIGN KEY (`commodities_id`) REFERENCES `commodity` (`id`),
  CONSTRAINT `FKF8E2A5B55124D90B` FOREIGN KEY (`commodity_catalogue_id`) REFERENCES `commodity_catalogue` (`id`),
  CONSTRAINT `FKF8E2A5B58490FCD8` FOREIGN KEY (`catalogues_id`) REFERENCES `commodity_catalogue` (`id`),
  CONSTRAINT `FKF8E2A5B5A7B7744E` FOREIGN KEY (`commodity_id`) REFERENCES `commodity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.catalogue_commodities: ~0 rows (około)
/*!40000 ALTER TABLE `catalogue_commodities` DISABLE KEYS */;
/*!40000 ALTER TABLE `catalogue_commodities` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.catalogue_companies
CREATE TABLE IF NOT EXISTS `catalogue_companies` (
  `contacts_catalogue_id` bigint(20) NOT NULL,
  `companies_id` bigint(20) NOT NULL,
  PRIMARY KEY (`contacts_catalogue_id`,`companies_id`),
  KEY `FK6D50F9855D527C27` (`contacts_catalogue_id`),
  KEY `FK6D50F985319F8530` (`companies_id`),
  CONSTRAINT `FK6D50F985319F8530` FOREIGN KEY (`companies_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK6D50F9855D527C27` FOREIGN KEY (`contacts_catalogue_id`) REFERENCES `contacts_catalogue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.catalogue_companies: ~0 rows (około)
/*!40000 ALTER TABLE `catalogue_companies` DISABLE KEYS */;
/*!40000 ALTER TABLE `catalogue_companies` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.commodity
CREATE TABLE IF NOT EXISTS `commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpv` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `commodity_type` bigint(20) DEFAULT NULL,
  `auction` bigint(20) DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA76C172DCA4754C7` (`commodity_type`),
  KEY `FKA76C172D6FCE873A` (`auction`),
  CONSTRAINT `FKA76C172D6FCE873A` FOREIGN KEY (`auction`) REFERENCES `auction` (`id`),
  CONSTRAINT `FKA76C172DCA4754C7` FOREIGN KEY (`commodity_type`) REFERENCES `commodity_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.commodity: ~1 rows (około)
/*!40000 ALTER TABLE `commodity` DISABLE KEYS */;
INSERT INTO `commodity` (`id`, `cpv`, `description`, `name`, `commodity_type`, `auction`, `quantity`) VALUES
	(1, NULL, NULL, 'p1', 1, 1, 11);
/*!40000 ALTER TABLE `commodity` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.commodity_catalogue
CREATE TABLE IF NOT EXISTS `commodity_catalogue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6B789D7300343AE` (`company`),
  CONSTRAINT `FK6B789D7300343AE` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.commodity_catalogue: ~0 rows (około)
/*!40000 ALTER TABLE `commodity_catalogue` DISABLE KEYS */;
/*!40000 ALTER TABLE `commodity_catalogue` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.commodity_type
CREATE TABLE IF NOT EXISTS `commodity_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.commodity_type: ~3 rows (około)
/*!40000 ALTER TABLE `commodity_type` DISABLE KEYS */;
INSERT INTO `commodity_type` (`id`, `name`) VALUES
	(1, 'Złom stalowy'),
	(2, 'Miedź'),
	(3, 'Aluminium');
/*!40000 ALTER TABLE `commodity_type` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `nip` varchar(255) DEFAULT NULL,
  `regon` varchar(255) DEFAULT NULL,
  `company_auctions` bigint(20) DEFAULT NULL,
  `address_additional` bigint(20) DEFAULT NULL,
  `address_main` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK38A73C7DE74E4509` (`company_auctions`),
  KEY `FK38A73C7D1C86D7FA` (`address_additional`),
  KEY `FK38A73C7D8A09E1CC` (`address_main`),
  CONSTRAINT `FK38A73C7D1C86D7FA` FOREIGN KEY (`address_additional`) REFERENCES `address` (`id`),
  CONSTRAINT `FK38A73C7D8A09E1CC` FOREIGN KEY (`address_main`) REFERENCES `address` (`id`),
  CONSTRAINT `FK38A73C7DE74E4509` FOREIGN KEY (`company_auctions`) REFERENCES `auction` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.company: ~3 rows (około)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `company_name`, `nip`, `regon`, `company_auctions`, `address_additional`, `address_main`) VALUES
	(2, 'Kropa sc', '666666666', '6666666', NULL, 1, 2),
	(3, 'Koras sp zoo', '6767676767', '7777777', NULL, 3, 4),
	(4, 'Kurek', '7652342211', '676767676', NULL, 5, 6);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.company_offer
CREATE TABLE IF NOT EXISTS `company_offer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_issued` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `auction` bigint(20) DEFAULT NULL,
  `company` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK521DA81A6FCE873A` (`auction`),
  KEY `FK521DA81A300343AE` (`company`),
  CONSTRAINT `FK521DA81A300343AE` FOREIGN KEY (`company`) REFERENCES `company` (`id`),
  CONSTRAINT `FK521DA81A6FCE873A` FOREIGN KEY (`auction`) REFERENCES `auction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.company_offer: ~0 rows (około)
/*!40000 ALTER TABLE `company_offer` DISABLE KEYS */;
/*!40000 ALTER TABLE `company_offer` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.contacts_catalogue
CREATE TABLE IF NOT EXISTS `contacts_catalogue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.contacts_catalogue: ~0 rows (około)
/*!40000 ALTER TABLE `contacts_catalogue` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts_catalogue` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.delivery_type
CREATE TABLE IF NOT EXISTS `delivery_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.delivery_type: ~2 rows (około)
/*!40000 ALTER TABLE `delivery_type` DISABLE KEYS */;
INSERT INTO `delivery_type` (`id`, `code`, `name`) VALUES
	(1, 1, 'Odbiór własny'),
	(2, 2, 'Dostawa na koszt sprzedającego');
/*!40000 ALTER TABLE `delivery_type` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.delivery_type_auction
CREATE TABLE IF NOT EXISTS `delivery_type_auction` (
  `delivery_type_id` bigint(20) NOT NULL,
  `auctions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`delivery_type_id`,`auctions_id`),
  UNIQUE KEY `auctions_id` (`auctions_id`),
  KEY `FKC82794A98F423A0F` (`delivery_type_id`),
  KEY `FKC82794A986156621` (`auctions_id`),
  CONSTRAINT `FKC82794A986156621` FOREIGN KEY (`auctions_id`) REFERENCES `auction` (`id`),
  CONSTRAINT `FKC82794A98F423A0F` FOREIGN KEY (`delivery_type_id`) REFERENCES `delivery_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.delivery_type_auction: ~0 rows (około)
/*!40000 ALTER TABLE `delivery_type_auction` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_type_auction` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.payment_method: ~2 rows (około)
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` (`id`, `code`, `name`) VALUES
	(1, 1, 'Płatność gotówką'),
	(2, 2, 'Płatność przelewem');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.payment_method_auction
CREATE TABLE IF NOT EXISTS `payment_method_auction` (
  `payment_method_id` bigint(20) NOT NULL,
  `auctions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`payment_method_id`,`auctions_id`),
  UNIQUE KEY `auctions_id` (`auctions_id`),
  KEY `FKECC9143EC9488C7B` (`payment_method_id`),
  KEY `FKECC9143E86156621` (`auctions_id`),
  CONSTRAINT `FKECC9143E86156621` FOREIGN KEY (`auctions_id`) REFERENCES `auction` (`id`),
  CONSTRAINT `FKECC9143EC9488C7B` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.payment_method_auction: ~0 rows (około)
/*!40000 ALTER TABLE `payment_method_auction` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_method_auction` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.pkd_classification
CREATE TABLE IF NOT EXISTS `pkd_classification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4304051C300343AE` (`company`),
  CONSTRAINT `FK4304051C300343AE` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.pkd_classification: ~0 rows (około)
/*!40000 ALTER TABLE `pkd_classification` DISABLE KEYS */;
/*!40000 ALTER TABLE `pkd_classification` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.role: ~3 rows (około)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_SUPERADMIN'),
	(3, 'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.role_user
CREATE TABLE IF NOT EXISTS `role_user` (
  `role_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`users_id`),
  UNIQUE KEY `users_id` (`users_id`),
  KEY `FK1407FDF4968167C6` (`role_id`),
  KEY `FK1407FDF4349D0449` (`users_id`),
  CONSTRAINT `FK1407FDF4349D0449` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK1407FDF4968167C6` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.role_user: ~0 rows (około)
/*!40000 ALTER TABLE `role_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_user` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `second_name` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36EBCBC4D58F4E` (`company_id`),
  CONSTRAINT `FK36EBCBC4D58F4E` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.user: ~6 rows (około)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `login`, `password`, `second_name`, `company_id`) VALUES
	(1, 'michal.krzaczynski@wp.pl', 'Michał', 'Krzaczyński', 'krzaq4', 'b4e3272ba9df20f7f7d96857643db02d', 'Krzysztof', NULL),
	(3, 'krq4@wp.pl', 'Janek', 'Sufranek', 'krq4', '´ă\'+©ß ÷÷ŮhWd=°-', 'Kranek', 2),
	(4, 'krq4@wp.pl', 'Janek', 'Sufranek', 'krq4', '´ă\'+©ß ÷÷ŮhWd=°-', 'Kranek', 2),
	(5, 'krzaq4@gmail.com', 'Kamil', 'Stoch', 'krzaq464', '´ă\'+©ß ÷÷ŮhWd=°-', 'Krzychu', 3),
	(6, 'krzaq4@gmail.com', 'Kamil', 'Stoch', 'krzaq464', '´ă\'+©ß ÷÷ŮhWd=°-', 'Krzychu', 3),
	(7, 'krzaq4@wp.pl', 'Michal', 'Kupalski', 'kraku464', 'b4e3272ba9df20f7f7d96857643db02d', 'Kra', 4);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  UNIQUE KEY `roles_id` (`roles_id`),
  KEY `FK143BF46A3BAC2BA6` (`user_id`),
  KEY `FK143BF46A3499E01F` (`roles_id`),
  CONSTRAINT `FK143BF46A3499E01F` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46A3BAC2BA6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.user_role: ~3 rows (około)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `roles_id`) VALUES
	(1, 1),
	(1, 2),
	(7, 3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;


-- Zrzut struktury tabela metalscrap.user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `role_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  KEY `FK73429949968167C6` (`role_id`),
  KEY `FK734299493BAC2BA6` (`user_id`),
  KEY `FK734299493499E01F` (`roles_id`),
  KEY `FK73429949349D0449` (`users_id`),
  CONSTRAINT `FK734299493499E01F` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK73429949349D0449` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK734299493BAC2BA6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK73429949968167C6` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table metalscrap.user_roles: ~1 rows (około)
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`role_id`, `users_id`, `user_id`, `roles_id`) VALUES
	(1, 1, 1, 1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
