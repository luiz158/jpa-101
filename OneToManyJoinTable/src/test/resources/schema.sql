DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence;

DROP TABLE IF EXISTS hero_alias;
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
	time_stopped_using date
);

CREATE TABLE hero_alias (
  hero_id integer NOT NULL,
  alias_id integer NOT NULL,
  CONSTRAINT fk_hero_id FOREIGN KEY (hero_id) REFERENCES super_hero (hero_id),
  CONSTRAINT fk_alias_id FOREIGN KEY (alias_id) REFERENCES super_alias (alias_id)
);

