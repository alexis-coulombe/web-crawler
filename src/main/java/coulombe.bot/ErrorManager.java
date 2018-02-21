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
                System.out.println(info + " n'est pas une url valide ou n'existe pas.");
                break;
            }
            case INVALID_URL_FORMAT: {
                System.out.println(info + " n'est pas une url valide. (format)");
                break;
            }
            case INVALID_ARGS_LENGTH: {
                System.out.println("Vous n'avez pas le bon nombre d'arguments.");
                break;
            }
            case INVALID_INT_SIZE: {
                System.out.println(info + " doit être supérieur à 0.");
                break;
            }
            case INVALID_INT: {
                System.out.println(info + " n'est pas un nombre entier valide.");
                break;
            }
            case INVALID_DIR: {
                System.out.println(info + " n'est pas une répertoire valide ou n'existe pas. ");
                break;
            }
            case INVALID_DIR_PERM: {
                System.out.println("Vous n'avez pas les droits d'écrire ou de lire le fichier: " + info);
                break;
            }
        }

        exit();
    }

    public static void exit() {
        System.out.println("Syntaxe: \n" + "1) La profondeur d'exploration qui doit être un nombre entier positif.\n"
                + "2) L\'url de départ qui est la première page à explorer. Il doit s'agir d'une adresse valide (le format est correct et il y a une page qui y correspond)\n"
                + "3) Le répertoire où écrire les copies locales des fichiers explorés. Le dossier doit être accessible et on doit pouvoir y écrire.\n");

        System.exit(1);
    }
}
