create table if not exists todo
(
    id int auto_increment primarykey,
    text varchar(255) null
    done boolean null,
);