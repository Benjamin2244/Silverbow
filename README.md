# Silverbow
A collection of Java programs

(Intellij)
Steps for setup:

1)
- Download JavaFX SDK https://openjfx.io/openjfx-docs/
- Clone the project
- Add SDK library
  - File -> Project Structure -> Libraries -> Add '+' -> Java
  - Add the 'lib' folder that was downloaded with the SDK
  - Apply -> Ok
- Add VM options
  - Windows = "--module-path "\path\to\javafx-sdk-18.0.2\lib" --add-modules javafx.controls,javafx.fxml"
  - Linux/Mac = "--module-path /path/to/javafx-sdk-18.0.2/lib --add-modules javafx.controls,javafx.fxml"
  - Run -> Edit Configurations
  - Modify options -> Add VM options
  - use the path above in the VM options section
  - Replace "/path/to/javafx-sdk-18.0.2/lib" to the file path of the lib in the SDK
 - Within Edit Configurations
  - Change the name to soenthing appropriate
  - Silverbow.MainView
Use this video as a guide: https://youtu.be/Ope4icw6bVk
