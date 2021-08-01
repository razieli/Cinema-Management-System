# **Cinema-Management-System**

## Developers:
* [Yuval Razieli](https://github.com/Razieli)
* [Yaniv Shnur](https://github.com/YanivShnur)
* [Gal Gibly](https://github.com/Galgibly)
* [Shay Silberklang](https://github.com/ShaySilbeklang)
* [Regev Aloni](https://github.com/AloniRegev)
* [Zvi Knoll](https://github.com/ZviKnoll)

## What can be done using this system?

There are 5 authorization levels for customers and employees (we used numbers for convenience):
* Level 0 - Customer
* Level 1 - CustomerService
* Level 2 - ContentManager
* Level 3 - BranchManager
* Level 4 - Administrator


The System allows the **customer** to:
1. Browse movie list
2. Purchase a movie ticket with the option to reserve seats for up to 10 minutes
3. Purchase a viewing package
4. Purchase a link to watch a specific movie online
5. Receive a purchase confirmation email
6. Cancel purchase
7. File a complaint


In addition this system allows **employees** to:
1. Update content, add and remove movies, etc. (Level 2+)
2. Handling customer complaints with the option of providing a financial credit (Level 1+)
3. Update restrictions following a Purple Badge due to COVID-19 (Level 1+)
4. Request for price update (Level 1+)
5. Confirmation of price update (Level 3+)
6. View branch reports (Level 3+)
7. View reports of all branches (Level 4)


Special features:
* Reports are displayed by way of graphs and histograms
* Automatic seat selection according to the number of people limit (Purple Badge restrictions)
* Save seats for 10 minutes from the moment a purchase process begins
* Multiple customers can simultaneously purchase movie tickets without a collision
* Customer messages are delivered in real time via email
* The passwords are encrypted
* There is an automatic check of the correctness of the input
* A combo box is used on many screens
* Sending an email to subscribers about a new screening
* Automatic handling of customer complaints that have not been processed for 24 hours


## Why and how was it built?
* This project was built as part of a software engineering course at the university.
* The system is based on a client-server model architecture.
* The code is written in Java.
* Design patterns such as: Observer, Singelton and Adapter.
* Compiled as a Maven project in IntelliJ.
* The application uses the Hibernate Java Framework.
* Hashing Passwords in Java with BCrypt
* Sending Email with JavaMail API


## How can I run it?
1. Download Server.jar and Client.jar from the main dir
2. Install MySQL Workbench
3. Execute the command:
    * `CREATE SCHEMA 'cinema' DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;`
4. Make sure you define the following on the Administartion screen:
    * hostname (="localhost")
    * login name (="root")
    * password (="cinema1234password")
    
    check [hibernate.properties](Common/src/main/resources/hibernate.properties) just to be sure
5. Run from CMD the command: (wait for "Server connected" message appears)
    * `java -jar Server.jar 8080`
6. Open the file Client.jar (we recommend via CMD, but a simple double-click will do)
7. For login info you can lookup usernames and passwords in the [DB](Server/src/main/java/il/ac/haifa/cs/sweng/cms/DB.java#L114).

   For convenience the user and password are the same and can be connected according to the level of permission you want (see details above)
   
   For example (user:pass):
   * Customer (Level-0) 0:0 or 00:00
   * Employee 1:1 or 2:2 or 3:3 or 4:4 (Permission level respectively)


## Upcoming features
- Connecting a guest user to the system without the need for prior registration
- Interface from the browser


## Note
- This GitHub project is for study purpose only. For other purposes, please contact me at Cinema2021SWE@gmail.com


## Examples

#### 1. Login Demo
![01_login_demo](https://user-images.githubusercontent.com/79280930/127687731-d25df841-5016-48db-b0a5-3411d3d5b9fd.gif)

---

#### 2. Coming Soon Demo
![02_coming_soon_demo](https://user-images.githubusercontent.com/79280930/127687738-801148cf-07cf-4a5c-89a1-abf3f90f27d0.gif)

---

#### 3. Ticket Purchase Demo
![03_ticket_demo](https://user-images.githubusercontent.com/79280930/127687943-5542b51a-c627-4746-8fcb-0c90d9112a26.gif)

---

#### 4. Link Purchase Demo
![04_link_demo](https://user-images.githubusercontent.com/79280930/127687958-843f4bab-8edf-4285-b44f-92087120fd1b.gif)

---

#### 5. Package Purchase Demo
![05_package_demo](https://user-images.githubusercontent.com/79280930/127687965-0152190f-4a2f-47e7-bd90-bde9efbe8e36.gif)

---

#### 6. Cancel Purchase Demo
![06_cancel_purchase_demo](https://user-images.githubusercontent.com/79280930/127687970-a2cb944c-3968-4a9c-85dd-717e2b0d61ab.gif)

---

#### 7. Update Movie Demo
![07_update_movie_Demo](https://user-images.githubusercontent.com/79280930/127687973-f4de88aa-8258-4593-8fa1-95f568b1cf39.gif)

---

#### 8. Price Change Demo
![08_price_change_demo](https://user-images.githubusercontent.com/79280930/127687981-4606d7ef-b830-415b-8357-989be9082f4d.gif)

---

#### 9. Support Demo
![09_support_demo](https://user-images.githubusercontent.com/79280930/127687987-a1bed281-4cbf-41b3-8b76-c4d1d33e5d5c.gif)
