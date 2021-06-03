# Env-Chamber

The prerequistes for building this project are the following:
* Maven 3.0+ 
* JDK 64/32bit

## Building steps 
At the first modify the database configuration to whatever works on your machine in the [MyJDBC Class](https://github.com/JabSYsEmb/Env-Chamber/blob/main/modules/app/src/main/java/com/imposters/team/db/MyJDBC.java)
Then, follow run the following commands in your terminal:
### For Windows users:
```bash
git clone https://github.com/JabSYsEmb/Env-Chamber.git
cd ./Env-Chamber
buildrun.cmd
```
The buildrun batch file should do the whole works for you, in case any problem occurs you can follow the same steps as a linux user.
### For Linux users:
```bash
git clone https://github.com/JabSYsEmb/Env-Chamber.git
cd ./Env-Chamber
mvn clean package
java -jar ./modules/app/target/app-1.jar
```
### to start the server:
After the building has been done and regardless of whatever operating system you use, you should run the following commands in order to start the server:
```bash
java -jar ./modules/server/target/server-1.jar <port>
```
The port argument is optional since the server is set to listen on port 2332 as default but it is possible to change the port by passing it to the server, for instance:
```
java -jar ./modules/server/target/server-1.jar 2021
```

Wait for more, 
To be continued . . .