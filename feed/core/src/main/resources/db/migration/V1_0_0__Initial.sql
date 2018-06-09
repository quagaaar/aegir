create table source (
    id bigint auto_increment,
    source_id varchar(255) not null,
    title varchar(255) not null,
    subtitle varchar(255),
    link varchar(255),
    logo varchar(255),
    primary key (id)
);

alter table source add constraint u_source_id unique (source_id);

create table author (
    id bigint auto_increment,
    name varchar(255) not null,
    email varchar(255),
    uri varchar(255),
    primary key (id)
);

alter table author add constraint u_name unique (name);


create table entry (
    id bigint auto_increment,
    entry_id varchar(255) not null,
    title varchar(255) not null,
    summary varchar(2048) not null,
    link varchar(255) not null,
    published timestamp,
    updated timestamp,
    copyright varchar(255),
    source_id bigint not null,
    author_id bigint not null,
    primary key (id)
);

alter table entry add constraint u_entry_id unique (entry_id);
alter table entry add constraint f_source_id foreign key (source_id) references source on delete cascade;
alter table entry add constraint f_author_id foreign key (author_id) references author on delete cascade;
