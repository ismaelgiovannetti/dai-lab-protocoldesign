# Client-Server protocol - Basic arithmetic operations
This protocol defines the interactions between a client sending calculation requests and a server processing them.

## Available commands
- `ADD x y` : addition of two numbers
- `MULT x y` : multiplication of two numbers
- `SUB x y` : substraction of two numbers
- `DIV x y` : division of two numbers

## Message formats
 - Each client message is a command in the string format : `OPERATION number1 number2`
 - Each server message is a response in the string format : `RESULT value` (where value can be undefined)

 ## Examples
 - Client : `ADD 1 2` -> Server : `RESULT 3`
 - Client : `MULT 1 -2` -> Server : `RESULT -2`
 - Client : `SUB 1 2` -> Server : `RESULT -1`
 - Client : `DIV 1 0` -> Server : `RESULT undefined`

 ## Errors
For robust communication, the server may return error messages in case of incorrect commands.
- `ERROR INVALID_OPERATION` : for unrecognized operations
- `ERROR NON_NUMERIC_VALUES` : for non numeric values

As shown previously, division by zero are handled as an undefined result and not as an error.

## Connection and communication steps
1. **Connection** : The client initiates a TCP connection to the server.
2. **Sending commands** : The client sends a command.
3. **Server response** : The server receives, processes, and responds to the client.
4. **Termination** : Once all commands are sent, the client closes the connection (no QUIT command).




