
create table if not exists persistent_logins ( 
  username varchar(100) not null, 
  series varchar(64) primary key, 
  token varchar(64) not null, 
  last_used timestamp not null
);

delete from  user_role;
delete from  roles;
delete from  users;


INSERT INTO roles (id, adi) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_ACTUATOR'),
(3, 'ROLE_FIRMA'),
(4, 'ROLE_OGRETMEN'),
(5, 'ROLE_OGRENCI')
;

INSERT INTO users (id, email, password, adi) VALUES
(-1, 'admin@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin'),
(-3, 'firma@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Firma'),
(-4, 'ogretmen@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Öğretmen'),
(-5, 'ogrenci@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Öğrenci');

insert into user_role(user_id, role_id) values
(-1,1),
(-1,2),
(-3,3),
(-4,4),
(-5,5);

