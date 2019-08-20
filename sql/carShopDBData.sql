
insert into car (model,picture, price, production,available) values ('Audi A8','/img/cars/AudiA8.jpg',6150000,2017,true);
insert into car (model,picture, price, production,available) values ('AlfaRomeo Stelvio','/img/cars/AlfaRomeoStelvio.jpg',3900000 ,2019,true);
insert into car (model,picture, price, production,available) values ('BMW i8','/img/cars/BMWi8.jpg',11490000,2017,true);
insert into car (model,picture, price, production,available) values ('Ford Fusion II','/img/cars/FordFusionII.jpg',640000,2018,true);
insert into car (model,picture, price, production,available) values ('Hummer H3','/img/cars/HummerH3.jpg',1000000,2017,true);
insert into car (model,picture, price, production,available) values ('Mercedes-AMG G63','/img/cars/Mercedes-AMG G63.jpg',13170000,2019,true);
insert into car (model,picture, price, production,available) values ('Tesla S','/img/cars/TeslaS.jpg',3500000,2017,true);
insert into car (model,picture, price, production,available) values ('Toyota Crown Coupe','/img/cars/ToyotaCrownCoupe.jpg',850000,1974,true);

insert into customer (name,phone) values ('Anna','123');
insert into customer (name,phone) values ('Andrew','124');
insert into customer (name,phone) values ('Alen','789');
insert into customer (name,phone) values ('Mia','234');
insert into customer (name,phone) values ('Moris','090');
insert into customer (name,phone) values ('Milena','129');

insert into transaction (car_id,customer_id,manager_id) values (1,1,1);
insert into transaction (car_id,customer_id,manager_id) values (2,2,1);
insert into transaction (car_id,customer_id,manager_id) values (3,3,1);
insert into transaction (car_id,customer_id,manager_id) values (4,4,1);
insert into transaction (car_id,customer_id,manager_id) values (5,5,1);
insert into transaction (car_id,customer_id,manager_id) values (6,6,1);


insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-15 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 1);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-19 20:38:40' , 'CONFIRMED', 'Application confirmed', 1);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-25 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 2);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-05 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 3);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-15 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 4);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-21 20:38:40' , 'CONFIRMED', 'Application confirmed', 4);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-10 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 5);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-07-12 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 6);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-07-19 20:38:40' , 'CONFIRMED', 'Application confirmed', 6);

