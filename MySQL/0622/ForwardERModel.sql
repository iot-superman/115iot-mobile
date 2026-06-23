-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `company` DEFAULT CHARACTER SET utf8 ;
USE `company` ;

-- -----------------------------------------------------
-- Table `company`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company`.`employee` (
  `emp_no` VARCHAR(3) NOT NULL,
  `emp_name` VARCHAR(45) NULL,
  `dept_no` VARCHAR(3) NULL,
  PRIMARY KEY (`emp_no`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `company`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company`.`department` (
  `dept_no` VARCHAR(3) NOT NULL,
  `dept_name` VARCHAR(45) NULL,
  `dept_no` VARCHAR(3) NULL,
  PRIMARY KEY (`dept_no`),
  INDEX `fk_department_employee_idx` (`dept_no` ASC) VISIBLE,
  CONSTRAINT `fk_department_employee0`
    FOREIGN KEY (`dept_no`)
    REFERENCES `company`.`employee` (`dept_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `company` ;

-- -----------------------------------------------------
--  routine1
-- -----------------------------------------------------

DELIMITER $$
USE `company`$$
$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
