CREATE TABLE `user-service`.`user_addresses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `address_type` varchar(40) NOT NULL,
  `address_line1` varchar(100) NOT NULL,
  `address_line2` varchar(100),
  `city` varchar(100),
  `state` varchar(100),
  `country` varchar(100),
  `pin` varchar(20),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
