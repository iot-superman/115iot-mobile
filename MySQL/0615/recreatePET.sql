-- 1. 指定使用 mylab 資料庫
USE mylab;

----------------------------------------------------------------------
-- 2. 重建 pet 表格 (包含完整索引、註解、編碼)
----------------------------------------------------------------------
-- 如果已經有這張表，就直接刪除它，確保舊資料與舊計數器完全清空
DROP TABLE IF EXISTS `pet`;

CREATE TABLE `pet` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '寵物編號',
  `name` varchar(30) NOT NULL COMMENT '寵物名稱',
  `owner` varchar(30) DEFAULT NULL COMMENT '主人名稱',
  `species` varchar(30) DEFAULT NULL COMMENT '寵物種類',
  `gender` enum('m','f') DEFAULT NULL COMMENT '寵物性別',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  UNIQUE KEY `id` (`id`),
  KEY `pet_name_index` (`name`),
  KEY `pet_owner_index` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


----------------------------------------------------------------------
-- 3. 重建 event 表格
----------------------------------------------------------------------
DROP TABLE IF EXISTS `event`;

CREATE TABLE `event` (
  `id` bigint unsigned NOT NULL COMMENT '寵物編號',
  `edate` date NOT NULL COMMENT '日期',
  `etype` varchar(15) NOT NULL COMMENT '種類',
  `remark` varchar(255) DEFAULT NULL COMMENT '說明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


----------------------------------------------------------------------
-- 4. 重新寫入標準資料
----------------------------------------------------------------------
-- 寫入 pet 資料（因為設定了 AUTO_INCREMENT=1 且沒指定 id，它會自動從 1 遞增到 7）
INSERT INTO `pet` (`name`, `owner`, `species`, `gender`, `birth`) VALUES 
('Fluffy', 'Harold', 'cat', 'f', '1993-02-04'),
('Claws', 'Gwen', 'cat', 'm', '1994-03-17'),
('Buffy', 'Harold', 'dog', 'f', '1989-05-13'),
('Fang', 'Benny', 'dog', 'm', '1990-08-27'),
('Chirpy', 'Gwen', 'bird', 'f', '1998-09-11'),
('Whistler', 'Gwen', 'bird', NULL, '1997-12-09'),
('Slim', 'Benny', 'snake', 'm', '1996-04-29');

-- 寫入 event 資料（保持原樣，對應剛才生成的 id 1 ~ 7）
INSERT INTO `event` (`id`, `edate`, `etype`, `remark`) VALUES 
(1, '1995-05-15', 'litter', '4 kittens, 3 female, 1 male'),
(3, '1993-06-23', 'litter', '5 puppies, 2 female, 3 male'),
(3, '1994-06-19', 'litter', '3 puppies, 3 female'),
(5, '1999-03-21', 'vet', 'needed beak straightened'),
(7, '1997-08-03', 'vet', 'broken rib'),
(4, '1991-10-12', 'kennel', NULL),
(4, '1998-08-28', 'birthday', 'Gave him a new chew toy'),
(2, '1998-03-17', 'birthday', 'Gave him a new flea collar'),
(6, '1998-12-09', 'birthday', 'First birthday');


----------------------------------------------------------------------
-- 5. 最後驗證與檢查
----------------------------------------------------------------------
SELECT * FROM `pet`;
SELECT * FROM `event`;