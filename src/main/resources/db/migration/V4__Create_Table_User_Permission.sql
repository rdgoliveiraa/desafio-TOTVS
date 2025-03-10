CREATE TABLE  users_permissions (
  id_user bigint NOT NULL,
  id_permission bigint NOT NULL,
  PRIMARY KEY ( id_user , id_permission ),
  FOREIGN KEY ( id_user ) REFERENCES  users  ( id ),
  FOREIGN KEY ( id_permission ) REFERENCES  permissions  ( id )
);