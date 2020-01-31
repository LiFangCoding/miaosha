CREATE TABLE `user_password` (
  `id` int NOT NULL,
  `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci