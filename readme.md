# Welcome on this project


## Git 

### Fork 

By default Git name the base of a clone : origin, and work every time on the checkout local branch. In a multi-repository flow, give the name of the cloud remote is a good practise.

- update from a specify remote and branch :

In this sample, `mphil78` is the remote, `Encheres-fin-1ere-semaine` is the branch

```Shell
git fetch --all
git pull --rebase mphil78 Encheres-fin-1ere-semaine
```

- backup on a cloud and specify 

```Shell
git push origin
```

## JAVA 

- Use a 8 JDK on the system.

- Use your favorit IDE with Maven import.

- Configure on your server The database server resource like sample in ./conf

```XML
<Resource name="jdbc/pool_cnx" auth="Container"   type="javax.sql.DataSource"
     maxTotal="100" maxIdle="30" maxWaitMillis="10000"
     username="sa" password="" driverClassName="org.h2.Driver"
     url="jdbc:h2:file:~/temp-database/eni-server-db;MODE=MSSQLSERVER" />
```

### NO developpement Java developpement environnement, juste install Maven  : 

- Nettoyage et construction du package WAR 

```Shell
mvn clean package
```

- Demarrage Tomcat 9 depuis Maven, avec le plugin https://codehaus-cargo.github.io/cargo/Home.html

```Shell
mvn -Pcargo cargo:run
```
