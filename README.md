# LiMe

## The Li Xin Messenger

LiMe is a communication application implemented in Java that allows you to keep in touch and exchange files with friends anytime, anywhere.
You can also quickly deploy a custom LiMe server for private communication.

## Tech Reviews
- LiMe is developed in the MVC design pattern. 
- We use Swing to implement the UI layer. 
- The persistence layer is implemented with JDBC. 
- As for the database, we chose MySQL. 
- The model layer is strictly following JavaBean specification requirements. 
- Fully comply with the code specification in the Alibaba Java Coding Guidelines, 
- Each layer achieves high cohesion and low coupling, which significantly leverages the scalability and the maintainability of this project.

## Requirements
- JRE 13
  > This project is compiled with AdoptOpenJDK.
  > ```
  > openjdk 13.0.1 2019-10-15
  > OpenJDK Runtime Environment AdoptOpenJDK (build 13.0.1+9)
  > OpenJDK 64-Bit Server VM AdoptOpenJDK (build 13.0.1+9, mixed mode, sharing)
  > ```
  > If you would like to adapt this project to business use.  
  > Please use this project with `AdoptOpenJDK`, `OpenJDK` or any Non-Oracle JDK, Thanks.
 
## Code Guide Lines
This repository follows the guideline of the _**Alibaba Java Coding Guidelines**_.

For more information, please refer to the _**Alibaba Java Coding Guidelines**_:
- Chinese Version: _**[阿里巴巴Java开发手册](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf)**_
- English Version: _**[Alibaba Java Coding Guidelines](https://alibaba.github.io/Alibaba-Java-Coding-Guidelines)**_

## Version
- C_v 0.6.8
- S_v 0.6.8

  ```
   _______________________
  /    Finally, v0.6.8!   \
  |  _     _ __  __       |
  | | |   (_)  \/  | ___  |
  | | |   | | |\/| |/ _ \ |
  | | |___| | |  | |  __/ |
  | |_____|_|_|  |_|\___| |
  \                       /
   -----------------------
          \   ^__^
           \  (oo)\_______
              (__)\ LiMe  )\/\
                  ||----w |
                  ||     ||
  ```

## Platform compatibility: 
- macOS
  > If you would like to use it with windows, you should clone this repo and rebuild it with windows.

## Protocol
To enable a more efficient and secure LiMe, we set up an application layer protocol.  

## The Server GUI
![The Server GUI](static/img/LiMeServer.png)

## Client

###The Login GUI and the welcome page as well.

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
- [x] Fix the server thread pool malfunction issue
- [x] Migrate to Non-Oracle JDK or `OpenJDK`
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

# Made with ❤ by [Li Xin](https://github.com/Great-Li-Xin)!
™ and © 1997-2019 Li Xin. All Rights Reserved. [License Agreement](./LICENSE)
