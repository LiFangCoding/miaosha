CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `gender` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0' COMMENT '//1  means male\n// 2 means female',
  `age` int NOT NULL,
  `telephone` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `register_mode` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '// by phone, bywechat, byalipay',
  `third_party_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci