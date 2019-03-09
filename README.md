# LiMe
LiMe is a communication application implemented in Java that allows you to keep in touch and exchange files with friends anytime, anywhere.
You can also quickly deploy a custom LiMe server for private communication.

## Full name
The Li Xin Messenger

## Tech reviews
LiMe is developed in the MVC design pattern. 
We use Swing to implement the UI layer. 
The persistence layer is implemented with JDBC. 
As for the database, we chose MySQL. 
The model layer is strictly following JavaBean specification requirements. 
Fully comply with the code specification in the Alibaba Java Development Manual, 
each layer achieves high cohesion and low coupling, 
which significantly improves the scalability of the program and is easy to maintain.

## Code Guide Lines
This repository follows the guideline of the Alibaba coding guidelines.

For more information, please refer to the *Alibaba Java Coding Guidelines*:
- Chinese Version: *[阿里巴巴Java开发手册](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf)*
- English Version: *[Alibaba Java Coding Guidelines](https://alibaba.github.io/Alibaba-Java-Coding-Guidelines)*

## Version
- C_v 0.6.3
- S_v 0.6.3

```
 _______________________
/    Finally, v0.6.2!   \
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
为了使LiMe更好地工作，我们设定了一个应用层协议

## The Server GUI
![](./ScreenShots/LiMeServer.png)

## Client

The Login GUI and the welcome page as well.

![](./ScreenShots/LiMeLogin.png)

The Register GUI

![](./ScreenShots/LiMeRegister.png)

The User Agreement (HTML parsing)

![](./ScreenShots/LiMeAgreement.png)

The Chat GUI of user @lixin, the friend list is on the left side of the panel
![](./ScreenShots/LiMeChatLixin.png)

The Chat GUI of user @test

![](./ScreenShots/LiMeChatTest.png)

### The Group Chat

![](./ScreenShots/LiMeGroupChat.png)

### The File Transmission

![](./ScreenShots/LiMeChatFile.png)

## Data persistence

MySQL table structure
![](./ScreenShots/TableStructure.png)

## Emails you might get from the server

Registration Confirmation
![](./ScreenShots/EmailCfmReg.png)

Banned Notification
![](./ScreenShots/EmailNtfBan.png)

Password Reset
![](./ScreenShots/EmailRstPwd.png)

## TODO
- [ ] Rebuild The Protocol with Restful API (json)
- [ ] Gradle the project
- [ ] Use HTML to render the email content
- [ ] Use hibernate or Mybatis as a persistence framework
- [ ] The process bar for the file transmission
- [ ] A fancy website for LiMe
- [x] Store the password on the server with MD5
- [x] Local password storage encrypted with AES using a random key, the random key stored with AES digested with MD5
- [x] Transport the message with AES and keys digested with MD5
- [X] Open group chat for all users
- [x] Enable user to reset the password via a server-sent Email
- [x] Redirect LiMe to the new domain name
- [x] Email should be a unique key (LiMeSeedRecoverPassword)
- [x] The file transmission function
- [x] Use the database to validate and manage the user
- [x] Blur Agreement Frame
- [x] HTML parsing and rendering
- [x] Version number increase

## Test Quote
Fate Whispers To The Warrior,

“You Cannot Withstand This Storm.”

And The Warrior Whispers Back,

“I Am The Storm”