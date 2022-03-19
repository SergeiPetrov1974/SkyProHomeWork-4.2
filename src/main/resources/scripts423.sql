select student.name, student.age, faculty.name
from student
         full join faculty on student.faculty_id = faculty.id;
select student.name
from student
         inner join avatar a on student.id = a.student_id;