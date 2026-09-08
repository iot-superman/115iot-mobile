-- SELECT * FROM myapp_students; 
-- Select cID, cName, cAddr from myapp_students;
-- SELECT DISTINCT `cSex` FROM `myapp_students`; 
-- SELECT * FROM `myapp_students` WHERE `cID`=2; 
-- SELECT * FROM `myapp_students` WHERE `cSex`='M'; 
-- SELECT * FROM `myapp_students` WHERE `cID`>5 AND `cSex`='M'; 
-- SELECT * FROM `myapp_students` WHERE `cID`=1 OR `cID`>=8; 
-- SELECT * FROM `myapp_students` WHERE `cID` BETWEEN 4 AND 6; 
SELECT * FROM `myapp_students` WHERE `cID` IN (1,3,5,8,9) 