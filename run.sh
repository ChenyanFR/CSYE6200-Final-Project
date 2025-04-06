#!/bin/bash

# Set JavaFX path
JAVAFX_PATH="/Users/zhuchenyan/JavaFX-SDK/javafx-sdk-21.0.6/lib"

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile the project
echo "Compiling Java files..."
find src -name "*.java" > sources.txt
javac -d bin --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml @sources.txt

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Running the application..."
    java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp bin main.MainApp
else
    echo "Compilation failed!"
    exit 1
fi

# Clean up
rm sources.txt 