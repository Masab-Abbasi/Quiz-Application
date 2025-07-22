import java.util.*;
import java.io.*;
public class QuizApplication{

    /** Global Variables **/
    static Scanner input = new Scanner(System.in);
    static boolean isUserLogin = false;
    static boolean isAdminLogin = false;
    static int age;
    static String currentUser ;

    public static boolean isStrong(String password){

        if (password.length() < 8){
            System.out.println("Password Must be 8 Characters");
            return false;
        }

        if(password.contains(" ")){
            System.out.println("Spaces are not Allowed in Password");
            return false;
        }

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLetter = false;
        boolean hasSpecial = false;

        //loop for checking the password
        for(int i = 0 ; i < password.length() ; i++ ){
            //storing character of password to check
            char character = password.charAt(i);
            if(Character.isDigit(character)){
                hasDigit = true;
            }
            if(Character.isLetter(character)){
                hasLetter = true;
            }
            if(Character.isUpperCase(character)){
                hasUpper = true;
            }
            if (!(Character.isLetterOrDigit(character))) {
                hasSpecial = true;
            }
        }
        if(!hasUpper){
            System.out.println("Password must contain at-least one Upper case Letter");
        }
        if(!hasLetter){
            System.out.println("Password must contain at-least one Letter");
        }
        if(!hasDigit){
            System.out.println("Password must contain at-least one Digit");
        }
        if(!hasSpecial){
            System.out.println("Password must contain at-least one special Character ");
        }
        return hasDigit && hasLetter && hasSpecial && hasUpper;
    }

