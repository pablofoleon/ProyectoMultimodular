use parking;

-- Procedimiento para agregar un vehiculo
DELIMITER //
DROP PROCEDURE IF EXISTS agregarVehiculo;
CREATE PROCEDURE agregarVehiculo (IN pMatricula VARCHAR(10),IN pMarca VARCHAR(50),IN pModelo VARCHAR(50),IN pColor VARCHAR(20)
)
BEGIN
    INSERT INTO Vehiculo (Matricula, Marca, Modelo, Color)
    VALUES (pMatricula, pMarca, pModelo, pColor);
END //
DELIMITER ;
/*
call agregarVehiculo("YYYY","FORD","FOCUS","VERDE");
select * from Vehiculo*/

-- Procedimineto para agregar un ticket
DELIMITER //
DROP PROCEDURE IF EXISTS agregarTicket;
CREATE PROCEDURE agregarTicket (IN pMatricula VARCHAR(10),IN pClienteID INT,IN pPlazaID INT,IN pFechaEntrada DATETIME)
BEGIN
    INSERT INTO Ticket (Matricula, ClienteID, PlazaID, FechaEntrada, FechaSalida, Precio)
    VALUES (pMatricula, pClienteID, pPlazaID, pFechaEntrada, NULL, 0.00);
END //
DELIMITER ;
/*call agregarTicket("YYYY",1,1, "2020-02-02 02:02:02");
select * from Ticket;*/

-- Procedimiento para actualizar la fecha de salida y precio del ticket

DELIMITER //
DROP PROCEDURE IF EXISTS actualizarTicket;
CREATE PROCEDURE actualizarTicket (IN pTicketID INT,IN pFechaSalida DATETIME,IN pPrecio DECIMAL(10, 2)
)
BEGIN
    UPDATE Ticket
    SET FechaSalida = pFechaSalida,
        Precio = pPrecio
    WHERE TicketID = pTicketID;
END //
DELIMITER ;
/*call actualizarTicket(11,"2020-02-02 08:00:02",20);
select * from ticket;*/


-- Funcion para contar las horas pasadas en el parking
DELIMITER //
DROP FUNCTION IF EXISTS CalcularDiferenciaHoras;
CREATE FUNCTION CalcularDiferenciaHoras(ticket_id INT)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE horas_diff INT;

    SELECT TIMESTAMPDIFF(HOUR, FechaEntrada, FechaSalida)
    INTO horas_diff
    FROM Ticket
    WHERE TicketID = ticket_id;

    RETURN horas_diff;
END//

DELIMITER ;

/* SELECT CalcularDiferenciaHoras(5); */


-- VIsta para mostrar los coches en el parking
DROP VIEW IF EXISTS vehiculosParking;
CREATE VIEW vehiculosParking AS
SELECT v.Matricula, v.Marca, v.Modelo, v.Color, t.FechaEntrada, p.Ubicacion
FROM Vehiculo v
JOIN Ticket t ON v.Matricula = t.Matricula
JOIN Plaza p ON t.PlazaID = p.PlazaID
WHERE t.FechaSalida IS NULL;

/*SELECT * FROM vehiculosParking;*/

-- Vista de los clientes y sus vehivulos
DROP VIEW IF EXISTS vehiculosClientes;
CREATE VIEW vehiculosClientes AS
SELECT c.ClienteID, c.Nombre, c.Apellido, c.Telefono, c.Email, v.Matricula, v.Marca, v.Modelo, v.Color
FROM Cliente c
JOIN Ticket t ON c.ClienteID = t.ClienteID
JOIN Vehiculo v ON t.Matricula = v.Matricula;

/*select * from vehiculosClientes;*/


-- Consulta para ver el historial de un vehiculo
/*
SELECT v.Matricula, v.Marca, v.Modelo, v.Color, t.FechaEntrada, t.FechaSalida, t.Precio, p.Ubicacion
FROM Vehiculo v
JOIN Ticket t ON v.Matricula = t.Matricula
JOIN Plaza p ON t.PlazaID = p.PlazaID
WHERE v.Matricula = 'ABC1234';*/

-- Consulta para obtener el total de ingresos

/* SELECT SUM(Precio) AS TotalIngresos FROM Ticket WHERE FechaSalida IS NOT NULL;*/

-- COnsulta para obtener datos de clientes que tienen vehiculos aparcados actualmente
/*
SELECT c.ClienteID, c.Nombre, c.Apellido, c.Telefono, c.Email, v.Matricula, v.Marca, v.Modelo, v.Color, t.FechaEntrada, p.Ubicacion
FROM Cliente c
JOIN Ticket t ON c.ClienteID = t.ClienteID
JOIN Vehiculo v ON t.Matricula = v.Matricula
JOIN Plaza p ON t.PlazaID = p.PlazaID
WHERE t.FechaSalida IS NULL;*/
