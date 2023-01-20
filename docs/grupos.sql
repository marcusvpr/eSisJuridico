delete from mp_grupo;

alter table mp_grupo auto_increment = 1;

insert into mp_grupo (nome, descricao) values ('ADMINISTRADORES', 'Administradores Sistema');
insert into mp_grupo (nome, descricao) values ('VENDEDORES', 'Vendedores Sistema');
insert into mp_grupo (nome, descricao) values ('AUXILIARES', 'Auxiliares Sistema');
