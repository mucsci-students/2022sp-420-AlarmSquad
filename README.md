# 2022sp-420-AlarmSquad

## Need:
- [Latest version of git](https://git-scm.com/downloads)
- [Latest version of Java](https://www.oracle.com/java/technologies/downloads/)

## Setup

1. Open Terminal for Linux and macOS, Command Prompt for Windows

2. Clone the repository

```
git clone https://github.com/mucsci-students/2022sp-420-AlarmSquad
```

3. Navigate to the app directory inside the project folder

```
# Linux and macOS
cd 2022sp-420-AlarmSquad\maven\initial

# Windows
cd 2022sp-420-AlarmSquad\maven\initial
```

4. Invoke `mvnw` for your OS.

```
# Linux and macOS
./mvnw package

# Windows
.\mvnw package
```

5. To run the application, invoke the generated jar file

```
# To run in GUI
java -jar target\2022sp-420-AlarmSquad-1.0.jar

# To run in CLI
java -jar target\2022sp-420-AlarmSquad-1.0.jar --cli
```

6. If you would like to run the tests, invoke the following command

```
# Linux and macOS
./mvnw test

# Windows
.\mvnw test
```

###### Authors: Aaron Katz, Logan Santee, Andrew Foster, Ryan Ganzke, Max Moyer  
