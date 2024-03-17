## 📝 REST Backend for 15 Puzzle game

The backend of a 15 Puzzle game, which is a sliding puzzle that consists of a 4x4 board of numbered square tiles in random order with one tile missing, with REST end-points.
A REST API that receives input and exposes the game state in JSON.  

The game state is handled in memory; no database is used. The games disappear when the server stops.  

### This project is made with:
- Java17
- Spring Boot
- Junit5 (for testing)
- Maven
- (Mockito could be used for future work with test mocks if complexity demands it)

## Running The Project Locally

### Installation
This requires the repository to be cloned locally and the current directory to be the root of the repo.

1. Clone the repository from
    ```sh
   https://github.com/EviePalaiochorinou/15-puzzle.git
    ```

2. Build this Spring Boot Project with Maven mvn package or `mvn install` / `mvn clean install`
3. Run this Spring Boot app using Maven: `mvn spring-boot:run`


Now you can test the endpoints manually
  ```sh
  POST
  http://localhost:8080//player/{playerId}/game
  
  GET
  http://localhost:8080//player/{playerId}/game
  
  PUT  
  http://localhost:8080//player/{playerId}/play
  ``` 
Or use the http client of your choice.  

### Testing

You can run the tests using:
```shell script
mvn test
```
Or by right-clicking on run on the test package in Intellij.

## 🖊️ Implementation

The API exposes the following two endpoints:

### `POST /player/{playerId}/game`

Creates a new game for the player with the given ID. The game is initialized with a random board. The response includes the initial state of the board and whether the player won or not.  

**Path Variable**
* playerId

Example request: `/player/1/game`

### `GET /player/{playerId}/game`

Returns the current state of the game for the player with the given ID. The response includes the current state of the board and whether the player won or not.

**Path Variable**
* playerId

Example request: `/player/1/game`  

### `PUT /player/{playerId}/play`

Returns the current state of the game for the player with the given ID. The response includes the current state of the board and whether the player won or not.

**Path Variable**
* playerId

**Request Body**
* move: The direction in which the empty tile should be moved. It can be one of the following values: UP, DOWN, LEFT, RIGHT.

Example request: `/player/1/play`

## Requirements

### 📋 Specifications
1. The game state is handled in memory. No database is used. The game disappears after restarting the application.
2. Multiple games could be played at the same time. For example: player1 plays game1,
   player2 plays game2.
3. Assume that we use a library to implement logic to move tiles. Empty methods have been created
   to handle this.
3. The game ends after a single move for a given player because we are not implementing the tile logic.  
   We implemented a fake method that checks if the board is in the winning state.
4. The game is initialized with a random board.

## Implementation

### 💻 Error Handling

- The service throws an error when the player tries to make a new move after their game has ended.
- A **custom** exception was created for when the client requests an invalid move to be played.
- Future work: Exception when you are trying to GET the game of a player that does not exist. The persistence layer would throw an exception that when caught by the service layer, then the service throws an exception with a meaningful message.


### 💻 Testing

- The Service layer is not mocked when we test the Controller, since we currently don't have a high level of complexity.
- The dao layer is not tested, since it is indirectly tested when we test the service layer (integration tests). It is consciously not mocked, as we store in memory with a simple mechanism that does not cost anything. If we were to persist the data in a database or another way, maybe we would have liked to mock the dao layer.
- MockMvc is used to test the controller layer.

### 💻 Assumptions

- Since we do not have logs, in some cases the error caught is printed instead.
- No overrides.properties needed as we do not have any properties to override for this application.
- We use a Data Access Object because it allows us to isolate the service layer from the persistence layer  
  (usually a database but can be any other persistence mechanism) using an abstract API.  
The API hides from the application all the complexity of performing CRUD operations in the underlying storage mechanism.  
This permits both layers to evolve separately without knowing anything about each other.

### 💬 Future Work

- If we had different implementations of the Store, I would name then like 'GameEngineStoreMapImpl', 'GameEngineStoreDBImpl', etc.
- Add a database to store the data for resilience, scalability, and advanced handling, instead of keeping the data in memory.
