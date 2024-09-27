# LiMe

## The Li Xin (Justin Lee) Messenger

LiMe is a communication application implemented in Java that allows you to keep in touch and exchange files with friends
anytime, anywhere. You can also quickly deploy a custom LiMe server for private communication.

## Tech Reviews

- LiMe is developed in the MVC design pattern.
- We use Swing to implement the UI layer.
- The persistence layer is implemented with JDBC.
- As for the database, we chose MySQL.
- The model layer is strictly following JavaBean specification requirements.
- Fully comply with the code specification in the Alibaba Java Coding Guidelines,
- Each layer achieves high cohesion and low coupling, which significantly leverages the scalability and the
  maintainability of this project.

## Requirements

- JDK 23
  > This project is compiled with Temurin.
  > ```
  > openjdk 23 2024-09-17
  > OpenJDK Runtime Environment Temurin-23+37 (build 23+37)
  > OpenJDK 64-Bit Server VM Temurin-23+37 (build 23+37, mixed mode, sharing)
  > ```
  > If you would like to adapt this project to business use.  
  > Please use this project with `Temurin`, `Microsoft OpenJDK`, `OpenJDK` or any Non-Oracle JDK, Thanks.

## Code Guidelines

This repository follows the guideline of the _**Alibaba Java Coding Guidelines**_.

For more information, please refer to the _**Alibaba Java Coding Guidelines**_:

- English Version: _**[Alibaba Java Coding Guidelines](https://alibaba.github.io/Alibaba-Java-Coding-Guidelines)**_

## Version

- C_v 1.0.3
- S_v 1.0.3

  ```
   _______________________
  /    Finally, v1.0.3!   \
  |  _     _ __  __       |
  | | |   (_)  \/  | ___  |
  | | |   | | |\/| |/ _ \ |
  | | |___| | |  | |  __/ |
  | |_____|_|_|  |_|\___| |
  \                       /
   -----------------------
          \   ^__^
           \  (oo)\_______
              (__)\ LiMoo )\/\
                  ||----w |
                  ||     ||
  ```

## Configuring

### Client Configuring

Fill `client.properties` like this:

```properties
#LiMe Config file
lime.cipher.key=<16_CHAR_STRING>
lime.host=<HOST_NAME>
lime.port=<PORT>
```

### Server Configuring

Fill `server.properties` like this:

```properties
#LiMe Server Config file
server.cipher.key=<16_CHAR_STRING>
server.db.db=<DB_NAME>
server.db.host=<DB_HOST_NAME>
server.db.password=<DB_PASSWORD>
server.db.port=<DB_PORT>
server.db.username=<DB_USERNAME>
#Only Supports Gmail Currently, you could try others.
server.email.domain=gmail.com
server.email.password=<EMAIL_PASSWORD>
server.email.user=<EMAIL_USERNAME>
server.port=<PORT>
```

### Must be equal

- `lime.port` and `server.port`
- `lime.cipher.key` and `server.cipher.key`

## Platform compatibility:

- macOS
  > If you would like to use it with windows, you should clone this repo and rebuild it with windows.

## Protocol

To enable a more efficient and secure LiMe, we set up an application layer protocol.

## The Server GUI

![The Server GUI](static/img/LiMeServer.png)

## Client

### The Login GUI and the welcome page as well.

![The Login GUI and the welcome page as well.](static/img/LiMeLogin.png)

### The Register GUI

![The Register GUI](static/img/LiMeRegister.png)

### The User Agreement (HTML parsing)

![The User Agreement (HTML parsing)](static/img/LiMeAgreement.png)

### The Chat GUI of user @lixin, the friend list is on the left side of the panel

![The Chat GUI of user @lixin, the friend list is on the left side of the panel](static/img/LiMeChatLixin.png)

### The Chat GUI of user @test

![The Chat GUI of user @test](static/img/LiMeChatTest.png)

### The Group Chat

![The Group Chat](static/img/LiMeGroupChat.png)

### The File Transmission

![The File Transmission](static/img/LiMeChatFile.png)

## Data persistence

### MySQL table structure

![MySQL table structure](static/img/TableStructure.png)

## Emails you might get from the server

### Registration Confirmation

![Registration Confirmation](static/img/EmailCfmReg.png)

### Banned Notification

![Banned Notification](static/img/EmailNtfBan.png)

### Password Reset

![Password Reset](static/img/EmailRstPwd.png)

## TODO

- [ ] Pure cli version of LiMe Server to enable deployment on linux headless server.
- [ ] Adapt Travis CI
- [ ] Rebuild The Protocol with Restful API (json)
- [ ] Gradle the project
- [ ] Use HTML to render the email content
- [ ] Use `Hibernate` or `Mybatis` as a persistence framework
- [ ] The process bar for the file transmission
- [ ] A fancy website for LiMe
- [ ] Improve concurrence
- [x] Store all sensitive info in config files
- [x] Enable custom host and port
- [x] Migrate to `Temurin`
- [x] Migrate to Non-Oracle JDK or `OpenJDK`
- [x] Fix the server thread pool malfunction issue
- [x] Merge all the services out of _**~~P.R.China~~**_ to provide a global-based service.
- [x] Store the password on the server with MD5
- [x] Local password storage encrypted with AES using a random key, the random key stored with AES digested with MD5
- [x] Transport the message with AES and keys digested with MD5
- [x] Open group chat for all users
- [x] Enable user to reset the password via a server-sent Email
- [x] Redirect LiMe to the new domain name
- [x] Email should be a unique key (LiMeSeedRecoverPassword)
- [x] The file transmission function
- [x] Use the database to validate and manage the user
- [x] Blur Agreement Frame
- [x] HTML parsing and rendering
- [x] Version number increase

## Test Quote

```
Fate Whispers To The Warrior,
“You Cannot Withstand This Storm.”

And The Warrior Whispers Back,
“I Am The Storm”
```

# Made with ❤ by [Justin Lee](https://github.com/realJustinLee)!

™ and © 1997-2022 Justin Lee. All Rights Reserved. [License Agreement](./LICENSE)
