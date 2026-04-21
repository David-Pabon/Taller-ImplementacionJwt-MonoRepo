# Entidades

## Tickets
- Va a tener un atributo de ticket category, no es una entidad por si sola
- regla de negocio: no se puede comprar un boleto si no esta dentro del periodo de venta
- regla de negocio: no se puede comprar mas de N tickets por usuario comprador (parametrizable)

atributos:
id
code
price
category
purchase_id
user_id
event_id
purchase_at -- trazabilidad
status

metodos de negocio:
crear ticket
cancelar ticket

esta cancelado?

esta comprado?

a quien pertenece?


## Users
- Tres tipos de usuarios: comprador, organizador y administrador

atributos:
id
name
email
password
role
created_at -- trazabilidad
updated_at -- trazabilidad

metodos de negocio:
crear usuario comprador
crear usuario organizador
crear usuario administrador



## Events

TODO: profe - definir entidad de eventos

## Purchases

TODO: profe - definir entidad de compras