    public static boolean duplicateUsers(String userName ){

        //opening the registration file
        File file = new File("registrations.txt");
        try{
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                //storing first line of file in a variable
                String line = reader.nextLine();

                // breaking element into parts and then storing in array
                String[] registrationDetails = line.trim().split("\\|");

                if(userName.equals(registrationDetails[0])){
                    return true;
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File Does Not Exists!");
        }
        return false;
    }

    public static void registerUser() {
        //clearing buffer
        input.nextLine();

        //declaring & initializing variables
        String userName = "";
        String password = "";

        boolean isRegistered = false;
        while (!isRegistered) {
            //prompting user to enter user-name & password
            System.out.print(" >> Enter Your Name: ");
            userName = input.nextLine();

            if(userName.isEmpty()){

                System.out.println(" > You Have Not Entered Anything");
                //skipping next statements
                continue;
            }

            //checking if user-name already taken
            if(duplicateUsers(userName)){
                System.out.println("> User Name Already Taken");
                System.out.println("  Kindly Use Another User-Name");
                continue;
            }
            boolean isValid = false;
            while (!isValid) {
                try {
                    System.out.print(" >> Enter Your Age: ");
                    age = input.nextInt();
                    // getting out of loop after receiving valid input
                    isValid = true;
                } catch (InputMismatchException e) {
                    System.out.println("> Only Integers are Accepted....Try Again");
                    input.nextLine();
                }
            }

            //validating age
            if( age < 15 || age > 100){
                input.nextLine();
                System.out.println("> You Can't Take This Quiz");
                System.out.println(" Good Bye!");

                System.out.print("> Press Enter to Exit: ");
                input.nextLine();
                return;
            }
            //clearing buffer
            input.nextLine();

            // flag for password strength
            boolean strong = false;
            while (!strong) {
                System.out.print(" >> Enter a strong Password: ");
                password = input.nextLine();

                if (isStrong(password)) {
                    strong = true;
                    //if password is strong then exit the loop
                    System.out.println("***** <Registered Successfully> *******");
                    isRegistered = true;
                }
            }
        }
        /**
         Writing Registration details to registration after the user
         has registered Successfully
         **/
        try {
            // opening file for storing registration records
            FileWriter writer = new FileWriter("registrations.txt", true);
            writer.write(userName + "|" + password + "|" + age +  "\n");
            //closing writer
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("File Does Not Exist");
        }
        catch (IOException e){
            System.out.println("Error While Writing to the file");
        }
    }

    public static void loginUser() {
        //clearing buffer
        input.nextLine();
        int attempts = 3;
        boolean haveAttempts = true;
        while (haveAttempts) {
            System.out.print(">> UserName: ");
            String userName = input.nextLine();
            boolean isValidAge = false;
            while (!isValidAge) {
                try {
                    System.out.print(">> Age: ");
                    age = input.nextInt();
                    isValidAge = true;
                } catch (InputMismatchException e) {
                    System.out.println("Only Integers are Accepted....Try Again");
                    input.nextLine();
                }
            }

            //clearing buffer
            input.nextLine();
            System.out.print(">> Password: ");
            String password = input.nextLine();

            File file = new File("registrations.txt");

            try  {
                Scanner reader = new Scanner(file);
                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    String[] details = line.trim().split("\\|"); // same like details = {1,2,3,4}
                    //storing age in registration file in a variable so to verify
                    int registeredAge = Integer.parseInt(details[2]);

                    if (userName.equals(details[0]) && password.equals(details[1]) &&
                            age == registeredAge) {
                        //when login successfully the that definitely is current user
                        currentUser = userName;
                        // updating global variable
                        isUserLogin = true;
                        haveAttempts = false;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
            if(!isUserLogin){
                attempts--;
            }
            if(attempts == 0){
                System.out.println(" -------------------------------------");
                System.out.println(" |    Better Luck Next Time!!         |");
                System.out.println(" | You Have Wasted All Your Attempts  |");
                System.out.println(" -------------------------------------");
                // when user is unable to login within given attempts
               haveAttempts = false;
               continue;
            }
            if (isUserLogin) {
                System.out.println("******** User Login Successful ********");
                haveAttempts = false;
            } else {
                System.out.println("Invalid Credentials. Try Again!");
                System.out.println("> Attempts left: " + attempts);
            }
        }
    }

    public static void loginAdmin() {
        //clearing buffer
        input.nextLine();

        int attempts = 3;

        do{
            System.out.print(">> AdminName: ");
            String adminName = input.nextLine();
            System.out.print(">> Password: ");
            String password = input.nextLine();

            //opening the registration file to read data so to verify login
            File file = new File("adminlogin.txt");
            try {
                Scanner reader = new Scanner(file);
                while (reader.hasNext()) {
                    //storing line from the file in a variable
                    String line = reader.nextLine();
                    String[] details = line.trim().split("\\|");

                    //checking if the login details matches
                    if (adminName.equals(details[0]) && password.equals(details[1])) {
                        isAdminLogin = true;
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Can not Find the File");
            }
            if (isAdminLogin) {
                System.out.println("********    Admin Login Successfully     ********");

                //getting out of loop if login successfully
                break;
            }

            else {
                System.out.println("Invalid Credentials  ....... Try Again!");
                System.out.println("You Have " +(attempts - 1) + ((attempts > 2)?" attempts":" attempt") + " left");
            }
            attempts --;
        }while (attempts != 0 );
    }

    public static void showUserQuizMenu() {
        System.out.println("=================================================");
        System.out.println("|     <<  Welcome to User Quiz Section  >>      |");
        System.out.println("=================================================");
        System.out.println("|  1: Take Quiz                                 |");
        System.out.println("|   -----------------------------------------   |");
        System.out.println("|  2: View Results                              |");
        System.out.println("|   -----------------------------------------   |");
        System.out.println("|  3: Exit                                      |");
        System.out.println("=================================================");

        //variable that will store user selected option
        int userMenuOption = 0 ;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(">> Kindly Select an Option: ");
                userMenuOption = input.nextInt();

                if (userMenuOption < 1 || userMenuOption > 3){
                    System.out.println("Invalid Option....Try Again");
                    continue;
                }
                //getting out of loop when valid input received
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("> Only Integers are Allowed! .... Try Again");
                input.nextLine();
            }
        }
        //checking what user selected from the MENU
        switch (userMenuOption) {

            case 1 -> startQuiz();

            case 2 -> viewResults();

            case 3 -> System.out.println("> Logging Out......");

        }
    }

    public static void showAdminQuizMenu() {

        System.out.println("==================================================");
        System.out.println("|           << Welcome to Admin Panel >>         |");
        System.out.println("=================================================");
        System.out.println("|   1: Add Quiz                                  |");
        System.out.println("|   -----------------------------------------    |");
        System.out.println("|   2: View All Results                          |");
        System.out.println("|   -----------------------------------------    |");
        System.out.println("|   3: Edit Quiz                                 |");
        System.out.println("|   -----------------------------------------    |");
        System.out.println("|   4: Delete Quiz                               |");
        System.out.println("|   -----------------------------------------    |");
        System.out.println("|   5: Exit                                      |");
        System.out.println("==================================================");

        int adminChoice = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(">> Kindly Select an option: ");
                adminChoice = input.nextInt();
                if(adminChoice < 1 || adminChoice > 5){
                    System.out.println("> Invalid Choice.... Try Again");
                    continue;
                }
                //clearing buffer
                input.nextLine();
                //getting out of loop if correct input is provided
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("> Only Integers are Accepted.....Try Again");
                input.nextLine();
            }
        }
        switch (adminChoice) {

            case 1 -> addQuiz();

            case 2 -> viewAllResults();

            case 3 -> editQuiz();

            case 4 -> deleteQuiz();

            case 5 -> {
                System.out.println("Logging Out....");
                isAdminLogin = false;
            }
        }
    }

    public static void subjects(){
        File file = new File("Subjects.txt");
        try{
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("File Does Not Exists");
        }
    }

    public static void startQuiz() {
        char userWants;
        //checking what difficulty level is selected by the user
        String difficulty = difficultyLevel().toUpperCase();

        //setting timer for the quiz based on the difficulty
        int totalTimeInSeconds = switch (difficulty.toUpperCase()){
            //setting quiz time 2.5 minutes if user selected easy
            case "EASY" -> 150;
            //setting quiz time 5 minutes if user selected medium
            case "MEDIUM" -> 5 * 60;
            //setting quiz time 10 minutes if user selected hard
            case "HARD" -> 10 * 60;
            //setting timer randomly between 1 - 10
            case "SURPRISE" -> (int)(1 + Math.random() * 10 ) * 60;
            default -> 300;
        };

        if(difficulty.equals("SURPRISE")){
            takeQuiz("SurpriseQuiz.txt","SurpriseKey.txt",currentUser,totalTimeInSeconds);
            System.out.print(">> Want to Go Back to Menu (Y/N)?");
            userWants = input.next().toUpperCase().charAt(0);
            if(userWants == 'Y'){
                showUserQuizMenu();
                return;
            }
            else {
                System.out.println("Logging Out.....");
                return;
            }
        }

        // if user selects surprise then quiz menu will not be shown to the user
        System.out.println("===========   QUIZ MENU    =======================");
        subjects();
        System.out.println("==================================================");

        //variable that will store what user selected from the QUIZ-MENU
        String subjectName;
        input.nextLine();
        System.out.print("Enter the Subject Name to Attempt Quiz: ");
        subjectName = input.nextLine();

        String questionFile = subjectName + difficulty + "Quiz.txt";
        String keyFile = subjectName + difficulty + "Key.txt";

        takeQuiz(questionFile,keyFile,currentUser,totalTimeInSeconds);

        System.out.println("Do You Want to Go to back to MENU then press Y otherwise " +
                "press any other key to exit");
        userWants = input.next().toUpperCase().charAt(0);
        if (userWants != 'Y') {
            System.out.println("Logging Out........");
        }
        else {
            showUserQuizMenu();
        }
    }
    //method to get total question that will help in compiling the result
    public static int lineCounter(String File){
        int counter = 0;
        File file = new File(File);
        try{
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                counter ++;
            }
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("File Does Not Exists");
        }
        return counter;
    }

