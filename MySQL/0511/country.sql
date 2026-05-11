SELECT * FROM world.country;

use world;

SELECT Name,IndepYear FROM world.country  where IndepYear  is null;

SELECT Name,IndepYear FROM world.country  where IndepYear  <=> null;

SELECT Name,IndepYear FROM world.country  where IndepYear  is not null;

SELECT Population as 人口數 ,Population*2 as '2倍人口數' from world.country;
--  AS 別名
SELECT Population as 'SELECT' ,Population*2  NEW_POP from world.country;

SELECT  7 MOD 3
select 7%3 ;
select 7/3;
SELECT 7 div 3;
SELECT POPulation , POPulation *2 from world.country;

SELECT * FROM world.country WHERE Code='USA';
SELECT * FROM world.country WHERE Population>=1000000;