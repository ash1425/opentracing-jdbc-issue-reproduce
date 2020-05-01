
Run oracle XE container
``
docker run -d -p 1521:1521 wnameless/oracle-xe-11g-r2:latest
``

Create table
``
create table person (id number(10,0) not null, age number(10,0) not null, city varchar2(255 char), name varchar2(255 char), primary key (id))
``

