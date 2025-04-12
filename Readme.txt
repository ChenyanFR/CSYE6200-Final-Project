
** Instructions **

*Requirements*
Java JDK 11 or later
JavaFX SDK (downloaded and unZipped)
Eclipse IDE for Java Developers

*Folder Structure*

- src/                : Java source files (MVC etc)
- JavaFX             (Default version : javafx-sdk-21)
- JRE System Library (Default version : Java SE 23.0.01) : lib JavaFX SDK JARs

*Detail Instructions to run*

Instruction A or B
- You can follow either Instruction A or Instruction B

Instruction A 

1. Open Eclipse IDE.

2. Import the project
   - File > Import > General > Existing Projects into Workspace
   - Select the root directory and browse folder of this project
   - Click "Finish"
   - !!IMPORTANT!! 
     **Make sure the project structure is under this order** 
     CollegeSubletApp [CollegeSubletApp main]
       - JRE System Library [Java SE 23.0.1 [23.0.1]] -> you should be able to run with Java SE 21.0.1

3. Configure JavaFX Libraries
   - Right-click the project > Build Path > Configure Build Path
   - Go to the 'Libraries' tab
   - Click 'Add External JARs'
   - Select all .jar files from the JavaFX SDK's /lib folder 
   - !!IMPORTANT!! 
     **Make sure you have JavaFX and JRE System Library [Java SE 23.0.1] under ModularPath**

4. Add VM Arguments
   - Go to Run or Run As > Run Configurations
   - Select main > MainApp class 
   - !!IMPORTANT!! 
     **Make sure your project is "CollegeSubletApp" and main is "main.MainApp"
   - Go to 'Arguments' tab -> 'VM arguments'
     Paste : --module-path /Users/YourPathTo/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
     Uncheck the 3 boxes below VM arguments
   - Go to JRE and see the execution environment is Java SE 23.0.1
     
5. Run the App:
   - Right-click on MainApp.java
   - Click 'Run As' > 'Java Application'
   
   Instruction B
   
1. Clone the project from GitHub

2. Open Terminal and navigate to the directory where you want to store the project

3. git clone "https://github.com/ChenyanFR/CSYE6200-Final-Project.git"

4. Open Eclise IDE

5. File > Import > Git > Projects from Git 

6. Select Clone URL -> 
   Next and paste on terminal : git clone "https://github.com/ChenyanFR/CSYE6200-Final-Project.git"

7. Set your branch to the main (it is set as main branch by default) 
   paste on terminal : "git checkout main" 
   
8. !!IMPORTANT!! the project structure must be as below: (otherwise it will not be able to locate the project)
     CollegeSubletApp [CollegeSubletApp main]
     - src /
     - JRE System Library [Java SE 23.0.1]
     - JavaFX
     (refer to Instruction A.3 and A.4 concerning configuration of JavaFX and setup of VM arguments)
     - Run the Application