    public static void takeQuiz(String questionFile , String key,String userName, int totalTimeInSeconds) {
        int score = 0;
        //getting how many total questions are there in the file
        int questions = lineCounter(questionFile);

        File file = new File(questionFile);
        File file2 = new File(key);

        try {
            //for writing results in user file
            FileWriter writer = new FileWriter(userName + ".txt", true);
            //print writer for formatting data in a proper way in user result's file
            PrintWriter writer1 = new PrintWriter(writer);

            //for writing result in a file accessible by admin
            FileWriter resultWriter = new FileWriter("AllResults.txt",true);
            //print writer for formatting all results file
            PrintWriter writer2 = new PrintWriter(resultWriter);

            // for reading questions
            Scanner reader = new Scanner(file);
            // for reading key
            Scanner keyReader = new Scanner(file2);

            // Start timer
            long startTime = System.currentTimeMillis();
            // variable to update time to user
            long updateTime = 0;

            //giving user message that how much time user have to attempt the quiz
            System.out.println("=======================  NOTE!    =======================");
            System.out.println("| You Have " + (totalTimeInSeconds/60) + " minutes and "
                    + (totalTimeInSeconds % 60) + " seconds to complete the Quiz |");
            System.out.println("==========================================================");
            while (reader.hasNext() && keyReader.hasNext()) {

                //storing question from file in a variable line
                String line = reader.nextLine();

                //breaking data of line in parts using  "$" and storing in an array
                String[] mcq = line.trim().split("\\$");

                //getting correct answer of the question from the key
                char correctAnswer = keyReader.next().charAt(0);


                for (String question : mcq) {
                    System.out.println(question);
                }

                char userAnswer = ' ';
                boolean validAnswer = false;
                while (!validAnswer) {
                    System.out.print("Enter Your Answer: ");
                    userAnswer = input.next().toUpperCase().charAt(0);
                    if(userAnswer != 'A' && userAnswer != 'B'
                            && userAnswer != 'C'&& userAnswer != 'D'){
                        System.out.println("Select option(A,B,C or D)");
                    }
                    else {
                        //clearing buffer
                        input.nextLine();
                        validAnswer = true;
                    }
                }
                if (userAnswer == correctAnswer) {
                    //increasing score when answer is correct
                    score++;
                }

                //subtracting current time with start time to get time passed
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

                //checking how much time is left
                long timeLeft = totalTimeInSeconds - elapsedTime;

                if (timeLeft <= 0) {
                    System.out.println("\n Time's up! Quiz Ended.");
                    break;
                }

                //Showing user time left after each 30s passed
                if (elapsedTime - updateTime >= 30) {
                    System.out.println(" Time Left: " + (timeLeft / 60) + " minutes "
                            + (timeLeft % 60) + " seconds");
                    //updating the time
                    updateTime = elapsedTime;
                }
            }

            System.out.println("You Scored " + score + " out of " + questions);

            //writing data in user results file in a specific format(for user view)
            writer1.println("========================================================");
            writer1.printf("|-> %-8s | Subject: %-15s | Scored: %s/%d |\n",userName,
                    questionFile.substring(0,questionFile.indexOf('Q')), score,questions);
            writer1.println("========================================================");


            //writing data in allResults file in a specific format (for admin view)
            writer2.println("=================================================================");
            writer2.printf("|-> Student: %-8s | Subject: %-15s | Scored: %s/%d |\n",userName,
                    questionFile.substring(0,questionFile.indexOf('Q')), score,questions);
            writer2.println("=================================================================");

            //closing all resources
            writer.close();
            writer1.close();
            reader.close();
            keyReader.close();
            resultWriter.close();
            writer2.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found Exception");
        } catch (IOException e) {
            System.out.println("Error while reading/writing the file");
        }
    }

