Proyecto para la gestión de un parking.


En java:

Clase App:
    - Clase principal que gestiona todo.
        Implementa funcionalidades como:
            - Registrar Cliente 
            - Dar de baja cliente  
            - Registrar  Vehículo
            - Dar de baja Vehículo
            - Crear ticket 
            - Borrar ticket 
            - Modificar ticket
            - Encontrar vehiculo por matrícula
            - Consultar plaza
            - Mostrar todos del garaje 

Clase Vehiculo:
    - Constructor de vehiculo

Clase Coche:
    - Constructor de coche que hereda de vehiculo.

Clase Moto:
    - Constructor de mote que hereda de vehiculo.

Clase Ticket:
    - Constructor de ticket

Clase TicketDAO:
    - Clase dao con métodos para la gestión de tickets


En Sql:

Parking:
    - Creación de las tablas y de la carga de datos.

FuncionesSql:
    - Implementa procedimientos, funciones, vistas y consultas.
        Implementa funcionalidades como:
            - agregarVehiculo : procedimiento para agregar un vehículo.
            - agregarTicket :  procedimiento para agregar un ticket.
            - actualizarTicket : procedimiento para actualizar la fecha de entrada y salida de un ticket.
            - CalcularDiferenciaHoras : funcion para contar las horas que un vehículo está en el parking.
            - vehiculosParking : vista para mostrar los coches en el parking.
            - vehiculosClientes : vista para mostrar los clientes y sus vehiculos.
            - 3 consultas:
                1º consulta para ver el historial de un vehículo
                2º consulta para obtener el total de ingresos
                3º consulta par obtener datos de clientes que tiene vehiculos aparcados actualmente

