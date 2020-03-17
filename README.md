# prepag
Api simulação sistema de venda cartão 

- faça o clone da aplicação no git hub. 
- Alterar usuario e senha do banco de dados no arquivo application.properties.
-  o banco de dados mysql está sendo criado automaticamente, desde que a url de conexão, usuário e senha estejam corretos. 
- como devemos usar? primeiro devemos executar a funcionalidade 'emitir' para criar o cartão, apos isso podemos chamar a funcionalidade comprar, lembrando que o campo numero, cvv e senha deve estar presente na tabela cartao por causa da validação   

exemplo requisão [/cartoes/emitir] tipo: post

{
	"nome": "Nicolas",
	"saldo": 1500.00
}

exemplo retorno [/cartoes/emitir] 

{
    "data": {
        "id": 2331878038345974,
        "nome": "Nicolas",
        "cvv": "638",
        "validade": "2022-03-16",
        "senha": "6884",
        "saldo": 1500.0
    },
    "errors": []
}
obs: o campo senha está vindo descriptografado apenas na resposta para o usuário ver a senha criada randomicamente e poder realizar a compra, mas no banco está sendo persistida criptografada (MD5).

O campo id(número do cartão) e cvv também são criados randomicamente
----------------------------------------------------------
exemplo requisão [/vendas/comprar] tipo: post 

{	
	"numero":	"2331878038345974",
	"validade":	"2022-03-15",
	"cvv":		"638",
	"nomeEstabelecimento": "Luiz Salgados",
	"valor": 50.0,
	"senha": "6884"
	
	
}

exemplo retorno [/vendas/comprar]

{
    "data": {
        "codigo": "00",
        "saldo": 500.0
    },
    "errors": []
} 
------------------------------------------------------------------------------------------
temos outros end point 
[/cartoes/apagar/{id}] -> exemplo 
[/cartoes/buscar/{id}]
[/vendas/apagar/{id}]
[/vendas/buscar/{id}]

Utilize o postman para realizar a requisições 

-------------------------------------------------------------------------------------------------------
Principais Ferramentas utilizadas: 
Banco mysql 
Hibernate para implemnetação da JPA
Spring Boot
Junit para testes unitarios
padrão MD5 para criptografia   