    public static void viewResults() {
        char userWants;
        //opening the file in which result is stores
        File file = new File(currentUser + ".txt");

        //checking if the record of the user exists or not
        if(!(file.exists())){
            System.out.println("> There is no Previous Record of " + currentUser);
            System.out.println("*THANKS!*");

            System.out.print(">> Do You Want to go back to USER-MENU(Y/N)? : ");
            userWants = input.next().toUpperCase().charAt(0);
            if(userWants != 'Y'){
                System.out.println("Logging Out..........");
            }
            else {
                showUserQuizMenu();
            }
            return;
        }
        try{
            //creating scanner object to read from the  results file
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File Does Not Exist!");
        }

        System.out.print(">> Do You Want to Go to back to MENU then press Y otherwise " +
                "press any other key to exit: ");
        userWants = input.next().toUpperCase().charAt(0);
        if (userWants != 'Y') {
            System.out.println("Logging Out........");
            isUserLogin = false;
        }
        else {
            showUserQuizMenu();
        }
    }

    public static void viewAllResults(){
        File file = new File("AllResults.txt");
        try{
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();

                System.out.println(line);
            }
        }catch (FileNotFoundException e){
            System.out.println("File Does not Exists");
        }
        catch (IOException e){
            System.out.println("Error While Reading The File");
        }


