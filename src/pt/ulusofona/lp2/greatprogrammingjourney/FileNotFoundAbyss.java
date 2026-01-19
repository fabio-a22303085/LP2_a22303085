package pt.ulusofona.lp2.greatprogrammingjourney;

public class FileNotFoundAbyss extends Abyss {
    public FileNotFoundAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) { // Tool 3: Exception Handling
            return "Trataste a FileNotFoundException! Estás salvo.";
        }
        player.move(-3);
        return "FileNotFoundException! Recua 3 casas";
    }
}