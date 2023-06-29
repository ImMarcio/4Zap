# 4TALK - Sistema de Bate-Papo

4TALK é um sistema de bate-papo entre pessoas e grupos, permitindo uma comunicação fácil e eficiente. Com o 4TALK, você pode trocar mensagens individuais, participar de grupos de bate-papo e gerenciar suas mensagens enviadas e recebidas.

## Funcionalidades Principais

- Envio de mensagens individuais
- Criação e participação em grupos de bate-papo
- Gerenciamento de mensagens enviadas e recebidas
- Interface de usuário intuitiva e responsiva

## Estrutura do Projeto

O projeto 4TALK possui a seguinte estrutura de diretórios e arquivos:

-📦system-4TALK
- ┣ 📂backup
- ┃ ┣ 📜grupo.csv
- ┃ ┣ 📜individual.csv
- ┃ ┗ 📜mensagem.csv
- ┣ 📂bin
- ┃ ┣ 📂appsConsole
- ┃ ┃ ┣ 📜Teste1.class
- ┃ ┃ ┗ 📜TesteProprio.class
- ┃ ┣ 📂appsSwing
- ┃ ┃ ┗ 📜Tela1Swing.class
- ┃ ┣ 📂modelo
- ┃ ┃ ┣ 📜Grupo.class
- ┃ ┃ ┣ 📜Individual.class
- ┃ ┃ ┣ 📜Mensagem.class
- ┃ ┃ ┗ 📜Participante.class
- ┃ ┣ 📂regras_negocio
- ┃ ┃ ┣ 📜Fachada$1.class
- ┃ ┃ ┗ 📜Fachada.class
- ┃ ┗ 📂repositorio
- ┃ ┃ ┗ 📜Repositorio.class
- ┣ 📂src
- ┃ ┣ 📂appsConsole
- ┃ ┃ ┣ 📜Teste1.java
- ┃ ┃ ┗ 📜TesteProprio.java
- ┃ ┣ 📂appsSwing
- ┃ ┃ ┗ 📜Tela1Swing.java
- ┃ ┣ 📂modelo
- ┃ ┃ ┣ 📜Grupo.java
- ┃ ┃ ┣ 📜Individual.java
- ┃ ┃ ┣ 📜Mensagem.java
- ┃ ┃ ┗ 📜Participante.java
- ┃ ┣ 📂regras_negocio
- ┃ ┃ ┗ 📜Fachada.java
- ┃ ┗ 📂repositorio
- ┃ ┃ ┗ 📜Repositorio.java
- ┣ 📜.classpath
- ┣ 📜.gitignore
- ┣ 📜.project
- ┣ 📜grupo.csv
- ┣ 📜individual.csv
- ┗ 📜mensagem.csv

_O diretório `repositorio/` contém o arquivo `Repositorio.java`, responsável pelo gerenciamento e armazenamento de participantes, mensagens e grupos._ <br>
_O diretório `regras_negocio/` contém o arquivo `Fachada.java`, que implementa as regras de negócio do sistema, como criação de mensagens, adição de participantes, etc._ <br>
_O diretório `modelo/` contém as classes de modelo que representam os participantes, grupos e mensagens do sistema._ <br>
_O diretório `appsConsole` contém os testes do sistema via Console._ <br>
_O diretório `appsSwing` contém as classes gráficas  do sistema, onde é possível encontrar a interface._ <br>
_O diretório `arquivos` contém arquivos para a interface._ <br>
_O diretório `backup` serve para deixar as mensagens salvas na memória._ <br>
_Além de tudo isso, é possível encontrar vários arquivos .csv, que são aqueles que vão ser salvos apenas em tempo de execução, e outros mais._ <br>

## Como Executar o Projeto

1. Clone este repositório em sua máquina local:

```git clone https://github.com/ImMarcio/System-4TALK.git```

2. Navegue até o diretório do projeto:

```cd System-4TALK```

3. Compile e execute o projeto:

<p>Verificar o caminho do arquivo, e executar:</p>

```javac nome_pasta/nome_aquivo.java``` <br>
```java nome_arquivo.java```

## Contribuidores

- Allan Amâncio - https://github.com/AllanSmithll;
- Márcio José - https://github.com/ImMarcio.

## Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).