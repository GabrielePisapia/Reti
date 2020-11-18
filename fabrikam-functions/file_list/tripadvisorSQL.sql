create schema tripadvisor;
use tripadvisor;

create table Ristorante (
    Nome varchar(100) not null,
    Citta varchar(50) not null,
    Cucina varchar(4000) not null,
    Rate double,
    primary key (Nome)
);

create table Recensione (
	Titolo varchar(3000) not null,
    Corpo varchar(5000) not null,
    UserLocation varchar(300) not null,
    Ratereview double,
	NomeRistorante varchar(100) not null,
    foreign key (NomeRistorante)
    references tripadvisor.Ristorante(Nome)
    on delete no action
    on update no action,
    primary key (NomeRistorante)
);
