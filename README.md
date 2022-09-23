# <p align="center">XML Parser</p>
<p align="center">
  <img src="https://jsonformatter.org/img/xml-parser.png" />
</p>

> ### XML Parser is an application in which the user can upload one XML file and it exports multiple XML files based on certain criteria. 

## Criteria
- The input XML file(s) need to start with the name 'orders' followed by two digits.
- The input XML file(s) need to have an order tag with the time of the order creation and id, followed by a product tag with a description of the product, GTIN, product price, currency as an attribute and the product's supplier.


## General functionality
- The application reads, processes the data, writes, and deletes the original file from the input file.
- The data process sorts the data read descending by order's timestamp and product's price.
- The applications export the new XML files based on each product's supplier in the output file. 
- The application checks the input file's name to be exactly as specified in the Criteria section above, it supports JUnit testing for methods and Logger for client notification.

## Libraries used
<p> From the most used parsing Java Libraries for <i>DOM, SAX, and StaX</i>.</p>
<p> After careful research, I decided that for the application is best to use StaX parse library because DOM Library creates a tree in memory of the XML file and allows it to access any part of the document repeatedly which slows down the application. SAX Libray is more efficient than DOM Library, but the SAX Library lacks the functionality which makes the user take care of problems, for example: creating their own data structure. </p>

<p>The StaX Library differentiates itself from the rest libraries through API type because it uses pull API rather than memory tree (DOM) and push API (SAX). The pull API is the best because the client is in control of when to receive the data in comparison, example: with push API in which the library pushes the data continuously to the user and the client does not have control of the data when it arrives.
</p>

<p>For the reading process, it was used StaX Cursor API for the following benefits: efficient code, also better performance compare to Iterator API, and high-performance applications.</p>

<p>For the exporting of the XML files, it was added the Transformer class which helped transform the XML file's content prettier.</p>

## How to install
Make sure you have [Maven (Windows)](https://www.educba.com/install-maven/) or [Maven (Linux)](https://www.journaldev.com/33588/install-maven-linux-ubuntu) install.
Open the project by right-click the pom.xml and selecting your favorite editor (might take a minute for the dependencies to install).

Verify that maven is installed:

`mvn --version`

Build project:

`mvn package`

## Getting started
The client needs to put it's own input file path and output file path. 

This can be achieved creating a folder file named `resources` in the main folder and under it, create two new folder named `input`, `output` and new file named `config.properties` which will be stored safetly input file path and output file path. 

To get the input file path and output file path, the user needs to click right to one of the file and press the option `Copy Path/Reference` -> `Absolute Path` and add a `/` at the end for every file path.
