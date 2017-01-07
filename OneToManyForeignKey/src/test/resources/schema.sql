--this is the schema for FK one to many relationships

DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;

DROP TABLE IF EXISTS super_alias;
DROP TABLE IF EXISTS super_hero;

CREATE TABLE super_hero (
	hero_id serial primary key,
	name varchar(100)
);

CREATE TABLE super_alias (
	alias_id serial primary key,
	alias_name varchar(100),
	time_started_using date,
	time_stopped_using date,
	hero_id integer not null,
	CONSTRAINT fk_hero_id FOREIGN KEY (hero_id) REFERENCES super_hero (hero_id)
);

