drop database if exists Parking;
create database Parking character set utf8mb4;
use Parking;

create table Vehiculo (
    Matricula varchar(10) primary key,
    Marca varchar(50),
    Modelo varchar(50),
    Color varchar(20)
);

create table Coche (
    Matricula varchar(10) primary key,
    TipoCoche varchar(50),
    foreign key (Matricula) references Vehiculo(Matricula)
);

create table Moto (
    Matricula varchar(10) primary key,
    TipoMoto varchar(50),
    foreign key (Matricula) references Vehiculo(Matricula)
);

create table Cliente (
    ClienteID int auto_increment primary key,
    Nombre varchar(50),
    Apellido varchar(50),
    Telefono varchar(15),
    Email varchar(50)
);

create table Plaza (
    PlazaID int auto_increment primary key,
    Ubicacion varchar(100),
    Tipo enum('Normal', 'Discapacitado', 'VIP', 'Moto')
);

create table Ticket (
    TicketID int auto_increment primary key,
    Matricula varchar(10),
    ClienteID int,
    PlazaID int,
    FechaEntrada datetime,
    FechaSalida datetime,
    Precio decimal(10, 2),
    foreign key (Matricula) references Vehiculo(Matricula),
    foreign key (ClienteID) references Cliente(ClienteID),
    foreign key (PlazaID) references Plaza(PlazaID)
);

insert into Vehiculo (Matricula, Marca, Modelo, Color) values
('ABC1234', 'Toyota', 'Corolla', 'Rojo'),
('DEF5678', 'Honda', 'Civic', 'Azul'),
('GHI9012', 'Yamaha', 'R1', 'Negro'),
('JKL3456', 'Suzuki', 'GSX-R750', 'Blanco'),
('MNO7890', 'Ford', 'Focus', 'Gris'),
('PQR2345', 'Chevrolet', 'Malibu', 'Verde'),
('STU6789', 'BMW', 'X5', 'Negro'),
('VWX1234', 'Audi', 'A4', 'Blanco'),
('YZA5678', 'Kawasaki', 'Ninja', 'Verde'),
('BCD8901', 'Ducati', 'Panigale', 'Rojo');

insert into Coche (Matricula, TipoCoche) values
('ABC1234', 'Sedán'),
('DEF5678', 'Compacto'),
('MNO7890', 'Hatchback'),
('PQR2345', 'Sedán'),
('STU6789', 'SUV'),
('VWX1234', 'Sedán');

insert into Moto (Matricula, TipoMoto) values
('GHI9012', 'Deportiva'),
('JKL3456', 'Deportiva'),
('YZA5678', 'Deportiva'),
('BCD8901', 'Deportiva');

insert into Cliente (Nombre, Apellido, Telefono, Email) values
('Juan', 'Pérez', '123456789', 'juan.perez@example.com'),
('María', 'González', '987654321', 'maria.gonzalez@example.com'),
('Carlos', 'López', '456123789', 'carlos.lopez@example.com'),
('Ana', 'Martínez', '321654987', 'ana.martinez@example.com'),
('Luis', 'Ramírez', '741852963', 'luis.ramirez@example.com'),
('Elena', 'Torres', '369258147', 'elena.torres@example.com');

insert into Plaza (Ubicacion, Tipo) values
('Nivel 1 - A1', 'Normal'),
('Nivel 1 - A2', 'Discapacitado'),
('Nivel 2 - B1', 'VIP'),
('Nivel 2 - B2', 'Moto'),
('Nivel 3 - C1', 'Normal'),
('Nivel 3 - C2', 'Normal'),
('Nivel 4 - D1', 'Discapacitado'),
('Nivel 4 - D2', 'VIP'),
('Nivel 5 - E1', 'Moto'),
('Nivel 5 - E2', 'Moto');


insert into Ticket (Matricula, ClienteID, PlazaID, FechaEntrada, FechaSalida, Precio) values
('ABC1234', 1, 1, '2024-05-20 08:00:00', null, 0.00),
('DEF5678', 2, 2, '2024-05-20 09:00:00', null, 0.00),
('GHI9012', 3, 4, '2024-05-20 08:30:00', null, 0.00),
('JKL3456', 4, 4, '2024-05-20 09:30:00', null, 0.00),
('MNO7890', 5, 3, '2024-05-19 08:00:00', '2024-05-19 18:00:00', 15.00),
('PQR2345', 6, 5, '2024-05-19 09:00:00', '2024-05-19 17:00:00', 12.00),
('STU6789', 1, 6, '2024-05-18 10:00:00', '2024-05-18 20:00:00', 20.00),
('VWX1234', 2, 7, '2024-05-18 11:00:00', '2024-05-18 19:00:00', 18.00),
('YZA5678', 3, 10, '2024-05-17 12:00:00', '2024-05-17 22:00:00', 10.00),
('BCD8901', 4, 9, '2024-05-17 13:00:00', '2024-05-17 21:00:00', 8.00);
/*
select * from Vehiculo;
select * from Coche;
select * from Moto;
select * from Cliente;
select * from Plaza;
select * from Ticket;*/

