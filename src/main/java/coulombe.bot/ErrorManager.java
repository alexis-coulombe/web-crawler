package coulombe.bot;

enum Errors {
    INVALID_URL,
    INVALID_URL_FORMAT,
    INVALID_ARGS_LENGTH,
    INVALID_INT,
    INVALID_INT_SIZE,
    INVALID_DIR,
    INVALID_DIR_PERM
}

public class ErrorManager {

    public static void printError(Errors type, String info){
        switch(type){
            case INVALID_URL: {
                System.out.println(info + " is not a valid URL.");
                break;
            }
            case INVALID_URL_FORMAT: {
                System.out.println(info + " is not a valid URL. (format)");
                break;
            }
            case INVALID_ARGS_LENGTH: {
                System.out.println("You must specify the arguments.");
                break;
            }
            case INVALID_INT_SIZE: {
                System.out.println(info + " must be greater than 0.");
                break;
            }
            case INVALID_INT: {
                System.out.println(info + " is not a valid integer.");
                break;
            }
            case INVALID_DIR: {
                System.out.println(info + " is not a valid repository. ");
                break;
            }
            case INVALID_DIR_PERM: {
                System.out.println("You don't have the right on this repository: " + info);
                break;
            }
        }

        exit();
    }

    /**
     * Print syntax error and close the program
     */
    public static void exit() {
        System.out.println("Syntax: \n" + "1) Search depth which is a positive integer.\n"
                + "2) Start url.\n"
                + "3) Save repository.\n");

        System.exit(1);
    }
}
