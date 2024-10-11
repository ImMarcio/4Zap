# 4Zap - Sistema de Bate-Papo

4Zap é um sistema de bate-papo entre pessoas e grupos, permitindo uma comunicação fácil e eficiente. Com o 4Zap, você pode trocar mensagens individuais, participar de grupos de bate-papo e gerenciar suas mensagens enviadas e recebidas.

![Página inicial](assets/preview.png)

## Funcionalidades Principais

- Envio de mensagens individuais
- Criação e participação em grupos de bate-papo
- Gerenciamento de mensagens enviadas e recebidas
- Interface de usuário intuitiva e responsiva

## Estrutura do Projeto

O projeto 4Zap possui a seguinte estrutura de diretórios e arquivos:

-📦4Zap
- ┣ 📂backup
- ┃ ┣ 📜grupo.csv
- ┃ ┣ 📜individual.csv
- ┃ ┗ 📜mensagem.csv
- ┣ 📂bin
- ┃ ┣ 📂appsConsole
- ┃ ┃ ┣ 📜Teste1.class
- ┃ ┃ ┗ 📜TesteProprio.class
- ┃ ┣ 📂appsSwing
- ┃ ┃ ┣ 📜desktop.ini
- ┃ ┃ ┣ 📜TelaConversa$1.class
- ┃ ┃ ┣ 📜TelaConversa$2.class
- ┃ ┃ ┣ 📜TelaConversa$3.class
- ┃ ┃ ┣ 📜TelaConversa$4.class
- ┃ ┃ ┣ 📜TelaConversa.class
- ┃ ┃ ┣ 📜TelaEspionagem$1.class
- ┃ ┃ ┣ 📜TelaEspionagem$2.class
- ┃ ┃ ┣ 📜TelaEspionagem$3.class
- ┃ ┃ ┣ 📜TelaEspionagem.class
- ┃ ┃ ┣ 📜TelaLogin$1.class
- ┃ ┃ ┣ 📜TelaLogin.class
- ┃ ┃ ┣ 📜TelaMensagem$1.class
- ┃ ┃ ┣ 📜TelaMensagem$2.class
- ┃ ┃ ┣ 📜TelaMensagem.class
- ┃ ┃ ┣ 📜TelaParticipante$1.class
- ┃ ┃ ┣ 📜TelaParticipante$2.class
- ┃ ┃ ┣ 📜TelaParticipante$3.class
- ┃ ┃ ┣ 📜TelaParticipante$4.class
- ┃ ┃ ┣ 📜TelaParticipante$5.class
- ┃ ┃ ┣ 📜TelaParticipante.class
- ┃ ┃ ┣ 📜TelaPrincipal$1.class
- ┃ ┃ ┣ 📜TelaPrincipal$2.class
- ┃ ┃ ┣ 📜TelaPrincipal$3.class
- ┃ ┃ ┣ 📜TelaPrincipal$4.class
- ┃ ┃ ┣ 📜TelaPrincipal$5.class
- ┃ ┃ ┣ 📜TelaPrincipal$6.class
- ┃ ┃ ┣ 📜TelaPrincipal$7.class
- ┃ ┃ ┗ 📜TelaPrincipal.class
- ┃ ┣ 📂imagens
- ┃ ┃ ┣ 📜imagem.png
- ┃ ┃ ┗ 📜tela_inicial.jpeg
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
- ┃ ┃ ┣ 📜desktop.ini
- ┃ ┃ ┣ 📜TelaConversa.java
- ┃ ┃ ┣ 📜TelaEspionagem.java
- ┃ ┃ ┣ 📜TelaLogin.java
- ┃ ┃ ┣ 📜TelaMensagem.java
- ┃ ┃ ┣ 📜TelaParticipante.java
- ┃ ┃ ┗ 📜TelaPrincipal.java
- ┃ ┣ 📂imagens
- ┃ ┃ ┣ 📜imagem.png
- ┃ ┃ ┗ 📜tela_inicial.jpeg
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
_O diretório `modelo/` contém as classes de modelo de negócio que representam os participantes (sendo Grupos ou Individuos) e mensagens do sistema._ <br>
_O diretório `appsConsole/` contém os testes do sistema via Console._ <br>
_O diretório `appsSwing/` contém as classes gráficas  do sistema, onde é possível encontrar a interface._ <br>
_O diretório `imagens/` contém imagens para a interface._ <br>
_O diretório `backup/` serve para deixar as mensagens salvas na memória._ <br>
_Além de tudo isso, é possível encontrar vários arquivos .csv, que são aqueles que vão ser salvos apenas em tempo de execução, e outros mais._ <br>

## Como Executar o Projeto

1. Clone este repositório em sua máquina local:

```git clone https://github.com/ImMarcio/4Zap.git```

- Ou baixe o APP 4Zap: 4Zap.jar

2. Navegue até o diretório do projeto:

```cd 4Zap``` depois ```cd 4Zap```

3. Compile e execute o projeto:

<p>Verificar o caminho do arquivo, e executar:</p>

```javac nome_pasta/nome_aquivo.java``` <br>
```java nome_arquivo.java```

## Contribuidores

- Allan Amâncio - https://github.com/AllanSmithll;
- Márcio José - https://github.com/ImMarcio.

## Licença

Este projeto está licenciado sob a [Licença Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0).
