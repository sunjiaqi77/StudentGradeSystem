# 建表语句
```
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `class` VALUES (1, '2021');
INSERT INTO `class` VALUES (2, '2020');

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ccredit` float NOT NULL,
  `tid` int NOT NULL,
  `tname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `course` VALUES (2, 'java', 3, 2, '孙嘉琦');
INSERT INTO `course` VALUES (3, 'java', 3, 3, '孙嘉琦');
INSERT INTO `course` VALUES (4, 'c', 3, 5, 'tyy');
INSERT INTO `course` VALUES (5, 'web', 2, 6, 'sjq');
INSERT INTO `course` VALUES (6, 'javaee', 2.5, 2, '孙嘉琦');
INSERT INTO `course` VALUES (7, '1', 1, 7, 'zyx');
INSERT INTO `course` VALUES (8, 'c', 1, 8, 'll');
INSERT INTO `course` VALUES (9, 'c', 1, 8, 'll');
INSERT INTO `course` VALUES (10, 'c', 1, 9, 'lpt');
INSERT INTO `course` VALUES (11, '1', 1, 3, '孙嘉琦');
INSERT INTO `course` VALUES (12, '1', 1, 8, 'll');
INSERT INTO `course` VALUES (13, 'ccc', 2, 4, '孙嘉琦');
INSERT INTO `course` VALUES (14, 'abcd', 3.5, 2, '孙嘉琦');
INSERT INTO `course` VALUES (15, '数据库', 2, 2, '孙嘉琦');
INSERT INTO `course` VALUES (16, '高等数学', 4, 7, 'zyx');

DROP TABLE IF EXISTS `courseclass`;
CREATE TABLE `courseclass`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `classid` int NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `courseclass_class_id_fk`(`classid` ASC) USING BTREE,
  INDEX `courseclass_course_cid_fk`(`cid` ASC) USING BTREE,
  CONSTRAINT `courseclass_class_id_fk` FOREIGN KEY (`classid`) REFERENCES `class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `courseclass_course_cid_fk` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `courseclass` VALUES (1, 1, 2);
INSERT INTO `courseclass` VALUES (2, 2, 2);
INSERT INTO `courseclass` VALUES (3, 1, 6);

DROP TABLE IF EXISTS `sct`;
CREATE TABLE `sct`  (
  `sctid` int NOT NULL AUTO_INCREMENT,
  `sid` int NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL,
  `tid` int NULL DEFAULT NULL,
  `grade` float NULL DEFAULT NULL,
  PRIMARY KEY (`sctid`) USING BTREE,
  INDEX `sct_course_cid_fk`(`cid` ASC) USING BTREE,
  INDEX `sct_ibfk_1`(`sid` ASC) USING BTREE,
  INDEX `sct_teacher_tid_fk`(`tid` ASC) USING BTREE,
  CONSTRAINT `sct_course_cid_fk` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sct_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sct_teacher_tid_fk` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `sct` VALUES (1, 9, 2, 2, 99);
INSERT INTO `sct` VALUES (2, 10, 2, 2, 99.5);
INSERT INTO `sct` VALUES (3, 9, 6, 2, 98.5);

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `sid` int NOT NULL AUTO_INCREMENT,
  `sname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `classid` int NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `student` VALUES (9, 'sjq', 1, '12345');
INSERT INTO `student` VALUES (10, '谭杨艺', 1, '123');
INSERT INTO `student` VALUES (11, '孙嘉琦', 1, '123');
INSERT INTO `student` VALUES (12, '谭杨艺', 1, 'tyy');

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tid` int NOT NULL AUTO_INCREMENT,
  `tname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `teacher` VALUES (1, 'admin', '123');
INSERT INTO `teacher` VALUES (2, '孙嘉琦', '12345');
INSERT INTO `teacher` VALUES (3, '孙嘉琦', '1234');
INSERT INTO `teacher` VALUES (4, '孙嘉琦', '234');
INSERT INTO `teacher` VALUES (5, 'tyy', '1234');
INSERT INTO `teacher` VALUES (6, 'sjq', 'qwwerf');
INSERT INTO `teacher` VALUES (7, 'zyx', '1234');
INSERT INTO `teacher` VALUES (8, 'll', '1233');
INSERT INTO `teacher` VALUES (9, 'lpt', 'fdfdfd');
INSERT INTO `teacher` VALUES (10, 'sjq', '12345');
INSERT INTO `teacher` VALUES (11, '孙嘉琦', '111');
INSERT INTO `teacher` VALUES (12, '徐蔚', '56789');
INSERT INTO `teacher` VALUES (13, '王康平', '145678');
INSERT INTO `teacher` VALUES (14, '谢成虎', '124566');
INSERT INTO `teacher` VALUES (15, '江浪', '888888');
INSERT INTO `teacher` VALUES (16, '徐蔚', '56789');
INSERT INTO `teacher` VALUES (17, '王康平', '145678');
INSERT INTO `teacher` VALUES (18, '谢成虎', '124566');
INSERT INTO `teacher` VALUES (19, '江浪', '888888');
INSERT INTO `teacher` VALUES (20, '123', '123');

SET FOREIGN_KEY_CHECKS = 1;

```
