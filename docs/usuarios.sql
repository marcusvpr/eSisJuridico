delete from mp_usuario;

alter table mp_usuario auto_increment = 1;

insert into mp_usuario (nome, email, senha) values ('Marcus Rodrigues', 'marcus@mpxds.com', 'teste');
insert into mp_usuario (nome, email, senha) values ('Prisco Brito', 'prisco@mpxds.com', 'teste');
