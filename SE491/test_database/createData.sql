CREATE database sdmay22;

create table sdmay22.students (
	netid integer,
    firstName text,
    middleName text, 
    lastName text, 
    major text
);

insert into sdmay22.students (netid, firstName, middleName, lastName, major)
values(1234,"jake","jerome","gudenkauf","SE");
insert into sdmay22.students (netid, firstName, middleName, lastName, major)
values(111,"john","leroy","smith","SE");
insert into sdmay22.students (netid, firstName, middleName, lastName, major)
values(6969,"sam","baby","yoder","SE");