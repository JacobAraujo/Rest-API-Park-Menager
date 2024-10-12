insert into USERS(id, username, password, role) values (100, 'ana@email.com', '$2a$10$Z7/3dclbuOLEOKUt5EMdG.lzK4bYlbUtVjAehj2Y45QxTJ/mwAg3.', 'ROLE_ADMIN');
insert into USERS(id, username, password, role) values (101, 'bia@email.com', '$2a$10$Z7/3dclbuOLEOKUt5EMdG.lzK4bYlbUtVjAehj2Y45QxTJ/mwAg3.', 'ROLE_CLIENT');
insert into USERS(id, username, password, role) values (102, 'bob@email.com', '$2a$10$Z7/3dclbuOLEOKUt5EMdG.lzK4bYlbUtVjAehj2Y45QxTJ/mwAg3.', 'ROLE_CLIENT');
insert into USERS(id, username, password, role) values (103, 'tob@email.com', '$2a$10$Z7/3dclbuOLEOKUt5EMdG.lzK4bYlbUtVjAehj2Y45QxTJ/mwAg3.', 'ROLE_CLIENT');

insert into CLIENTS(id, name, cpf, id_user) values (10, 'Bianca Silva', '93738469060', 101);
insert into CLIENTS(id, name, cpf, id_user) values (20, 'Roberto Gomes', '28072892070', 102);

