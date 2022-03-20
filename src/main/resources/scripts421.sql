alter table student
    add constraint age_constraint check ( age > 16 ),
    add unique (name),
    alter column age set default 20;
alter table faculty
    add constraint name_color_unique unique (name, color);



