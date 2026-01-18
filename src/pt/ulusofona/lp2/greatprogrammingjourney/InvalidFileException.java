package pt.ulusofona.lp2.greatprogrammingjourney;

// 1. Herdar de "Exception" e não de "ExceptionAbyss"
public class InvalidFileException extends Exception {

    public InvalidFileException(String message) {
        // 2. Agora o super(message) funciona porque a classe Exception aceita uma mensagem de erro
        super(message);
    }
}