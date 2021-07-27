
<br />
<p align="center">
  
<h3 align="center">ReadingIsGood is an online books store </h3>

  <p align="center">
   Main target of ReadingIsGood is to deliver books from its one centralized warehouse to their customers within the same day. That is why stock consistency is the first priority for their vision operations
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>

  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

In this case, we will only consider; 
* Registering New Customer 
* Placing a new order 
* Tracking the stock of books 
* List all orders of the customer 
* Viewing the order details 
* Query Monthly Statistics 


### Built With

* []()`java 11`
* []()`spring-boot 2.5.2 Version`
* []()`MySQL 8.0`
* []()`docker & docker-compose`

**Additional Information:**

* []() Provides **swagger api** docs. You can reach it after successfully run the application.
  
`The URL`: http://localhost:8080/api/swagger-ui/index.html
* []()Unit tests for business requirements(only includes **book&order** modules) written using the **TDD** technique 
* []()Log all changes on entities by using **Hibernate-Envers** library(only includes **book&inventory** modules). https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#envers
* []()Added **Global Exception Handler** to be resilience
* []()Added secure endpoints. Authentication is provided by **JWT-Bearer Token**. You can get this token by request to the http://localhost:8080/api/user/authenticate to the endpoint. After that you can make requests by adding token to the header to the other endpoints.
* []()Added some basic **validations** like as you can not register via the same email again and again. 
* []()if it happens if 2 or more users tries to buy one last book at the same time, It will be prevented by implemented **optimistic locking**.
* []()Will query all orders of the customer by **paging**. 
* []()Database **view** schema is used to serve customer’s monthly order **statistics**. 
* []()**Dockerized** the project. You can find more details the following section.




<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* docker

or 

if you want to don't use docker

* java 11
* maven 3.3.6
* mysql 8.0
  

### Installation

1. Clone the project
   ```sh
   git clone https://github.com/fthgul/online-books-store.git
   ```
2. Execute the following docker command:
   ```sh
   docker build -t online-books-store .
   docker-compose up
   ```



<!-- USAGE EXAMPLES -->
## Usage

You can make request by import postman collections where is `online-books.postman_collection.json`


<!-- CONTACT -->
## Contact

Fatih Gül  `email:` fatihgul.elm@gmail.com

Project Link: [https://github.com/fthgul/online-books-store](https://github.com/fthgul/online-books-store)
