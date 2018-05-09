CREATE SEQUENCE IF NOT EXISTS authority_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


CREATE SEQUENCE IF NOT EXISTS user_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


CREATE TABLE IF NOT EXISTS users
(
  id bigint NOT NULL,
  enabled boolean NOT NULL,
  firstname character varying(50) NOT NULL,
  last_password_reset_date timestamp without time zone NOT NULL,
  lastname character varying(50) NOT NULL,
  password character varying(100) NOT NULL,
  username character varying(50) NOT NULL,
  tenant_id character varying(255),
  CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE IF NOT EXISTS authority
(
  id bigint NOT NULL,
  name character varying(255) NOT NULL,
  CONSTRAINT authority_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE IF NOT EXISTS user_authority
(
  user_id bigint NOT NULL,
  authority_id bigint NOT NULL,
  CONSTRAINT fkgvxjs381k6f48d5d2yi11uh89 FOREIGN KEY (authority_id)
      REFERENCES public.authority (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkhi46vu7680y1hwvmnnuh4cybx FOREIGN KEY (user_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


