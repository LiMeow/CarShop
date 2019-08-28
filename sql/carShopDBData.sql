
insert into car (model,picture, price, production,available) values ('Mercedes Benz 300SL','/img/cars/Mercedes-Benz-300SL.jpg',21300000,1955,true);
insert into car (model,picture, price, production,available) values ('Ford Mustang Boss 429','/img/cars/Ford-Mustang-Boss-429.jpg',19983300 ,1969,true);
insert into car (model,picture, price, production,available) values ('Toyota Crown Coupe','/img/cars/ToyotaCrownCoupe.jpg',850000,1974,true);
insert into car (model,picture, price, production,available) values ('Audi SQ8','/img/cars/AudiSQ8.jpg',12000000,2019,true);
insert into car (model,picture, price, production,available) values ('BMW X2','/img/cars/BMW-X2.jpg',28000000,2019,true);
insert into car (model,picture, price, production,available) values ('Jaguar I Pace','/img/cars/Jaguar-I-Pace.jpg',6246000 ,2018,true);
insert into car (model,picture, price, production,available) values ('BMW i8','/img/cars/BMWi8.jpg',28000000,2017,true);
insert into car (model,picture, price, production,available) values ('Mercedes-AMG G63','/img/cars/Mercedes-AMG-G63.jpg',13170000,2019,true);
insert into car (model,picture, price, production,available) values ('Tesla S','/img/cars/TeslaS.jpg',3500000,2017,true);


insert into customer (name,phone) values ('Anna','123');
insert into customer (name,phone) values ('Andrew','124');
insert into customer (name,phone) values ('Alen','789');
insert into customer (name,phone) values ('Mia','234');
insert into customer (name,phone) values ('Moris','090');
insert into customer (name,phone) values ('Milena','129');
insert into customer (name,phone) values ('Bob','090');
insert into customer (name,phone) values ('Riko','129');

insert into transaction (car_id,customer_id,manager_id) values (1,1,1);
insert into transaction (car_id,customer_id,manager_id) values (2,2,1);
insert into transaction (car_id,customer_id,manager_id) values (3,3,1);
insert into transaction (car_id,customer_id,manager_id) values (4,4,1);
insert into transaction (car_id,customer_id,manager_id) values (5,5,1);
insert into transaction (car_id,customer_id,manager_id) values (6,6,1);
insert into transaction (car_id,customer_id,manager_id) values (7,7,1);
insert into transaction (car_id,customer_id,manager_id) values (8,8,1);

insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-03-15 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 1);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-04-25 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 2);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-15 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 3);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-25 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 4);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-05 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 5);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-10 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 6);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-15 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 7);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-07-12 20:38:40' , 'APPLICATION_CONFIRMATION', 'Pending application confirmation', 8);

insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-04-29 20:38:40' , 'REJECTED', 'Application confirmed', 2);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-10 20:38:40' , 'REJECTED', 'Application confirmed', 5);

insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-03-19 20:38:40' , 'CONFIRMED', 'Application confirmed', 1);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-19 20:38:40' , 'CONFIRMED', 'Application confirmed', 3);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-05-28 20:38:40' , 'CONFIRMED', 'Application confirmed', 4);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-06-21 20:38:40' , 'CONFIRMED', 'Application confirmed', 6);
insert into transaction_status (date, status_name, description, transaction_id) values (timestamp '2019-07-19 20:38:40' , 'CONFIRMED', 'Application confirmed', 8);



