This is C# .NET web application, using EntityFramework, Code First approach and MySQL server. Allows user to add car rents to database. You can change configuration in Startup.cs in .UseMySQL() method. Without any changes server ip set to localhost, port: 3306, database: cars, user: root, password: root.

![](/WebApp/database.bmp)

Script for database:
```
create database cars;

create table cars.cars(
	IdCar Int not null,
    RegisterNumber NVARCHAR(15) not null,
    Model NVARCHAR(25) not null,
    primary key(idcar)
);

CREATE TABLE IF NOT EXISTS cars.rents (
    IdRent INT AUTO_INCREMENT,
    Description VARCHAR(255) NOT NULL,
    Client NVARCHaR(255) not null,
    DateFrom DATEtime,
    DateTo DATEtime,
    IdCar INT not null,
    PRIMARY KEY (IdRent),
    foreign key(idcar) references car (idcar)
);
```
If you want to insert some cars:

```
INSERT INTO car (IdCar,RegisterNumber,Model)
VALUES (1,"SA9AS8F","Infernus");
INSERT INTO car (IdCar,RegisterNumber,Model)
VALUES (2,"SASJ08F","Panini");
INSERT INTO car (IdCar,RegisterNumber,Model)
VALUES (3,"SA1O68F","Spyro");

```
