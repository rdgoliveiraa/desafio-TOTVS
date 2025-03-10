
## Documentação da API

#### Cria uma conta

```http
  POST /api/account/v1
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `dueDate` | `string` | **Obrigatório**|
| `paymentDate` | `string` | **Obrigatório**|
| `value` | `double` | **Obrigatório**|
| `description` | `string` | **Obrigatório**|
| `situation` | `string` | **Obrigatório**. Enum de situação de uma conta, que aceita os valores **PENDING**, **PAID**, **CANCELED**.|

#### Retorna uma conta

```http
  GET /api/account/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. O ID da conta que você quer |

#### atualiza uma conta

```http
  POST /api/account/v1/{id}
```
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id`      | `string` | **Obrigatório**. O ID da conta que você quer |
| `dueDate` | `string` | **Opcional**|
| `paymentDate` | `string` | **Opcional**|
| `value` | `double` | **Opcional**|
| `description` | `string` | **Opcional**|

#### atualiza a situação da conta

```http
  POST /api/account/v1/{id}/situation/{situation}
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id`      | `string` | **Obrigatório**. O ID da conta que você quer |
| `situation`      | `enum` | **Obrigatório**. Enum de situação de uma conta, que aceita os valores **PENDING**, **PAID**, **CANCELED**.|

#### obtem paginação de contas filtrada por data de pagamento e descrição

```http
  POST /api/account/v1/due-date-description
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `dueDate`      | `string` | **Opcional**. |
| `description`      | `string` | **Opcional**. |

#### obtem valor total de contas filtrada por data de início e data fim

```http
  POST /api/account/v1/due-date-description
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `startDate`      | `string` | **Opcional**. |
| `endDate`      | `string` | **Opcional**. |

#### criação de conta através de arquivo csv

```http
  POST /api/account/v1/by-file
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `file`      | `file` | **Obrigatório**. |

### Todas requisições devem utilizar token de autorização que pode ser obtido através da seguinte requisição

```http
  POST /api/auth/signin
```
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `username`      | `string` | **Obrigatório**. admin |
| `password`      | `string` | **Obrigatório**. admin123 |


## Rodando localmente

Clone o projeto

```bash
  git clone [https://link-para-o-projeto](https://github.com/rdgoliveiraa/desafio-TOTVS)
```

Entre no diretório do projeto

```bash
  cd desafio-TOTVS
```
Gere o jar

```bash
  mvn clean package
```

Inicie os contêineres

```bash
  docker compose up
```

Para disparar as requisições pode ser utilizado a collection no postman localizado no diretório raiz do projeto.
