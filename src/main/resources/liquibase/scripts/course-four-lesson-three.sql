--liquibase formatted SQL
--changeSet spetrov:1
CREATE INDEX student_name_id ON student (name);
--changeSet spetrov:2
CREATE INDEX faculty_name_color_id ON faculty (color, name);