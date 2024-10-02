create table if not exists _user (
    id bigint not null auto_increment,
    username varchar(100) not null,
    email varchar(300) not null,
    password varchar(1000) not null,
    primary key (id)
);

create table if not exists learning_kit (
    id bigint not null auto_increment,
    title varchar(100) not null,
    owner_id bigint not null,
    primary key (id),
    foreign key (owner_id) references _user(id)
);

create table if not exists flashcard (
    id bigint not null auto_increment,
    name varchar(50) not null,
    definition varchar(500) not null,
    learning_kit_id bigint not null,
    primary key (id),
    foreign key (learning_kit_id) references learning_kit(id)
);
