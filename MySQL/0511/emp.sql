use cmdev;
SELECT * from cmdev.emp;
SELECT * from cmdev.emp WHERE hiredate<'1987-01-01';
SELECT * from cmdev.emp where job !='CLERK';
SELECT * from cmdev.emp where not job ='CLERK';
select * FROM cmdev.emp WHERE job ='SALESMAN' AND salary>1500;

SELECT Name, Continent,Population from World.country
WHERE (Continent='EUROPE' OR Continent='AFRICA') AND Continent<10000;