ALTER TABLE `SITE`
	ADD COLUMN `SHOW_SECTION_TEACHERS`  BOOLEAN DEFAULT NULL DEFAULT 1,
	ADD COLUMN `SHOW_SECTION_EMPLOYEES` BOOLEAN DEFAULT NULL DEFAULT 1,
	ADD COLUMN `SHOW_SECTION_COURSES`   BOOLEAN DEFAULT NULL DEFAULT 1,
	ADD COLUMN `SHOW_SECTION_STUDENTS`  BOOLEAN DEFAULT NULL DEFAULT 1,
	ADD COLUMN `SHOW_SECTION_DEGREES`   BOOLEAN DEFAULT NULL DEFAULT 1;

UPDATE `SITE` SET
	`SHOW_SECTION_TEACHERS` = NULL,
	`SHOW_SECTION_EMPLOYEES` = NULL,
	`SHOW_SECTION_COURSES` = NULL,
	`SHOW_SECTION_STUDENTS` = NULL,
	`SHOW_SECTION_DEGREES` = NULL
WHERE `OJB_CONCRETE_CLASS` LIKE "%DegreeSite"
   OR `OJB_CONCRETE_CLASS` LIKE "%ExecutionCourseSite"
   OR `OJB_CONCRETE_CLASS` LIKE "%Homepage"
   OR `OJB_CONCRETE_CLASS` LIKE "%SiteTemplate";