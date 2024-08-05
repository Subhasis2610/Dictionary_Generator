# Dictionary Generator

The **Dictionary Generator** is a client-server-based application developed in Java. This project showcases a system that allows clients to request the generation of customized dictionaries from a central server. The server, known as Crunch Admin, processes these requests and generates dictionaries based on specified keywords and options, which are then sent back to the client.

## Key Features:

- **Client-Server Architecture:** Utilizes socket programming to enable communication between clients and a central server.
- **Graphical User Interface (GUI):** Provides an intuitive and user-friendly interface for both the server and client, enhancing interaction and usability.
- **Dynamic Dictionary Generation:** Allows clients to request dictionaries tailored to specific keywords, with the server generating and sending these dictionaries in real time.
- **File Management:** Supports saving the generated dictionary to a specified directory on the client’s system.

## Components:

- **Server (Crunch Admin):** Listens for incoming client connections, processes dictionary requests, and generates the requested dictionaries based on a local dictionary file.
- **Client Application:** Allows users to enter keywords, request dictionary generation from the server, view results in a GUI, and download the generated dictionary to a local directory.

## Learning Outcomes:

- **Socket Programming:** Demonstrates the use of Java sockets for communication between client and server applications.
- **GUI Development:** Features a graphical interface for ease of use, developed using Java Swing.
- **Network Security:** Provides a practical example of how customized dictionaries can be used in network security testing, particularly in brute-force attack scenarios.

## Installation and Usage:

- **Clone the Repository:** git clone https://github.com/yourusername/Dictionary-Generator.git
- **Compile and Run:** Follow the instructions in the README.md for compiling and running the server and client applications.

## References:

- Computer Networks, Andrew S. Tannenbaum
- Java Network Programming by Harold
- Internetworking with TCP/IP Client-Server Programming and Applications by Douglas E Comer and David L Stevens
- Client / Server Programming with Java and Corba by Robert Oriali and Dan Harkey
- GeeksforGeeks - Client-Server Model
- JavaTpoint - Socket Programming
