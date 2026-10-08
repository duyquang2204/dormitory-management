-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: dormitory_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bao_cao_thong_ke`
--

DROP TABLE IF EXISTS `bao_cao_thong_ke`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bao_cao_thong_ke` (
  `ma_bao_cao` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam` int DEFAULT NULL,
  `ngay_lap` date DEFAULT NULL,
  `so_luongsv` int DEFAULT NULL,
  `so_luong_sua_chua` int DEFAULT NULL,
  `so_luong_vi_pham` int DEFAULT NULL,
  `thang` int DEFAULT NULL,
  `ma_khu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_nguoi_lap` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_bao_cao`),
  KEY `FK5o122wjrvswoei9p3qw76is1h` (`ma_khu`),
  KEY `FK1fhebxctbd6g649edtmhhofll` (`ma_nguoi_lap`),
  CONSTRAINT `FK1fhebxctbd6g649edtmhhofll` FOREIGN KEY (`ma_nguoi_lap`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`),
  CONSTRAINT `FK5o122wjrvswoei9p3qw76is1h` FOREIGN KEY (`ma_khu`) REFERENCES `khu` (`ma_khu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bao_cao_thong_ke`
--

LOCK TABLES `bao_cao_thong_ke` WRITE;
/*!40000 ALTER TABLE `bao_cao_thong_ke` DISABLE KEYS */;
/*!40000 ALTER TABLE `bao_cao_thong_ke` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chuyen_phong`
--

DROP TABLE IF EXISTS `chuyen_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chuyen_phong` (
  `ma_chuyen_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ly_do` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_xu_ly` date DEFAULT NULL,
  `ngay_yeu_cau` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong_moi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_chuyen_phong`),
  KEY `FKam0325u1jndcmn5m5awsunfmj` (`ma_phan_phong`),
  KEY `FK6hji132neohk75kmmfrtopjog` (`ma_phong_moi`),
  CONSTRAINT `FK6hji132neohk75kmmfrtopjog` FOREIGN KEY (`ma_phong_moi`) REFERENCES `phong` (`ma_phong`),
  CONSTRAINT `FKam0325u1jndcmn5m5awsunfmj` FOREIGN KEY (`ma_phan_phong`) REFERENCES `phan_phong` (`ma_phan_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuyen_phong`
--

LOCK TABLES `chuyen_phong` WRITE;
/*!40000 ALTER TABLE `chuyen_phong` DISABLE KEYS */;
/*!40000 ALTER TABLE `chuyen_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dang_kyktx`
--

DROP TABLE IF EXISTS `dang_kyktx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dang_kyktx` (
  `ma_dang_ky` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_dang_ky` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_minh_chung_uu_tien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_the_sinh_vien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dien_uu_tien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `khoa` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ly_do_tu_choi` text COLLATE utf8mb4_unicode_ci,
  `nam_hoc` int DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `que_quan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `truong_dai_hoc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_dang_ky`),
  KEY `FK1ds80lcl00j9hfcehel7twl20` (`ma_loai_phong`),
  KEY `FKh7urc5psuhfnxfg0tb7aq77dn` (`masv`),
  CONSTRAINT `FK1ds80lcl00j9hfcehel7twl20` FOREIGN KEY (`ma_loai_phong`) REFERENCES `loai_phong` (`ma_loai_phong`),
  CONSTRAINT `FKh7urc5psuhfnxfg0tb7aq77dn` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dang_kyktx`
--

LOCK TABLES `dang_kyktx` WRITE;
/*!40000 ALTER TABLE `dang_kyktx` DISABLE KEYS */;
INSERT INTO `dang_kyktx` VALUES ('DK172165','','2026-10-08','Đã duyệt','LP08','SV0001','/uploads/cccd_b7fc1.png',NULL,'/uploads/the_sv_9707c.png','0123456789','Không ưu tiên','duyquang22042005@gmail.com','Nam','Nguyễn Duy Quang','Công nghệ thông tin',NULL,1,'2005-04-22','xã Đăk Mar, tỉnh Quảng Ngãi','0378869290','ĐH Công nghệ Kỹ thuật '),('DK1962009','em muốn ở chung với bạn Duy Quang','2026-10-08','Đã duyệt','LP04','SV0002',NULL,NULL,NULL,'01234567877','','quangduy22042005@gmail.com','Nam','Nguyễn Quang Duy','',NULL,2026,'2005-04-23','xã Đăk Mar, tỉnh Quảng Ngãi','0378869291',NULL),('DK357178','Em muốn đổi nguyện vọng','2026-10-08','Đã duyệt','LP06','SV0001',NULL,NULL,NULL,'0123456789','Không ưu tiên','duyquang22042005@gmail.com','Nam','Nguyễn Duy Quang','Công nghệ thông tin',NULL,1,'2005-04-22','xã Đăk Mar, tỉnh Quảng Ngãi','0378869290','ĐH Công nghệ Kỹ thuật '),('DK4852761','em muốn đổi nguyện vọng','2026-10-08','Đã duyệt','LP04','SV0001',NULL,NULL,NULL,'0123456789','Không ưu tiên','duyquang22042005@gmail.com','Nam','Nguyễn Duy Quang','Công nghệ thông tin',NULL,1,'2005-04-22','xã Đăk Mar, tỉnh Quảng Ngãi','0378869290','ĐH Công nghệ Kỹ thuật ');
/*!40000 ALTER TABLE `dang_kyktx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gia_han_hop_dong`
--

DROP TABLE IF EXISTS `gia_han_hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gia_han_hop_dong` (
  `ma_gia_han` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_bat_dau_moi` date DEFAULT NULL,
  `ngay_ket_thuc_moi` date DEFAULT NULL,
  `ngay_yeu_cau` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_hop_dong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_gia_han`),
  KEY `FKl5erf0oboku2ud4i48d0g9op3` (`ma_hop_dong`),
  CONSTRAINT `FKl5erf0oboku2ud4i48d0g9op3` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gia_han_hop_dong`
--

LOCK TABLES `gia_han_hop_dong` WRITE;
/*!40000 ALTER TABLE `gia_han_hop_dong` DISABLE KEYS */;
/*!40000 ALTER TABLE `gia_han_hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `ma_hoa_don` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chi_so_dien_cu` double DEFAULT NULL,
  `chi_so_dien_moi` double DEFAULT NULL,
  `chi_so_nuoc_cu` double DEFAULT NULL,
  `chi_so_nuoc_moi` double DEFAULT NULL,
  `minh_chung_thanh_toan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam` int DEFAULT NULL,
  `ngay_tao` date DEFAULT NULL,
  `thang` int DEFAULT NULL,
  `tien_phong` double DEFAULT NULL,
  `tong_tien` double DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phuong_thuc_thanh_toan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tien_dien` double DEFAULT NULL,
  `tien_nuoc` double DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_hoa_don`),
  KEY `FK98kur1iyt6w09bate6c1596hk` (`ma_phong`),
  KEY `FK2gjro9egb6sfmfd2ww27tsodj` (`masv`),
  CONSTRAINT `FK2gjro9egb6sfmfd2ww27tsodj` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FK98kur1iyt6w09bate6c1596hk` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES ('2bcbbb25-c397-40ce-916b-685ca26f26b5',0,200,0,10,NULL,2026,'2026-10-08',1,1500000,1925000,'Đã thanh toán','C301',NULL,350000,75000,'SV0002'),('59e76208-72e1-490a-9c5d-f7664995e0eb',0,200,0,10,NULL,2026,'2026-10-08',1,1500000,1925000,'Đã thanh toán','C301',NULL,350000,75000,'SV0001');
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hop_dong`
--

DROP TABLE IF EXISTS `hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hop_dong` (
  `ma_hop_dong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dieu_khoan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gia_tien` double DEFAULT NULL,
  `ngay_bat_dau` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `ngay_lap` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_nhan_vien_lap` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yeu_cau_cuasv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_hop_dong`),
  KEY `FKpoahr4jjngsi7puqo9s7xwonh` (`ma_nhan_vien_lap`),
  KEY `FK4u03eto7ofpcubcvhusixs6mg` (`ma_phan_phong`),
  CONSTRAINT `FK4u03eto7ofpcubcvhusixs6mg` FOREIGN KEY (`ma_phan_phong`) REFERENCES `phan_phong` (`ma_phan_phong`),
  CONSTRAINT `FKpoahr4jjngsi7puqo9s7xwonh` FOREIGN KEY (`ma_nhan_vien_lap`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hop_dong`
--

LOCK TABLES `hop_dong` WRITE;
/*!40000 ALTER TABLE `hop_dong` DISABLE KEYS */;
INSERT INTO `hop_dong` VALUES ('HD2025569',NULL,1500000,'2026-10-08','2027-04-08','2026-10-08','Còn hạn',NULL,'0a3a28aa-5573-4d71-8156-6fa72cd0fc84',NULL),('HD207071',NULL,800000,'2026-10-08','2027-04-08','2026-10-08','Đã thanh lý',NULL,'121af071-cbcb-4179-8f3f-cc763c61fd9f',NULL),('HD397869',NULL,1000000,'2026-10-08','2027-04-08','2026-10-08','Đã hết hạn',NULL,'169972eb-dfba-4478-a3a4-32d9112ac73b',NULL),('HD4901757',NULL,1500000,'2026-10-08','2027-04-08','2026-10-08','Còn hạn',NULL,'6743419c-60c2-48d0-b797-a0aa169b26d1',NULL);
/*!40000 ALTER TABLE `hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ket_qua_nhan_vien`
--

DROP TABLE IF EXISTS `ket_qua_nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ket_qua_nhan_vien` (
  `ma_phan_cong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `manvsc` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  KEY `FK2shfip59haqk2mtvwd7s0sn97` (`manvsc`),
  KEY `FKmw6fayooyw4578wgsp83sgxos` (`ma_phan_cong`),
  CONSTRAINT `FK2shfip59haqk2mtvwd7s0sn97` FOREIGN KEY (`manvsc`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`),
  CONSTRAINT `FKmw6fayooyw4578wgsp83sgxos` FOREIGN KEY (`ma_phan_cong`) REFERENCES `ket_qua_sua_chua` (`ma_phan_cong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ket_qua_nhan_vien`
--

LOCK TABLES `ket_qua_nhan_vien` WRITE;
/*!40000 ALTER TABLE `ket_qua_nhan_vien` DISABLE KEYS */;
INSERT INTO `ket_qua_nhan_vien` VALUES ('2a43dbae-97a3-4f85-bb53-07df9da4ff08','06664612-d0d7-4293-8e97-41f3d9bbba00');
/*!40000 ALTER TABLE `ket_qua_nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ket_qua_sua_chua`
--

DROP TABLE IF EXISTS `ket_qua_sua_chua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ket_qua_sua_chua` (
  `ma_phan_cong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ghi_chu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ket_qua` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_hoan_thanh` date DEFAULT NULL,
  `ngay_tiep_nhan` date DEFAULT NULL,
  `noi_dung_xu_ly` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `manvsc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_yeu_cau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hinh_anh_minh_chung` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_phan_cong`),
  UNIQUE KEY `UKe4l4j1f0m62vptw8t6xyjjjcd` (`ma_yeu_cau`),
  KEY `FKo5iyy2n5799poesfbnw7py4v2` (`manvsc`),
  CONSTRAINT `FKo5iyy2n5799poesfbnw7py4v2` FOREIGN KEY (`manvsc`) REFERENCES `nguoi_dung` (`ma_nguoi_dung`),
  CONSTRAINT `FKopstulyki7xnfeapvi4k8d4su` FOREIGN KEY (`ma_yeu_cau`) REFERENCES `yeu_cau_sua_chua` (`ma_yeu_cau`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ket_qua_sua_chua`
--

LOCK TABLES `ket_qua_sua_chua` WRITE;
/*!40000 ALTER TABLE `ket_qua_sua_chua` DISABLE KEYS */;
INSERT INTO `ket_qua_sua_chua` VALUES ('2a43dbae-97a3-4f85-bb53-07df9da4ff08',NULL,'Đã sửa xong','2026-10-08','2026-10-08','Đã sửa đèn',NULL,'3a5b431c-3e38-4122-90d4-9934d0993c64','/uploads/PhongC-301_d895c.jpg');
/*!40000 ALTER TABLE `ket_qua_sua_chua` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khu`
--

DROP TABLE IF EXISTS `khu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khu` (
  `ma_khu` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mo_ta` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ten_khu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_khu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khu`
--

LOCK TABLES `khu` WRITE;
/*!40000 ALTER TABLE `khu` DISABLE KEYS */;
INSERT INTO `khu` VALUES ('A','Khu A - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 8 người','Khu A'),('B','Khu B - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 6 người','Khu B'),('C','Khu C - Tòa 4 tầng (Tầng 1-2: Nữ | Tầng 3-4: Nam) - Phòng 4 người cao cấp','Khu C');
/*!40000 ALTER TABLE `khu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_phong`
--

DROP TABLE IF EXISTS `loai_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_phong` (
  `ma_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `don_gia` double DEFAULT NULL,
  `mo_ta` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `so_nguoi_toi_da` int DEFAULT NULL,
  `ten_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_loai_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_phong`
--

LOCK TABLES `loai_phong` WRITE;
/*!40000 ALTER TABLE `loai_phong` DISABLE KEYS */;
INSERT INTO `loai_phong` VALUES ('LP04',1500000,'Phòng 4 người cao cấp: Điều hòa Inverter 24/24, quạt trần, bình nóng lạnh, wifi riêng, nệm êm ái',4,'Phòng 4 người'),('LP06',1000000,'Phòng 6 người tiện nghi: Có quạt trần, bình nóng lạnh, wifi tốc độ cao, tủ cá nhân (không có máy điều hòa)',6,'Phòng 6 người'),('LP08',800000,'Phòng 8 người tiết kiệm: Có quạt trần công suất lớn, wifi tốc độ cao, tủ cá nhân (không điều hòa, không nóng lạnh)',8,'Phòng 8 người');
/*!40000 ALTER TABLE `loai_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguoi_dung`
--

DROP TABLE IF EXISTS `nguoi_dung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoi_dung` (
  `ma_nguoi_dung` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `chuc_vu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ten_dang_nhap` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_nguoi_dung`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoi_dung`
--

LOCK TABLES `nguoi_dung` WRITE;
/*!40000 ALTER TABLE `nguoi_dung` DISABLE KEYS */;
INSERT INTO `nguoi_dung` VALUES ('06664612-d0d7-4293-8e97-41f3d9bbba00','Nhân viên sửa chữa','Trần Thợ Sửa','$2a$10$CmwPf2VWczreQJ3d9QNUxu0k1F05966pkYStQRvtUwyCsjIqRuxVO','0378869290','nhanvien','Hoạt động'),('0797a054-73c4-45d8-8154-4e40181c0374','Quản sinh','Nguyễn Quản Sinh','$2a$10$3XVzN0/uP1yhEeyji9Pt0.Fq8v/ZNbt3ckrPShCAuy86zm.sTjvIq','0378869291','quansinh','Hoạt động'),('1ace699f-9928-46a2-8bdb-d92d6aa0e131','Quản trị viên','Quản trị viên hệ thống','$2a$10$NFUu.2WGKXuqfN9rulGfN.GFJRGOfE4Pw02scqWQylVDCjFjWbLgW','0378869292','admin','Hoạt động');
/*!40000 ALTER TABLE `nguoi_dung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_xac_thuc`
--

DROP TABLE IF EXISTS `otp_xac_thuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_xac_thuc` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `da_su_dung` bit(1) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `loai_otp` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `otp_code` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `thoi_gian_het_han` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_xac_thuc`
--

LOCK TABLES `otp_xac_thuc` WRITE;
/*!40000 ALTER TABLE `otp_xac_thuc` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp_xac_thuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phan_phong`
--

DROP TABLE IF EXISTS `phan_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phan_phong` (
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ngay_bat_dau` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_dang_ky` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_phan_phong`),
  UNIQUE KEY `UKn28vrbmklsr25j64t2bijmqp` (`ma_dang_ky`),
  KEY `FKmun4hrn6h8i8bwchsfag6mo2j` (`ma_phong`),
  KEY `FK5nv6g25gr8oa6jt3sm4dqnq3v` (`masv`),
  CONSTRAINT `FK3eb5is5gvqjao8aosh7mydmjh` FOREIGN KEY (`ma_dang_ky`) REFERENCES `dang_kyktx` (`ma_dang_ky`),
  CONSTRAINT `FK5nv6g25gr8oa6jt3sm4dqnq3v` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FKmun4hrn6h8i8bwchsfag6mo2j` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phan_phong`
--

LOCK TABLES `phan_phong` WRITE;
/*!40000 ALTER TABLE `phan_phong` DISABLE KEYS */;
INSERT INTO `phan_phong` VALUES ('0a3a28aa-5573-4d71-8156-6fa72cd0fc84','2026-10-08','2027-04-08','Đang ở','DK1962009','C301','SV0002'),('121af071-cbcb-4179-8f3f-cc763c61fd9f','2026-10-08','2027-04-08','Đã trả phòng','DK172165','A301','SV0001'),('169972eb-dfba-4478-a3a4-32d9112ac73b','2026-10-08','2027-04-08','Đã kết thúc','DK357178','B301','SV0001'),('6743419c-60c2-48d0-b797-a0aa169b26d1','2026-10-08','2027-04-08','Đang ở','DK4852761','C301','SV0001');
/*!40000 ALTER TABLE `phan_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong` (
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `so_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_khu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_loai_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tang` int DEFAULT NULL,
  PRIMARY KEY (`ma_phong`),
  KEY `FKe4rcyk79xtbu58p52nlydr2p` (`ma_khu`),
  KEY `FK378h0h60ooky42egxi2ckdqu` (`ma_loai_phong`),
  CONSTRAINT `FK378h0h60ooky42egxi2ckdqu` FOREIGN KEY (`ma_loai_phong`) REFERENCES `loai_phong` (`ma_loai_phong`),
  CONSTRAINT `FKe4rcyk79xtbu58p52nlydr2p` FOREIGN KEY (`ma_khu`) REFERENCES `khu` (`ma_khu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES ('A101','A-101','Trống','A','LP08','Nữ',1),('A102','A-102','Trống','A','LP08','Nữ',1),('A103','A-103','Trống','A','LP08','Nữ',1),('A104','A-104','Trống','A','LP08','Nữ',1),('A105','A-105','Trống','A','LP08','Nữ',1),('A106','A-106','Trống','A','LP08','Nữ',1),('A107','A-107','Trống','A','LP08','Nữ',1),('A108','A-108','Trống','A','LP08','Nữ',1),('A109','A-109','Trống','A','LP08','Nữ',1),('A110','A-110','Trống','A','LP08','Nữ',1),('A111','A-111','Trống','A','LP08','Nữ',1),('A112','A-112','Trống','A','LP08','Nữ',1),('A201','A-201','Trống','A','LP08','Nữ',2),('A202','A-202','Trống','A','LP08','Nữ',2),('A203','A-203','Trống','A','LP08','Nữ',2),('A204','A-204','Trống','A','LP08','Nữ',2),('A205','A-205','Trống','A','LP08','Nữ',2),('A206','A-206','Trống','A','LP08','Nữ',2),('A207','A-207','Trống','A','LP08','Nữ',2),('A208','A-208','Trống','A','LP08','Nữ',2),('A209','A-209','Trống','A','LP08','Nữ',2),('A210','A-210','Trống','A','LP08','Nữ',2),('A211','A-211','Trống','A','LP08','Nữ',2),('A212','A-212','Trống','A','LP08','Nữ',2),('A301','A-301','Còn trống','A','LP08','Nam',3),('A302','A-302','Trống','A','LP08','Nam',3),('A303','A-303','Trống','A','LP08','Nam',3),('A304','A-304','Trống','A','LP08','Nam',3),('A305','A-305','Trống','A','LP08','Nam',3),('A306','A-306','Trống','A','LP08','Nam',3),('A307','A-307','Trống','A','LP08','Nam',3),('A308','A-308','Trống','A','LP08','Nam',3),('A309','A-309','Trống','A','LP08','Nam',3),('A310','A-310','Trống','A','LP08','Nam',3),('A311','A-311','Trống','A','LP08','Nam',3),('A312','A-312','Trống','A','LP08','Nam',3),('A401','A-401','Trống','A','LP08','Nam',4),('A402','A-402','Trống','A','LP08','Nam',4),('A403','A-403','Trống','A','LP08','Nam',4),('A404','A-404','Trống','A','LP08','Nam',4),('A405','A-405','Trống','A','LP08','Nam',4),('A406','A-406','Trống','A','LP08','Nam',4),('A407','A-407','Trống','A','LP08','Nam',4),('A408','A-408','Trống','A','LP08','Nam',4),('A409','A-409','Trống','A','LP08','Nam',4),('A410','A-410','Trống','A','LP08','Nam',4),('A411','A-411','Trống','A','LP08','Nam',4),('A412','A-412','Trống','A','LP08','Nam',4),('B101','B-101','Trống','B','LP06','Nữ',1),('B102','B-102','Trống','B','LP06','Nữ',1),('B103','B-103','Trống','B','LP06','Nữ',1),('B104','B-104','Trống','B','LP06','Nữ',1),('B105','B-105','Trống','B','LP06','Nữ',1),('B106','B-106','Trống','B','LP06','Nữ',1),('B107','B-107','Trống','B','LP06','Nữ',1),('B108','B-108','Trống','B','LP06','Nữ',1),('B109','B-109','Trống','B','LP06','Nữ',1),('B110','B-110','Trống','B','LP06','Nữ',1),('B111','B-111','Trống','B','LP06','Nữ',1),('B112','B-112','Trống','B','LP06','Nữ',1),('B201','B-201','Trống','B','LP06','Nữ',2),('B202','B-202','Trống','B','LP06','Nữ',2),('B203','B-203','Trống','B','LP06','Nữ',2),('B204','B-204','Trống','B','LP06','Nữ',2),('B205','B-205','Trống','B','LP06','Nữ',2),('B206','B-206','Trống','B','LP06','Nữ',2),('B207','B-207','Trống','B','LP06','Nữ',2),('B208','B-208','Trống','B','LP06','Nữ',2),('B209','B-209','Trống','B','LP06','Nữ',2),('B210','B-210','Trống','B','LP06','Nữ',2),('B211','B-211','Trống','B','LP06','Nữ',2),('B212','B-212','Trống','B','LP06','Nữ',2),('B301','B-301','Còn trống','B','LP06','Nam',3),('B302','B-302','Trống','B','LP06','Nam',3),('B303','B-303','Trống','B','LP06','Nam',3),('B304','B-304','Trống','B','LP06','Nam',3),('B305','B-305','Trống','B','LP06','Nam',3),('B306','B-306','Trống','B','LP06','Nam',3),('B307','B-307','Trống','B','LP06','Nam',3),('B308','B-308','Trống','B','LP06','Nam',3),('B309','B-309','Trống','B','LP06','Nam',3),('B310','B-310','Trống','B','LP06','Nam',3),('B311','B-311','Trống','B','LP06','Nam',3),('B312','B-312','Trống','B','LP06','Nam',3),('B401','B-401','Trống','B','LP06','Nam',4),('B402','B-402','Trống','B','LP06','Nam',4),('B403','B-403','Trống','B','LP06','Nam',4),('B404','B-404','Trống','B','LP06','Nam',4),('B405','B-405','Trống','B','LP06','Nam',4),('B406','B-406','Trống','B','LP06','Nam',4),('B407','B-407','Trống','B','LP06','Nam',4),('B408','B-408','Trống','B','LP06','Nam',4),('B409','B-409','Trống','B','LP06','Nam',4),('B410','B-410','Trống','B','LP06','Nam',4),('B411','B-411','Trống','B','LP06','Nam',4),('B412','B-412','Trống','B','LP06','Nam',4),('C101','C-101','Trống','C','LP04','Nữ',1),('C102','C-102','Trống','C','LP04','Nữ',1),('C103','C-103','Trống','C','LP04','Nữ',1),('C104','C-104','Trống','C','LP04','Nữ',1),('C105','C-105','Trống','C','LP04','Nữ',1),('C106','C-106','Trống','C','LP04','Nữ',1),('C107','C-107','Trống','C','LP04','Nữ',1),('C108','C-108','Trống','C','LP04','Nữ',1),('C109','C-109','Trống','C','LP04','Nữ',1),('C110','C-110','Trống','C','LP04','Nữ',1),('C111','C-111','Trống','C','LP04','Nữ',1),('C112','C-112','Trống','C','LP04','Nữ',1),('C201','C-201','Trống','C','LP04','Nữ',2),('C202','C-202','Trống','C','LP04','Nữ',2),('C203','C-203','Trống','C','LP04','Nữ',2),('C204','C-204','Trống','C','LP04','Nữ',2),('C205','C-205','Trống','C','LP04','Nữ',2),('C206','C-206','Trống','C','LP04','Nữ',2),('C207','C-207','Trống','C','LP04','Nữ',2),('C208','C-208','Trống','C','LP04','Nữ',2),('C209','C-209','Trống','C','LP04','Nữ',2),('C210','C-210','Trống','C','LP04','Nữ',2),('C211','C-211','Trống','C','LP04','Nữ',2),('C212','C-212','Trống','C','LP04','Nữ',2),('C301','C-301','Đang ở','C','LP04','Nam',3),('C302','C-302','Trống','C','LP04','Nam',3),('C303','C-303','Trống','C','LP04','Nam',3),('C304','C-304','Trống','C','LP04','Nam',3),('C305','C-305','Trống','C','LP04','Nam',3),('C306','C-306','Trống','C','LP04','Nam',3),('C307','C-307','Trống','C','LP04','Nam',3),('C308','C-308','Trống','C','LP04','Nam',3),('C309','C-309','Trống','C','LP04','Nam',3),('C310','C-310','Trống','C','LP04','Nam',3),('C311','C-311','Trống','C','LP04','Nam',3),('C312','C-312','Trống','C','LP04','Nam',3),('C401','C-401','Trống','C','LP04','Nam',4),('C402','C-402','Trống','C','LP04','Nam',4),('C403','C-403','Trống','C','LP04','Nam',4),('C404','C-404','Trống','C','LP04','Nam',4),('C405','C-405','Trống','C','LP04','Nam',4),('C406','C-406','Trống','C','LP04','Nam',4),('C407','C-407','Trống','C','LP04','Nam',4),('C408','C-408','Trống','C','LP04','Nam',4),('C409','C-409','Trống','C','LP04','Nam',4),('C410','C-410','Trống','C','LP04','Nam',4),('C411','C-411','Trống','C','LP04','Nam',4),('C412','C-412','Trống','C','LP04','Nam',4);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinh_vien`
--

DROP TABLE IF EXISTS `sinh_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinh_vien` (
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cccd` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dien_uu_tien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gioi_tinh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `khoa` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mat_khau` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam_hoc` int DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `que_quan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anh_the_sinh_vien` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dia_chi` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ho_ten_phu_huynh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sdt_phu_huynh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `truong_dai_hoc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`masv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinh_vien`
--

LOCK TABLES `sinh_vien` WRITE;
/*!40000 ALTER TABLE `sinh_vien` DISABLE KEYS */;
INSERT INTO `sinh_vien` VALUES ('SV0001','0123456789','Không ưu tiên','Nam','Nguyễn Duy Quang','Công nghệ thông tin','$2a$10$SRiNJw9QPhE9cLcMuPSWU.fdbjEEA50LPehgykLbXSiUw5bPEcms2',1,'2005-04-22','xã Đăk Mar, tỉnh Quảng Ngãi','0378869290','duyquang22042005@gmail.com','/uploads/the_sv_9707c.png',NULL,NULL,NULL,'Hoạt động','ĐH Công nghệ Kỹ thuật '),('SV0002','01234567877','','Nam','Nguyễn Quang Duy','Điện','$2a$10$Q5QPYm7579D0QmaEIGRHHOwU9GHXbZt7.tFuJWC8T8RClSHF5HI8i',2026,'2005-04-23','xã Đăk Mar, tỉnh Quảng Ngãi','0378869291','quangduy22042005@gmail.com',NULL,'','Nguyễn Bố ( Bố )','0389966916','Hoạt động','ĐH Công nghệ Kỹ thuật');
/*!40000 ALTER TABLE `sinh_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tam_vang`
--

DROP TABLE IF EXISTS `tam_vang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tam_vang` (
  `ma_tam_vang` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `den_ngay` date DEFAULT NULL,
  `ly_do` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_dang_ky` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tu_ngay` date DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_tam_vang`),
  KEY `FKoci8ikwvyx41wkxdu72v8vd8c` (`ma_phong`),
  KEY `FKl3vltdq700bccdnrwgy220y33` (`masv`),
  CONSTRAINT `FKl3vltdq700bccdnrwgy220y33` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FKoci8ikwvyx41wkxdu72v8vd8c` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tam_vang`
--

LOCK TABLES `tam_vang` WRITE;
/*!40000 ALTER TABLE `tam_vang` DISABLE KEYS */;
/*!40000 ALTER TABLE `tam_vang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thong_bao`
--

DROP TABLE IF EXISTS `thong_bao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thong_bao` (
  `ma_thong_bao` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `da_doc` bit(1) NOT NULL,
  `link` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `loai_thong_bao` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `nguoi_nhan` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `noi_dung` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `tieu_de` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ma_thong_bao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thong_bao`
--

LOCK TABLES `thong_bao` WRITE;
/*!40000 ALTER TABLE `thong_bao` DISABLE KEYS */;
INSERT INTO `thong_bao` VALUES ('1170802c-2de7-436a-947f-211c77070e9b',_binary '\0','/sinhvien/dashboard','Chat','2026-10-08 15:53:57.856185','SV0002','Chào emDuy','Ban Quản Sinh đã phản hồi tin nhắn'),('16085525-ea05-499e-b17f-e1d2be7b4a52',_binary '\0','/quansinh/chat?maSV=SV0001','Chat','2026-10-08 15:49:49.480961','ROLE_QUAN_SINH','Em chào thầy','Tin nhắn mới từ Nguyễn Duy Quang'),('33d31c65-9258-4e45-bdf4-645349dc46e2',_binary '','/sinhvien/phong','DangKy','2026-10-08 11:50:07.078610','SV0001','Bạn đã được phân vào phòng 301 (Khu A).','Đơn đăng ký phòng được duyệt'),('389d44f4-babe-48f5-87e7-dd9f149dce31',_binary '\0','/quansinh/chat?maSV=SV0001','Chat','2026-10-08 16:43:23.918252','ROLE_QUAN_SINH','em chào thầy','Tin nhắn mới từ Nguyễn Duy Quang'),('393538c0-7b8a-4284-a863-94e13fd880d4',_binary '\0','/sinhvien/hoa-don','HoaDon','2026-10-08 15:12:09.807637','SV0002','Bạn có hóa đơn tháng 1/2026 cần thanh toán.','Hóa đơn mới'),('49246ab9-fdd9-416d-aba3-aa3592dc1a41',_binary '','/sinhvien/phong','DangKy','2026-10-08 11:53:17.884359','SV0001','Bạn đã được phân vào phòng 301 (Khu B).','Đơn đăng ký phòng được duyệt'),('98ded414-676e-4416-a38b-44238193086d',_binary '\0','/sinhvien/dashboard','Chat','2026-10-08 16:43:40.211867','SV0001','Chào em','Ban Quản Sinh đã phản hồi tin nhắn'),('9a2e5f88-af39-4912-938a-52f50c1e3653',_binary '','/sinhvien/sua-chua','SuaChua','2026-10-08 14:56:41.979045','SV0001','Yêu cầu của bạn đã được giao cho nhân viên xử lý.','Yêu cầu sửa chữa đã được tiếp nhận'),('9f917b13-4648-484d-96a6-313f3424c5ca',_binary '','/quansinh/sua-chua','SuaChua','2026-10-08 14:59:15.339339','ROLE_QUAN_SINH','Phòng C-301 đã sửa chữa xong.','Nhân viên đã hoàn thành sửa chữa'),('a3e34496-c1c7-4bfb-8925-dfc140c74e35',_binary '\0','/quansinh/sua-chua','SuaChua','2026-10-08 14:55:05.434971','ROLE_QUAN_SINH','Có một yêu cầu sửa chữa mới từ phòng C-301','Yêu cầu sửa chữa mới'),('b47df81d-2a84-4f87-a15e-86c872fe1910',_binary '\0','/nhanvien/sua-chua','SuaChua','2026-10-08 14:56:41.969361','nhanvien','Phòng C-301: Hư đèn','Bạn được giao việc sửa chữa mới'),('ca74b9bd-c795-4ce1-b65a-cb8092b0c5f8',_binary '\0','/sinhvien/hoa-don','HoaDon','2026-10-08 15:12:09.828628','SV0001','Bạn có hóa đơn tháng 1/2026 cần thanh toán.','Hóa đơn mới'),('cc562525-a4a2-4716-9e5f-ae1c622a1b2a',_binary '\0','/sinhvien/phong','DangKy','2026-10-08 15:07:05.573830','SV0002','Bạn đã được phân vào phòng C-301 (Khu C).','Đơn đăng ký phòng được duyệt'),('d2ef530b-77ca-41f2-8565-2e782e8e922b',_binary '','/sinhvien/phong','DangKy','2026-10-08 13:08:21.779184','SV0001','Bạn đã được phân vào phòng C-301 (Khu C).','Đơn đăng ký phòng được duyệt'),('d46a8fa6-2dde-467e-a45e-1e5dd8d7f1c0',_binary '\0','/quansinh/chat?maSV=SV0002','Chat','2026-10-08 15:50:10.096156','ROLE_QUAN_SINH','Em chào thầy','Tin nhắn mới từ Nguyễn Quang Duy'),('d5a185f4-3b71-453c-ac53-90737c4a3b04',_binary '','/sinhvien/sua-chua','SuaChua','2026-10-08 14:59:15.330065','SV0001','Yêu cầu sửa chữa phòng C-301 đã được xử lý xong.','Yêu cầu sửa chữa đã hoàn thành'),('dc690e42-481f-4e83-8b99-666a4d2f44b0',_binary '\0','/sinhvien/dashboard','Chat','2026-10-08 15:54:03.604767','SV0001','Chào em Quang','Ban Quản Sinh đã phản hồi tin nhắn');
/*!40000 ALTER TABLE `thong_bao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tin_nhan`
--

DROP TABLE IF EXISTS `tin_nhan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tin_nhan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `da_doc` bit(1) DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nguoi_gui` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `noi_dung` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_nguoi_gui` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thoi_gian` datetime(6) DEFAULT NULL,
  `vai_tro_gui` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tin_nhan`
--

LOCK TABLES `tin_nhan` WRITE;
/*!40000 ALTER TABLE `tin_nhan` DISABLE KEYS */;
INSERT INTO `tin_nhan` VALUES (9,_binary '','SV0001','SV0001','Em chào thầy','Nguyễn Duy Quang','2026-10-08 15:49:49.461340','SINH_VIEN'),(10,_binary '','SV0002','SV0002','Em chào thầy','Nguyễn Quang Duy','2026-10-08 15:50:10.096156','SINH_VIEN'),(11,_binary '\0','SV0002','quansinh','Chào emDuy','Ban Quản Sinh KTX','2026-10-08 15:53:57.856185','QUAN_SINH'),(12,_binary '','SV0001','quansinh','Chào em Quang','Ban Quản Sinh KTX','2026-10-08 15:54:03.602563','QUAN_SINH'),(13,_binary '','SV0001','SV0001','em chào thầy','Nguyễn Duy Quang','2026-10-08 16:43:23.787225','SINH_VIEN'),(14,_binary '\0','SV0001','QUAN_SINH','Chào em','Ban Quản Sinh KTX','2026-10-08 16:43:40.210377','QUAN_SINH');
/*!40000 ALTER TABLE `tin_nhan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tra_phong`
--

DROP TABLE IF EXISTS `tra_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tra_phong` (
  `ma_tra_phong` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ket_qua_kiem_ke` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ly_do` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_tra` date DEFAULT NULL,
  `ngay_yeu_cau` date DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phan_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_tra_phong`),
  KEY `FKg0ygq3eho23ucm92lcc3nykbt` (`ma_phan_phong`),
  CONSTRAINT `FKg0ygq3eho23ucm92lcc3nykbt` FOREIGN KEY (`ma_phan_phong`) REFERENCES `phan_phong` (`ma_phan_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tra_phong`
--

LOCK TABLES `tra_phong` WRITE;
/*!40000 ALTER TABLE `tra_phong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tra_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vi_pham`
--

DROP TABLE IF EXISTS `vi_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vi_pham` (
  `ma_vi_pham` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dia_diem` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hinh_thuc_xu_ly` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_lap_bien_ban` date DEFAULT NULL,
  `ngay_vi_pham` date DEFAULT NULL,
  `noi_dung` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai_xu_ly` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_vi_pham`),
  KEY `FKfvl4jr8ddtpe4yt4roohov11q` (`masv`),
  CONSTRAINT `FKfvl4jr8ddtpe4yt4roohov11q` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vi_pham`
--

LOCK TABLES `vi_pham` WRITE;
/*!40000 ALTER TABLE `vi_pham` DISABLE KEYS */;
/*!40000 ALTER TABLE `vi_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yeu_cau_sua_chua`
--

DROP TABLE IF EXISTS `yeu_cau_sua_chua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `yeu_cau_sua_chua` (
  `ma_yeu_cau` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hinh_anh` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_gui` datetime(6) DEFAULT NULL,
  `noi_dung_su_co` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_phong` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `masv` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ma_yeu_cau`),
  KEY `FK9eajwoukoeyvs09sjt4g1rj95` (`ma_phong`),
  KEY `FK1lhkwgeq2t1ewtnppy29jslis` (`masv`),
  CONSTRAINT `FK1lhkwgeq2t1ewtnppy29jslis` FOREIGN KEY (`masv`) REFERENCES `sinh_vien` (`masv`),
  CONSTRAINT `FK9eajwoukoeyvs09sjt4g1rj95` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yeu_cau_sua_chua`
--

LOCK TABLES `yeu_cau_sua_chua` WRITE;
/*!40000 ALTER TABLE `yeu_cau_sua_chua` DISABLE KEYS */;
INSERT INTO `yeu_cau_sua_chua` VALUES ('3a5b431c-3e38-4122-90d4-9934d0993c64','/uploads/8016d7ca-5389-4231-a3e9-bc79e5ea7c69_ph__ki_n.jpg','2026-10-08 14:55:05.343563','Hư đèn','Đã hoàn thành','C301','SV0001');
/*!40000 ALTER TABLE `yeu_cau_sua_chua` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-09  0:29:14
