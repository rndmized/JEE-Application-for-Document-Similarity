# JEE-Application-for-Document-Similarity
Project for Advanced Obejct Oriented Software Development Module (4th Year, Bsc (Hons) in Software Development) 

## Project Overview

A Java web application that enables two or more text documents to be compared for similarity.

## Project Minimum Requirements

The implementation includes the following features:

1. A document or URL can be specified or selected from a web browser and then
dispatched to a servlet instance running under Apache Tomcat.

2. Each submitted document it is parsed into its set of constituent shingles and
then compared against the existing document(s) in an object-oriented database (db4O)
and then stored in the database.

3. The similarity of the submitted document to the set of documents stored in the
database is returned and presented to the session user.

## Simple UML Design

<p align="center">
<img src="https://github.com/rndmized/JEE-Application-for-Document-Similarity/blob/master/UML%20Diagrams/design.png" width="350">
</p>

##  Features and Design Decisions

* The access to the database is controller by a set of classes to prevent a concurrency issue in the database running on a separate Thread that takes in requests. (package: ie.gmit.db)
* Comparisons between documents run on a separate thread.

<p align="center">
<img src="https://github.com/rndmized/JEE-Application-for-Document-Similarity/blob/master/UML%20Diagrams/DetailedUMLDesign.png" width="350">
</p>

## Known Bugs

* Empty documents get a 99% similarity result with any other document.
* Due to the randomness of the minHash similarity values fluctuate from one request to another for the same files.

## Tecnologies

- Java: Java is a set of computer software and specifications developed by Sun Microsystems, 
	which was later acquired by the Oracle Corporation, that provides a system for 
	developing application software and deploying it in a cross-platform computing 
	environment.

- db4o: An embeddable open source object database for Java and.NET developers. Developed, 
	commercially licensed and supported by Actian. In October 2014, Actian declined to 
	continue to actively pursue and promote the commercial db4o product offering for new 
	customers.

-db4o XTEA encryption library XTEA:  A support for db4o open source object database. XTEA is 
	a 64-bit block Feistel cipher with a 128-bit key and a suggested 64 rounds.

## Tools and IDEs

- Eclipse Eclipse is an integrated development environment (IDE) used in computer programming, 
	and is the most widely used Java IDE.

- Tomcat: The Apache Tomcat® software is an open source implementation of the Java Servlet, 
	JavaServer Pages, Java Expression Language and Java WebSocket technologies.

## Authors

* **Albert Rando** - *Coding* - [rndmized](https://github.com/rndmized)

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/rndmized/JEE-Application-for-Document-Similarity/blob/master/LICENSE) file for details