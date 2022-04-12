# 2022sp-420-AlarmSquad

## Need:
- [Latest version of git](https://git-scm.com/downloads)
- [Latest version of Java](https://www.oracle.com/java/technologies/downloads/)

## Setup:

1. Open Terminal for Linux and M  acOS, Command Prompt for Windows

2. Clone the repository

```
git clone https://github.com/mucsci-students/2022sp-420-AlarmSquad
```

3. Navigate to the app directory inside the project folder

```
# Linux and MacOS
cd 2022sp-420-AlarmSquad/maven/initial

# Windows
cd 2022sp-420-AlarmSquad\maven\initial
```

4. Invoke `mvnw` for your OS.

```
# Linux and MacOS
./mvnw package

# Windows
.\mvnw package
```

5. To run the application, invoke the generated jar file

```
# Linux and MacOS:
# To run in GUI (default)
java -jar target/2022sp-420-AlarmSquad-1.0.jar

# To run in CLI
java -jar target/2022sp-420-AlarmSquad-1.0.jar --cli

# Windows:
# To run in GUI (default)
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

# Design Patterns

## Creational
  - Abstract Factory
    - Our project contains a sort of Abstract Factory using a family of lines. The relationship lines and arrowhead shapes are both lines that come from the same draw method. They each have their individual lists and act in different ways but are both inherently line objects. The arrowhead shape is dependent of the relationship line.
  - Adapter
    - Our GUI environment utilizes the adapter design pattern when it comes to using the legacy methods from the model, class and other legacy class files. The new application(GUI/GUIView) uses the wrapper(GUIController) to execute new behaviors and create new objects using the legacy methods found in the model, class, attributes, and relationship class files. 

## Structural 
  - Composite
    - Objects in a small tree structure can be found in the Attribute section of the UML Editor. An Attribute can either be a Field, a Method, or a Parameter. They all have similar behaviors, but act a little different in their execution. So they are all Attribute objects that branch off and act in their own way.

## Behavioral
  - Memento
    - A stack of saved states is how our Undo/Redo feature works. Each time an action is made, a state is created and put on the stack. Undo will go backwards to the previous state of the Editor. Redo will move forward to the next state after using Undo.
  - Observer
    - This is the View of our project. Are project design is Based on MVC(Model, View, Controller). The View, registered with the controllers, will show the user the data they are using. The controller will wait for an event to occur and act upon it to update the Model and the View.

###### Authors: Aaron Katz, Logan Santee, Andrew Foster, Ryan Ganzke, Max Moyer  