        System.out.print(">> Do You Want to Go to back to MENU then press Y otherwise " +
                "press any other key to exit: ");
        char adminWants = input.next().toUpperCase().charAt(0);
        if (adminWants != 'Y') {
            System.out.println("Logging Out........");
            isAdminLogin = false;
        }
    }

    public static void createFile(String questionFile, String keyFile){
        File file1 = new File(questionFile);
        File file2 = new File(keyFile);
        try{
            FileWriter writer1 = new FileWriter(file1,true);
            FileWriter writer2 = new FileWriter(file2,true);

            writer1.close();
            writer2.close();
        }catch (IOException e){
            System.out.println("Error While Creating Files");
        }

    }

    public static void addQuiz() {
        // Showing subjects list to the admin
        System.out.println("===========    << SUBJECTS >>  ===================");
        subjects();
        System.out.println("==================================================");

        System.out.print("Enter the Subject Name to Add: ");
        String subjectName = input.nextLine();

        //loop for deleting all related file of the chosen subject
        for(int i = 0 ; i < 3 ; i++){
            if(i == 0){
                String questionFile = subjectName +  "Easy" + "Quiz.txt";
                String keyFile = subjectName +  "Easy" + "Key.txt";
                // creating the files
                createFile(questionFile,keyFile);
            }
            else if (i == 1){
                String questionFile = subjectName +  "Medium" + "Quiz.txt";
                String keyFile = subjectName +  "Medium" + "Key.txt";
                createFile(questionFile,keyFile);
            }
            else{
                String questionFile = subjectName +  "Hard" + "Quiz.txt";
                String keyFile = subjectName +  "Hard" + "Key.txt";
                createFile(questionFile,keyFile);
            }
        }

        /** Updating Subject File  **/

        int lineNumber = lineCounter("Subjects.txt");
        try {
            //creating file writer Object to append data
            FileWriter writer = new FileWriter("Subjects.txt",true);

            //creating Print Writer so we can format data in a specific way
            PrintWriter writer1 = new PrintWriter(writer);

            //counting line number in subject file
            lineNumber ++;

            writer1.printf("|  %d: %-43s|\n",lineNumber,subjectName);

            System.out.println(subjectName + "- Added to the Subjects Successfully!");

            writer.close();
            writer1.close();
        }catch (FileNotFoundException e){
            System.out.println("> File Does Not exists");
        }
        catch (IOException e){
            System.out.println(">> Error Occurred Writing or creating the files");
        }

        char adminWants;
        System.out.print("> Do You Want to Go to back to MENU then press Y otherwise " +
                "press any other key to exit: ");
        adminWants = input.next().toUpperCase().charAt(0);
        if (adminWants != 'Y') {
            System.out.println("Logging Out........");
            isAdminLogin = false;
        }
    }

    public static void deleteFile(String quizFile , String keyFile){

        File file1 = new File(quizFile);
        File file2 = new File(keyFile);

        // deleting the files
        if(file1.delete() && file2.delete()){
            System.out.println(quizFile + " and " + keyFile + " deleted Successfully");
        }

    }

    public static void deleteQuiz(){

        System.out.println("===========    << SUBJECTS >>  ===================");
        subjects();
        System.out.println("==================================================");

        System.out.print(">> Enter the Subject Name to Delete: ");
        String subjectName = input.nextLine();

        // Declaring & initializing necessary variables
        String difficulty = "";
        String quizFile = "";
        String keyFile = "";
        //loop for deleting all related file of the chosen subject
        for(int i = 0 ; i < 3 ; i++){
            if(i == 0){
                difficulty = "EASY";
                quizFile = subjectName + difficulty + "Quiz.txt";
                keyFile = subjectName + difficulty + "Key.txt";
                //deleting related files
                deleteFile(quizFile,keyFile);
            }
            else if (i == 1){
                difficulty = "MEDIUM";
                quizFile = subjectName + difficulty + "Quiz.txt";
                keyFile = subjectName + difficulty + "Key.txt";

                deleteFile(quizFile,keyFile);
            }
            else {
                difficulty = "HARD";
                quizFile = subjectName + difficulty + "Quiz.txt";
                keyFile = subjectName + difficulty + "Key.txt";

                deleteFile(quizFile,keyFile);
            }

            /**
             As all The Files of the subject that admin selected are deleted so it is necessary
             to update the subject file & that we will do by removing the Subject Name that have been
             Deleted
             **/
            File subjects = new File("Subjects.txt");
            //creating temporary file that will keep the record of updated subject list
            File tempFile = new File("temp.txt");

            //variable that will store Sr. Number of Subject
            int subjectNumber = 1;
            try{
                //creating temporary file
                tempFile.createNewFile();

                Scanner reader = new Scanner(subjects);
                // Writing to Temp file
                PrintWriter writer = new PrintWriter(tempFile);

                // Reading the subjects file
                while (reader.hasNext()) {
                    String line = reader.nextLine();

                    if (line.contains(subjectName)) {
                        continue;
                    }
                    //extracting subject name & trim used to skip spaces
                    String subject = line.substring(line.indexOf(":" )+ 1,line.lastIndexOf("|") ).trim();

                    // writing to temporary file after removing the deleted subject
                    writer.printf("|  %d: %-43s|\n",subjectNumber,subject);
                    subjectNumber ++;

                }
                reader.close();
                writer.close();
            }catch (IOException e){
                System.out.println("Error while reading or writing to the file");
            }

            //now deleting the subject file & renaming temp file with subject file
            if(subjects.delete() ){
                tempFile.renameTo(subjects);
            }
            else {
                System.out.println("Error while updating the subjects file");
            }
        }

        char adminWants;
        System.out.print("Do You Want to Go to back to MENU then press Y otherwise " +
                "press any other key to exit: ");
        adminWants = input.next().toUpperCase().charAt(0);
        if (adminWants != 'Y') {
            System.out.println("Logging Out........");
            isAdminLogin = false;
        }
    }

    public static void editQuiz() {
        char wantToAddQuestion;
        System.out.println("============= <<<  EDIT QUIZ  >>> ================");
        System.out.println("--------------------------------------------------");
        System.out.println("|                    SUBJECTS                    |");
        System.out.println("--------------------------------------------------");
        subjects();
        System.out.println("==================================================");

        System.out.print(" >> Enter Subject Name to Edit Quiz: ");
        String quizSelection = input.nextLine();

        // Prompt admin to select a subject to edit
        System.out.println("=================================================");
        System.out.println("| 1: Easy                                       |");
        System.out.println("| 2: Medium                                     |");
        System.out.println("| 3: Hard                                       |");
        System.out.println("=================================================");


        int difficultyOption = 0 ;
        // Prompt admin to select difficulty level for editing quiz
        boolean isValid = false;
        while (!isValid){
            try{
                System.out.println();
                System.out.print("  >> Select a difficulty: ");
                difficultyOption = input.nextInt();
                if(difficultyOption <1 || difficultyOption>3){
                    System.out.println("Kindly Select a Valid Option");
                }
                else {
                    isValid = true;
                }
            }catch (InputMismatchException e){
                System.out.println("Only Integers Are Accepted.....Try Again");
            }
        }
        String difficulty = "";
        //matching admin choice
        switch (difficultyOption){
            case 1 -> difficulty = "Easy";
            case 2 -> difficulty = "Medium";
            case 3 -> difficulty = "Hard";
        }

        String subjectName = quizSelection + difficulty;

        try{
            //creating file writer object to edit the  question  & key file
            FileWriter questionWriter = new FileWriter(subjectName + "Quiz.txt", true);
            FileWriter keyWriter = new FileWriter(subjectName + "Key.txt",true);
            do {
                //prompting admin to enter the question to add
                System.out.println("Enter the Questions that you want to add along with the options: ");
                System.out.println("(In format: QS STATEMENT $A.   $B.   $C.    $D. )");
                input.nextLine();

                //storing question in a variable
                String question = input.nextLine();
                //storing questions in the file
                questionWriter.write(question + "\n");
                //prompting admin to enter the key for the asked question
                System.out.print("Now Enter the Key: ");
                char key = input.next().toUpperCase().charAt(0);
                //clearing buffer
                input.nextLine();
                //storing answer in the key file
                keyWriter.write(key + "\n");

                //asking admin if want to add more question , otherwise exit from loop
                System.out.print("Do You want to Add More Questions?(Y/N): ");
                wantToAddQuestion = input.next().toUpperCase().charAt(0);
            } while (wantToAddQuestion == 'Y');
            //closing resources
            System.out.println("*** Questions Added Successfully! ***");
            questionWriter.close();
            keyWriter.close();
        }
        catch (FileNotFoundException e){
            System.out.println(">> File Does Not exits");
        }
        catch (IOException e){
            System.out.println(">> Error while Writing to the file");
        }

        char adminWants;
        System.out.print("Do You Want to Go to back to MENU then press Y otherwise " +
                "press any other key to exit: ");
        adminWants = input.next().toUpperCase().charAt(0);
        if (adminWants != 'Y') {
            System.out.println("Logging Out........");
            isAdminLogin = false;
        }
    }

    public static String difficultyLevel() {
        System.out.println("==================================================");
        System.out.println("|   1: Easy                                      |");
        System.out.println("|   2: Medium                                    |");
        System.out.println("|   3: Hard                                      |");
        System.out.println("|   4: Surprise                                  |");
        System.out.println("==================================================");

        int optionSelected = 0;
        while (true) {
            try {
                System.out.print(">> Kindly Select a Difficulty Level[1-4]: ");
                optionSelected = input.nextInt();
                // getting out of loop if input is valid
                if (optionSelected >= 1 && optionSelected <= 4) {
                    break;
                }
                else {
                    System.out.print(">> Kindly Select Option 1, 2, 3, or 4: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("> Only Integers are Accepted....Try Again");
                // clear invalid input
                input.nextLine();
            }
        }

        String difficultySelected = "";
        switch (optionSelected) {
            case 1 -> difficultySelected = "Easy";
            case 2 -> difficultySelected = "Medium";
            case 3 -> difficultySelected = "Hard";
            case 4 -> difficultySelected = "Surprise";

        }
        return difficultySelected;
    }

    public static void userLoginMenu(){
        System.out.println("====================================================");
        System.out.println("|       << Welcome to User Login Section  >>       |");
        System.out.println("====================================================");
        System.out.println("|  1: Register                                     |");
        System.out.println("|   --------------------------------------------   |");
        System.out.println("|  2: Login                                        |");
        System.out.println("|   --------------------------------------------   |");
        System.out.println("|  3: Exit                                         |");
        System.out.println("====================================================");
        //variable that will store user's choice
        int userOption  = 0 ;
        //loop that will run till valid input is not given
        boolean isValid = false;
        while (!isValid){
            try {
                System.out.print("  >> Kindly Select an Option: ");

                userOption = input.nextInt();

                //input validation
                if(userOption < 1 || userOption > 3){
                    System.out.println(" > Invalid Option .... Try Again");
                    System.out.println();
                    //skipping next statements if invalid input
                    continue;
                }
                System.out.println();

                //getting out of loop if got the desired input
                isValid = true;
            }
            catch (InputMismatchException e){
                System.out.println(" > Only Integers are Accepted...... Try Again ");
                //clearing buffer
                input.nextLine();
            }
        }
        //checking which option user selected from the USER-MENU
        switch (userOption) {
            case 1 -> {
                registerUser();
                //returning if age is not valid
                if(age < 15 || age >100){
                    return;
                }
                else {
                    System.out.println("  ***** Kindly Enter Details to Now Login ******");
                    //after registration prompting user to login
                    loginUser();
                }
            }
            case 2 -> {
                loginUser();
            }
            case 3 -> {
                System.out.println("Logging Out......");
                return;
            }

        }
        //if user login successfully then show userQuizMenu
        if (isUserLogin) {
            showUserQuizMenu();
        }

    }

    public static void adminLoginMenu(){
        System.out.println("==================================================");
        System.out.println("|     << Welcome to Admin Login Section >>       |");
        System.out.println("==================================================");
        loginAdmin();

        while (isAdminLogin) {
            showAdminQuizMenu();
        }
    }

    public static void main(String[] args) {
        //declaring & initializing variables
        int userChoice = 0;
        //displaying welcome message & prompting user to register or login accordingly
        System.out.println("\n/==================================================\\");
        System.out.println("|                                                  |");
        System.out.println("|    !        <<   Q U I Z   A P P   >>        !   |");
        System.out.println("|    !        =========================        !   |");
        System.out.println("|    !        |      W E L C O M E     |       !   |");
        System.out.println("|    !        =========================        !   |");
        System.out.println("|    !                                         !   |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|                                                  |");
        System.out.println("|    !        +----------------------+         !   |");
        System.out.println("|    !        |   1. USER            |         !   |");
        System.out.println("|    !        |   2. ADMIN           |         !   |");
        System.out.println("|    !        +----------------------+         !   |");
        System.out.println("|    !                                         !   |");
        System.out.println("\\==================================================/");

        boolean isValid = false;
        while (!isValid) {
            try{

                System.out.print("\n  >> Select an Option[1-2]: ");
                userChoice = input.nextInt();

                //verifying whether user selected valid option
                if(userChoice != 1 && userChoice != 2){
                    System.out.println(" > Invalid Option.... Try Again");
                    continue;
                }
                //if input is valid then getting out of the loop
                isValid = true;
            }
            catch (InputMismatchException e){
                System.out.println(" > Only Integers are Accepted....... Try Again");
                //clearing buffer
                input.nextLine();
            }
        }

        // checking what user selected
        if (userChoice == 1) {
            userLoginMenu();
        }
        else  {
            adminLoginMenu();
        }
        //closing scanner input
        input.close();
    }
}