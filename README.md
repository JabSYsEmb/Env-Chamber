# Env-Chamber

The prerequistes for building this project are the following:
* Maven 3.0+ 
* JDK 64/32bit

## Building steps 
At the first modify the database configuration to whatever works on your machine in the [MyJDBC Class](https://github.com/JabSYsEmb/Env-Chamber/blob/main/modules/app/src/main/java/com/imposters/team/db/MyJDBC.java)
Then, run the following commands in your terminal:
### For Windows & Linux users :
```bash
git clone https://github.com/JabSYsEmb/Env-Chamber.git
cd ./Env-Chamber
buildProject.bat 
```
The buildProject batch file should do the whole cleaning and packaging of the project for you.
After the project is built, we need to run the application and the server in two seperated terminals.

### In First Terminal:
At the first, we need to start the server to let the application communicate with it, the server is set up to run and listen automatically to port 2332 (in case you want to change the port you need to change in both in the server and the client class in the application).
Type the following for starting the server:
```
StartServer.bat
```

### In Second Terminal:
Now we need to run the application by typing the following command in a seperated terminal
```bash
Application.bat
```

Wait for more, 
To be continued . . .