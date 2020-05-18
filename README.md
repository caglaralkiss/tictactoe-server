# TicTacToe Server

This project includes Websocket server for a basic TicTacToe game. For the client part of the project, see my other repository [XOX-Client](https://github.com/caglaralkiss/vanilla-xox-game).

## Getting Started

### Prerequisites
```
Java(JDK 8)
Docker(optional)
```

### Installation

#### With Command Line

* Go to project root directory.
* Build the project with command ``mvn clean package``.
* Execute the following command: ``java -jar target/tictactoe-0.0.1-SNAPSHOT.jar``. You can also specify a port different than default port(``8080``) with ``java -Dserver.port=<port> -jar target/tictactoe-0.0.1-SNAPSHOT.jar``
* Server will be listening requests on the port speficied.

#### With Maven
Alternatively, you can run an embedded server for development purposes:
* Navigate to project directory in your local.
* Run ``mvn spring-boot:run`` command.
* Server will be listening requests on the port ``8080``.

#### With Docker
* Go to project root directory.
* Build the project with command ``mvn clean package``.
* Execute the following command: ``docker build -t xox-server .``
* After image is created, create a container with following command:
```
docker run -p <port>:8080 xox-server
```
* Requests will be listening on the ``<port>`` you specified.