package pt.ulusofona.lp2.greatprogrammingjourney;

public class FileNotFoundAbyss extends Abyss {
    public FileNotFoundAbyss(int position) {
        super(3, position, "FileNotFoundException");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) { // ID 3 = Exception Handling
            return "Trataste a FileNotFoundException! Estás salvo.";
        }
        player.move(-3);
        return "FileNotFoundException! Recua 3 casas";
    }
}