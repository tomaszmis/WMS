--
-- WMS DB INIT SCRIPT
--

CREATE DATABASE IF NOT EXISTS `wms` CHARACTER SET utf8 COLLATE utf8_polish_ci;
USE `wms`;
SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
                             warehouse_id             INT(11) NOT NULL AUTO_INCREMENT,
                             warehouse_owner_id       INT(11) NOT NULL,
                             warehouse_created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             warehouse_name           VARCHAR(40) NOT NULL,
                             warehouse_description    VARCHAR(1000),
                             warehouse_status         TINYINT NOT NULL DEFAULT 1,
                             warehouse_payment_status TINYINT NOT NULL DEFAULT 1,
                             CONSTRAINT warehouse_id_pk PRIMARY KEY(warehouse_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        user_id                 INT(11) NOT NULL AUTO_INCREMENT,
                        user_warehouse_id       INT(11),
                        user_role_id            INT(11) NOT NULL,
                        user_surname            VARCHAR(30),
                        user_name               VARCHAR(20),
                        user_employment_date    TIMESTAMP,
                        user_position           VARCHAR(20),
                        user_salary             INT(11),
                        user_login              VARCHAR(20) NOT NULL,
                        user_email              VARCHAR(50),
                        user_password           VARCHAR(1000) NOT NULL,
                        CONSTRAINT user_id_pk PRIMARY KEY(user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                             ur_id                 INT(11) NOT NULL AUTO_INCREMENT,
                             ur_name               VARCHAR (20),
                             CONSTRAINT ur_id_pk PRIMARY KEY(ur_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
                          client_id                INT(11) NOT NULL AUTO_INCREMENT,
                          client_warehouse_id      INT(11) NOT NULL,
                          client_nip               VARCHAR(11),
                          client_name              VARCHAR(40) NOT NULL,
                          client_address           VARCHAR(50),
                          client_phone             VARCHAR(12),
                          client_email             VARCHAR(50),
                          client_contact_person    VARCHAR(25),
                          CONSTRAINT client_id_pk PRIMARY KEY(client_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
                           product_id               INT(11) NOT NULL AUTO_INCREMENT,
                           product_warehouse_id     INT(11) NOT NULL,
                           product_deliverer_id     INT(11) NOT NULL,
                           product_code             VARCHAR(15) NOT NULL,
                           product_name             VARCHAR(50) NOT NULL,
                           product_brand            VARCHAR(50) NOT NULL,
                           product_category         VARCHAR(30) NOT NULL,
                           product_taxcat_id        INT(11) NOT NULL,
                           product_buy_nett_price   INT(11) NOT NULL,
                           product_sell_nett_price  INT(11) NOT NULL,
                           product_desc             VARCHAR(1000),
                           product_status           TINYINT NOT NULL DEFAULT 1,
                           product_unit             VARCHAR(4),
                           product_optimal_quant    INT(11) NOT NULL,
                           product_minimal_quant    INT(3) NOT NULL DEFAULT 60,
                           CONSTRAINT product_id_pk PRIMARY KEY(product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `client_order`;
CREATE TABLE `client_order` (
                         order_id                  INT(11) NOT NULL AUTO_INCREMENT,
                         order_warehouse_id        INT(11) NOT NULL,
                         order_client_id           INT(11) NOT NULL,
                         order_number              VARCHAR(10) NOT NULL,
                         order_created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         order_annotation          VARCHAR(30),
                         order_status              TINYINT NOT NULL DEFAULT 1,
                         CONSTRAINT order_id_pk PRIMARY KEY(order_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `ordered_products`;
CREATE TABLE `ordered_products` (
                                    op_id                      INT(11) AUTO_INCREMENT,
                                    op_product_id             INT(11) NOT NULL,
                                    op_order_id               INT(11) NOT NULL,
                                    op_amount                 INT(11) NOT NULL,
                                        CONSTRAINT op_id_pk PRIMARY KEY(op_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `warehouse_content`;
CREATE TABLE `warehouse_content` (
                                     wc_warehouse_id           INT(11) NOT NULL,
                                     wc_product_id             INT(11) NOT NULL,
                                     wc_available              INT(11) NOT NULL,
                                     wc_reserved               INT(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `delivery`;
CREATE TABLE `delivery` (
                            delivery_id               INT(11) NOT NULL AUTO_INCREMENT,
                            delivery_warehouse_id     INT(11) NOT NULL,
                            delivery_deliverer_id     INT(11) NOT NULL,
                            delivery_number           VARCHAR(20) NOT NULL,
                            delivery_date             TIMESTAMP NOT NULL,
                            delivery_status           TINYINT NOT NULL DEFAULT 1,
                            CONSTRAINT delivery_id_pk PRIMARY KEY(delivery_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `deliverer`;
CREATE TABLE `deliverer` (
                             deliverer_id              INT(11) NOT NULL AUTO_INCREMENT,
                             deliverer_warehouse_id    INT(11) NOT NULL,
                             deliverer_nip             VARCHAR(11) NOT NULL,
                             deliverer_name            VARCHAR(40) NOT NULL,
                             deliverer_address         VARCHAR(50),
                             deliverer_phone           VARCHAR(12),
                             deliverer_email           VARCHAR(50),
                             deliverer_page            VARCHAR(40),
                             deliverer_contact_person  VARCHAR(40),
                             CONSTRAINT deliverer_id_pk PRIMARY KEY(deliverer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `delivery_products`;
CREATE TABLE `delivery_products` (
                                     dp_id                     INT(11) AUTO_INCREMENT,
                                     dp_warehouse_id           INT(11) NOT NULL,
                                     dp_delivery_id            INT(11) ,
                                     dp_product_id             INT(11) NOT NULL,
                                     dp_quantity               INT(11) NOT NULL,
                                     CONSTRAINT dp_id_pk PRIMARY KEY(dp_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `taxcat`;
CREATE TABLE `taxcat` (
                          taxcat_id                 INT(11) NOT NULL AUTO_INCREMENT,
                          taxcat_name               VARCHAR(50) NOT NULL,
                          taxcat_tax                INT(2) NOT NULL,
                          CONSTRAINT taxcat_id_pk PRIMARY KEY(taxcat_id)
) ENGINE=InnoDB AUTO_INCREMENT=1;


ALTER TABLE user
    ADD CONSTRAINT user_warehouse_fk
        FOREIGN KEY (user_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE user
    ADD CONSTRAINT user_role_fk
        FOREIGN KEY (user_role_id)
            REFERENCES user_role(ur_id);

ALTER TABLE warehouse
    ADD CONSTRAINT warehouse_user_fk
        FOREIGN KEY (warehouse_owner_id)
            REFERENCES user(user_id);

ALTER TABLE client
    ADD CONSTRAINT client_warehouse_fk
        FOREIGN KEY (client_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE product
    ADD CONSTRAINT product_warehouse_fk
        FOREIGN KEY (product_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE product
    ADD CONSTRAINT product_deliverer_fk
        FOREIGN KEY (product_deliverer_id)
            REFERENCES deliverer(deliverer_id);

ALTER TABLE product
    ADD CONSTRAINT product_taxcat_fk
        FOREIGN KEY (product_taxcat_id)
            REFERENCES taxcat(taxcat_id);

ALTER TABLE `client_order`
    ADD CONSTRAINT order_warehouse_fk
        FOREIGN KEY (order_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE `client_order`
    ADD CONSTRAINT order_client_fk
        FOREIGN KEY (order_client_id)
            REFERENCES CLIENT(client_id);

ALTER TABLE ordered_products
    ADD CONSTRAINT op_product_pk
        FOREIGN KEY (op_product_id)
            REFERENCES product(product_id);

ALTER TABLE ordered_products
    ADD CONSTRAINT op_order_fk
        FOREIGN KEY (op_order_id)
            REFERENCES `client_order`(order_id);

ALTER TABLE warehouse_content
    ADD CONSTRAINT wc_warehouse_fk
        FOREIGN KEY (wc_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE warehouse_content
    ADD CONSTRAINT wc_product_fk
        FOREIGN KEY (wc_product_id)
            REFERENCES product(product_id);

ALTER TABLE deliverer
    ADD CONSTRAINT deliverer_warehouse_fk
        FOREIGN KEY (deliverer_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE delivery_products
    ADD CONSTRAINT dp_delivery_fk
        FOREIGN KEY (dp_delivery_id)
            REFERENCES delivery(delivery_id);

ALTER TABLE delivery_products
    ADD CONSTRAINT dp_product_fk
        FOREIGN KEY (dp_product_id)
            REFERENCES product(product_id);


ALTER TABLE delivery
    ADD CONSTRAINT delivery_warehouse_fk
        FOREIGN KEY (delivery_warehouse_id)
            REFERENCES warehouse(warehouse_id);

ALTER TABLE delivery
    ADD CONSTRAINT delivery_deliverer_fk
        FOREIGN KEY (delivery_deliverer_id)
            REFERENCES deliverer(deliverer_id);


LOCK TABLES `user_role` WRITE;
    INSERT INTO `user_role` VALUES (1, 'ADMINISTRATOR');
    INSERT INTO `user_role` VALUES (2, 'OWNER');
    INSERT INTO `user_role` VALUES (3, 'WORKER');
UNLOCK TABLES;

LOCK TABLES `taxcat` WRITE;
    INSERT INTO `taxcat` VALUES (1, 'Stawka podstawowa', 22);
    INSERT INTO `taxcat` VALUES (2, 'Stawka obniżona', 8);
    INSERT INTO `taxcat` VALUES (3, 'Produkty rolne, ksiażki, czasopisma', 5);
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
    INSERT INTO `user`(user_id, user_role_id, user_login, user_password) VALUES (1,1,'administrator','$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju'); -- qwertyuiop
    INSERT INTO `user` VALUES(1244, 1,   2, 'Kowalski',     'Adam',      '2019-06-12 00:00:00', 0, 12000, 'akowalski',    'akowalski@gmail.com',      '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
    INSERT INTO `user` VALUES(1246, 1,   3, 'Niewiadomski', 'Kazimierz', '2019-06-16 00:00:00', 2, 4000,  'kniewiad',     'niewiadomsky@interia.pl',  '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
    INSERT INTO `user` VALUES(5131, 1,   3, 'Żmijewski',    'Krystian',  '2019-09-10 00:00:00', 4, 2000,  'kzmijewski',   'zmija219@gmail.com',       '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
    INSERT INTO `user` VALUES(1535, 1,   3, 'Komarewska',   'Agata',     '2019-06-23 00:00:00', 3, 8000,  'akomarewska',  'a.komarewska@onet.pl',     '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
    INSERT INTO `user` VALUES(1315, 1,   3, 'Olszewski',    'Marian',    '2019-11-18 00:00:00', 4, 2000,  'molszewski',   'molszew12@wp.pl',          '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
    INSERT INTO `user` VALUES(1164, 1,   3, 'Więckiewicz',  'Klaudia',   '2019-11-18 00:00:00', 1, 2000,  'kwieckiewicz', 'kwieciewska@gmail.com',    '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
    INSERT INTO `user` VALUES(5123, 124, 2, 'Niemcewicz',   'Jędrzej',   '2019-11-29 00:00:00', 0, 9000,  'jniemcewicz',  'jeden.niemiec@interia.pl', '$2a$11$bXjN8rHNlf3/EYD0Q5UkKuev7nOYX61X1/ZTt2alhRUPqWCGfYHju');
UNLOCK TABLES;

LOCK TABLES `warehouse` WRITE;
    INSERT INTO `warehouse` VALUES(1, 1244, '2019-06-12 00:00:00', 'Polzbex sp.z o.o.', 'Hurtownia materiałów budowlanych', 1, 1);
    INSERT INTO `warehouse` VALUES(124, 5123, '2019-11-29 00:00:00', 'Budimex', '', 1, 1);
UNLOCK TABLES;

LOCK TABLES `product` WRITE;
    INSERT INTO `product` VALUES(1262, 1, 15, '316148', 'Panele podłogowe Colours Dąb Srebrny AC4 2,22 m2', 'Colorus Panele', 'Panele', 1, 2184, 2618, 'znajdujący się wśród naszego asortymentu panel podłogowy doskonale imituje posadzkę wykonaną z naturalnego drewna dębowego. Jego zaletą jest także nieskomplikowany montaż, który polega na dopasowaniu poszczególnych elementów na zasadzie pióro w szczelinę, zaś wysoka klasa ścieralności sprawia, że produkt odznacza się dużą wytrzymałością i odpornością na zarysowania.', 1, 'm2', 240, 60);
    INSERT INTO `product` VALUES(5613, 1, 244, '922121', 'Schody z zabiegiem Atrium System Dixi', 'Atrium', 'Schody', 1, 389000, 400400, 'Schody Atrium Dixi Plus umożliwiają wygodne użytkowanie oraz nadają nowoczesny wygląd pomieszczeniom, w których są montowane. Przy regularnej konserwacji przez długi czas zachowają nienaganny stan, gdyż wykonane zostały ze stali malowanej proszkowo połączonej z lakierowanymi stopniami drewnianymi. Jednostronna balustrada zagwarantuje Ci poczucie bezpieczeństwa podczas pokonywania stopni. W proponowanym asortymencie znajdziesz również inne rodzaje tego produktu.', 1, 'szt', 2, 0);
    INSERT INTO `product` VALUES(1523, 1, 15, '319104', 'Panele podłogowe GoodHome Otley AC5 1,759 m2', 'GoodHome', 'Panele', 1, 3989, 4543, 'Panel podłogowy GoodHome Otley jest łatwy w montażu – nie wymaga użycia kleju lub specjalistycznych narzędzi. Okładzinę wykonasz samodzielnie, a uzyskany efekt będzie wyglądał niczym drewniany parkiet. Model w ciemnobrązowym kolorze przypomina swoją strukturą naturalny surowiec. Dzięki temu podłoga w Twoim domu będzie nadzwyczajnie atrakcyjna.', 0, 'm2', 120, 30);
    INSERT INTO `product` VALUES(5612, 1, 244, '923164', 'Bloczek komórkowy Ytong 10 x 20 x 60 cm', 'Ytong', 'Cegły, pustaki i bloczki', 1, 398, 487, 'Szukasz trwałych i praktycznych elementów konstrukcyjnych do wznoszenia ścian? Wykorzystaj dostępne w naszych marketach budowlanych bloczki komórkowe Ytong. Bloczek komórkowy Ytong składa się ze starannie dobranej mieszaniny naturalnych surowców. Dzięki temu masz pewność, że bloczki zawsze charakteryzują się tymi samymi właściwościami.', 1, 'szt.',  330, 75);
    INSERT INTO `product` VALUES(5231, 1, 15, '321970', 'Panele podłogowe Dąb Shoreland AC4 1,73 m2', 'Colorus Panele', 'Panele', 1, 1864, 2233, 'Panele podłogowe Dąb Shoreland pomogą Ci w estetycznym i funkcjonalnym wykończeniu podłogi. Mają strukturę drewna i klasyczny design. Wysoka klasa ścieralności gwarantuje im dużą trwałość. Montaż tych paneli nie zajmie Ci wiele czasu. Nie jest także trudny, dlatego poradzisz sobie bez pomocy specjalisty. Z tym produktem szybko przeprowadzisz remont.', 1, 'm2', 220, 40);
UNLOCK TABLES;

LOCK TABLES `client` WRITE;
    INSERT INTO `client` VALUES(86, 1, 1234567890, 'Kleofas Niemcewicz sp. z o.o.', 'Szkolna 24, 34-290 Rowerkowo', '123-456-789', 'k.niemcewicz@firmowisko.pl', 'Kleofas Niemcewicz');
    INSERT INTO `client` VALUES(124, 1, NULL, 'Adrian Zieliński', 'Marmurowa 31b, 31-653 Zabierzów', '214-124-632', 'zielinski.adrian@gmail.com', '');
    INSERT INTO `client` VALUES(513, 1, NULL, 'Marzena Orzechowska', 'Kolejowa 2/21, 24-512 Małe Ciche', '+48 624 836 613', 'morzeoiochowska@gmail.com', '');
    INSERT INTO `client` VALUES(145, 1, 5295156125, 'SamBud sp. z o.o.', 'Agrafkowa 1/5, 31-412 Zamoście', '12 642 34 34','biuro@sambud.pl', 'Ignacy Trzewiczek');
UNLOCK TABLES;

LOCK TABLES `deliverer` WRITE;
    INSERT INTO `deliverer` VALUES(12, 1, '8932414528', 'Atrium spółka akcyjna', 'Orzechowa 1, 32-412 Wielmoża', '12 531 53 13', 'biuro@atriummeble.pl', 'https://www.atriummeble.pl', 'Beata Jałocha');
    INSERT INTO `deliverer` VALUES(244, 1, '7357254724', 'Ytong Bet', 'Kamienna 14, 12-531 Piastów', '12 643 25 67', 'contact@ytongbet.com', 'https://www.orders.ytongbet.com', 'Agnieszka Dudzikowska');
    INSERT INTO `deliverer` VALUES(15, 1, '6436123346', 'Colorplast SA', 'Amarantowa 842, 51-563 Chrzanów', '12 614 67 73', 'zamowienia@colorplast.com', '', 'Antoni Oratowski');
UNLOCK TABLES;

LOCK TABLES `delivery` WRITE;
    INSERT INTO `delivery` VALUES(215, 1, 244, 'YB/D0025135', '2019-11-24 00:00:00', 1);
    INSERT INTO `delivery` VALUES(124, 1, 15, '221119049', '2019-11-22 00:00:00', 0);
UNLOCK TABLES;

LOCK TABLES `client_order` WRITE;
    INSERT INTO `client_order` VALUES(7157, 1, 124, 'ZAM0004', '2019-11-22 00:00:00', '', 1);
    INSERT INTO `client_order` VALUES(8956, 1, 86, 'ZAM0006', '2019-11-23 00:00:00', '', 2);
    INSERT INTO `client_order` VALUES(1325, 1, 86, 'ZAM002', '2019-11-19 00:00:00', '', 2);
    INSERT INTO `client_order` VALUES(5123, 1, 145, 'ZAM032', '2019-11-19 00:00:00', '', 0);
    INSERT INTO `client_order` VALUES(4124, 1, 513, 'ZAM024', '2019-11-19 00:00:00', '', 1);
UNLOCK TABLES;

LOCK TABLES `warehouse_content` WRITE;
    INSERT INTO `warehouse_content` VALUES(1, 1262, 20, 0);
    INSERT INTO `warehouse_content` VALUES(1, 5613, 1, 1);
    INSERT INTO `warehouse_content` VALUES(1, 1523, 120, 0);
    INSERT INTO `warehouse_content` VALUES(1, 5612, 60, 100);
    INSERT INTO `warehouse_content` VALUES(1, 5231, 220, 0);
UNLOCK TABLES;

LOCK TABLES `delivery_products` WRITE;
    INSERT INTO `delivery_products` VALUES(1,215, 5612, 330);
    INSERT INTO `delivery_products` VALUES(2,124, 1262, 240);
UNLOCK TABLES;

LOCK TABLES `ordered_products` WRITE;
    INSERT INTO `ordered_products` VALUES(1,5612, 1325, 120);
    INSERT INTO `ordered_products` VALUES(2,1262, 4124, 165);
    INSERT INTO `ordered_products` VALUES(3,5612, 5123, 200);
    INSERT INTO `ordered_products` VALUES(4,5613, 7157, 1);
    INSERT INTO `ordered_products` VALUES(5,5612, 7157, 100);
    INSERT INTO `ordered_products` VALUES(6,5612, 8956, 80);
    INSERT INTO `ordered_products` VALUES(7,1262, 8956, 120);
UNLOCK TABLES;