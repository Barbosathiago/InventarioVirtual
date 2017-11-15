CREATE TABLE tb_inventario (
	descricao varchar,
	_id integer PRIMARY KEY AUTOINCREMENT,
	nome text,
	fk_pessoa blob
);

CREATE TABLE tb_item (
	descricao text,
	_id integer PRIMARY KEY AUTOINCREMENT,
	imagem integer PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE tb_item_inventario (
	fk_item integer,
	fk_inventario integer,
	situacao integer,
	_id integer PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE tb_pessoa (
	situacao text,
	_id integer PRIMARY KEY AUTOINCREMENT
);

