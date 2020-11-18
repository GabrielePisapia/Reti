create schema tripadvisor;
use tripadvisor;

create table Ristorante (
	idRistorante int not null auto_increment,
    Nome varchar(100) not null,
    Citta varchar(50) not null,
    Cucina varchar(4000) not null,
    Rate double,
    primary key (idRistorante)
);

create table Recensione (
	Titolo varchar(3000) not null,
    Corpo varchar(5000) not null,
    UserLocation varchar(300) not null,
    Ratereview double,
	RistoranteId int not null,
    foreign key (RistoranteId)
    references tripadvisor.Ristorante(idRistorante)
    on delete no action
    on update no action,
    primary key (RistoranteId)
);
