package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static String tasksFile = "tasks.csv";
    public static Scanner file = new Scanner(tasksFile);

    public static void main(String[] args) {

        fileLoaderConverter(tasksFile);
        System.out.println(ConsoleColors.BLUE + "Welcome in TaskManager!" + "\n" + ConsoleColors.RESET);

        mainMenu(tasksFile);

    }

    public static String[][] fileLoaderConverter(String file) {

        Path path = Paths.get(file);
        if (!Files.exists(path)) {
            System.out.println(ConsoleColors.RED + "File not exist." + ConsoleColors.RESET);
            System.exit(0);
        }

        String[][] fileArr = null;

        try {
            List<String> strings = Files.readAllLines(path);
            fileArr = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                System.arraycopy(split, 0, fileArr[i], 0, split.length);
            }
        } catch (IOException e) {
            System.out.print(ConsoleColors.RED + "File not found..." + ConsoleColors.RESET);
        }

        return fileArr;
    }

    public static void mainMenu(String file) {

        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        System.out.println("add" + "\n" + "remove" + "\n" + "list" + "\n" + "exit" + "\n");

        try (Scanner scanner = new Scanner(System.in)) {

            String choose = scanner.next();

            switch (choose) {
                case "add":
                    System.out.println();
                    addToFile();
                    break;

                case "remove":
                    System.out.println();
                    removeFromFile(file);
                    break;

                case "list":
                    System.out.println();
                    readList(file);
                    break;

                case "exit":
                    System.out.println();
                    exitProgram();
                    break;

                default:
                    System.out.println(ConsoleColors.RED + "Wrong command..." + ConsoleColors.RESET);
                    mainMenu(tasksFile);
            }
        }
    }

    public static void addToFile () {

        Path path1 = Paths.get(tasksFile);

        userTaskDescription(path1);

        userDateInput(path1);

        userInputTrueFalse(path1);

        System.out.println();
        System.out.println(ConsoleColors.GREEN_BOLD + "Task added successfully." + ConsoleColors.RESET);
        choiceYesNoAdd();

    }

    private static void userTaskDescription(Path path1) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Task description: " + ConsoleColors.RESET);
        String userDescription = scanner.nextLine();

        if (userDescription.isBlank()) {
            System.out.println(ConsoleColors.RED + "No description." + ConsoleColors.RESET + " Please describe your task.");
            userTaskDescription(path1);
        } else if (userDescription.contains(",")) {
            System.out.println(ConsoleColors.RED + "Illegal input ',' " + ConsoleColors.RESET + "- try again.");
            userTaskDescription(path1);
        } else {
            try {
                Files.writeString(path1,userDescription + ", ", StandardOpenOption.APPEND);
            } catch (IOException ex) {
                System.out.println(ConsoleColors.RED + "Can't write into file." + ConsoleColors.RESET);
            }
        }
    }

    private static void userDateInput(Path path1) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Task due date: " + ConsoleColors.RESET);
        String userDate = scanner.nextLine();

        if (userDate.isBlank()) {
            try {
                Files.writeString(path1, userDate.strip().replaceAll(userDate.strip(), "No due date") + ", ", StandardOpenOption.APPEND);
            } catch (IOException ex) {
                System.out.println("Can't write into file.");
            }
        } else if (userDate.contains(",")) {
            System.out.println(ConsoleColors.RED + "Illegal input ','" + ConsoleColors.RESET + " - try again.");
            userDateInput(path1);
        } else {
            try {
                Files.writeString(path1, userDate.strip() + ", ", StandardOpenOption.APPEND);
            } catch (IOException ex) {
                System.out.println("Can't write into file.");
            }
        }
    }

    private static void userInputTrueFalse(Path path1) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Important true/false: " + ConsoleColors.RESET);
        String userPriority = scanner.nextLine();

        if (userPriority.equals("true")) {
            try {
                Files.writeString(path1, userPriority + "\n", StandardOpenOption.APPEND);
            } catch (IOException ex) {
                System.out.println("Can't write into file.");
            }

        } else if (userPriority.equals("false")) {
            try {
                Files.writeString(path1, userPriority + "\n", StandardOpenOption.APPEND);
            } catch (IOException ex) {
                System.out.println("Can't write into file.");
            }
        } else if (userPriority.contains(",")) {
            System.out.println(ConsoleColors.RED + "Illegal input ','" + ConsoleColors.RESET + " - try again.");
            userInputTrueFalse(path1);
        } else {
            System.out.println(ConsoleColors.RED + "Wrong command, try again. " + ConsoleColors.RESET + "Type " + ConsoleColors.GREEN + "true" + ConsoleColors.RESET + "/" + ConsoleColors.GREEN + "false: " + ConsoleColors.RESET);
            userInputTrueFalse(path1);
        }
    }

    private static void choiceYesNoAdd() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to add another task? " + ConsoleColors.RESET + "Type " + ConsoleColors.GREEN +  "y/n: " + ConsoleColors.RESET);
        String yesNo = scanner.nextLine();

        if (yesNo.equals("y")) {
            System.out.println();
            addToFile();
        } else if (yesNo.equals("n")) {
            System.out.println();
            mainMenu(tasksFile);
        } else {
            System.out.println(ConsoleColors.RED + "Wrong command, try again." + ConsoleColors.RESET);
            choiceYesNoAdd();
        }
    }

    private static void choiceYesNoRemove() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to remove another task?" + " Type " + ConsoleColors.GREEN +  "y/n: " + ConsoleColors.RESET);
        String yesNo = scanner.nextLine();

        if (yesNo.equals("y")) {
            System.out.println();
            removeFromFile(tasksFile);
        } else if (yesNo.equals("n")) {
            System.out.println();
            mainMenu(tasksFile);
        } else {
            System.out.println(ConsoleColors.RED + "Wrong command, try again." + ConsoleColors.RESET);
            choiceYesNoRemove();
        }
    }

    public static void removeFromFile (String file) {

        //displaying tasks for deletion
        Path path = Paths.get(file);
        int i = 0;
        try {
            for (String line : Files.readAllLines(path)) {
                System.out.println(i + " " + line);
                i++;
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "File not found..." + ConsoleColors.RESET);
        }

        System.out.println();

        //choosing index to delete
        Scanner scanner = new Scanner(System.in);

        System.out.println(ConsoleColors.BLUE + "Choose an index of a task to remove " + ConsoleColors.RESET + ConsoleColors.GREEN + "(0" + "-" + (fileLoaderConverter(tasksFile).length-1) + "): " + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Choose " + ConsoleColors.GREEN + "-1" + ConsoleColors.RESET + ConsoleColors.BLUE + " to return to Main Menu " + ConsoleColors.RESET);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print(ConsoleColors.RED + "Invalid task index! " + ConsoleColors.RESET + "Choose from " + ConsoleColors.GREEN + "(0" + "-" + (fileLoaderConverter(tasksFile).length-1) + "): " + ConsoleColors.RESET);
            System.out.println();
        }
        int taskIndex = scanner.nextInt();

        if (taskIndex > (fileLoaderConverter(tasksFile).length-1) || taskIndex < -1) {
            System.out.println(ConsoleColors.RED + "Wrong index number, try again." + ConsoleColors.RESET);
            System.out.println();
            removeFromFile(file);
        } else if (taskIndex == -1) {
            System.out.println();
            mainMenu(tasksFile);
        } else {
            try {
                List<String> strings = Files.readAllLines(path);
                strings.remove(taskIndex);
                Files.write(path, strings);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println();
            System.out.println(ConsoleColors.GREEN_BOLD + "Task deleted successfully." + ConsoleColors.RESET);
            choiceYesNoRemove();
        }



    }

    public static void readList (String file){

        Path path = Paths.get(file);
        int i = 0;
        try {
            for (String line : Files.readAllLines(path)) {
                System.out.println(i + " " + line);
                i++;
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "File not found..." + ConsoleColors.RESET);
        }
        System.out.println();
        mainMenu(tasksFile);
    }

    public static void exitProgram () {
        System.out.println(ConsoleColors.BLUE + "Thanks for using TaskManager!" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD + "Program ended." + ConsoleColors.RESET);
        System.exit(0);
    }

}