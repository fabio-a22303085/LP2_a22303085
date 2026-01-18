package pt.ulusofona.lp2.greatprogrammingjourney;

public class ExceptionAbyss extends Abyss {
    public ExceptionAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) { // Tool 3: Exception Handling
            return "Apanhaste a Exception com um Try-Catch! Estás salvo.";
        }
        player.move(-2);
        return "Exception! Recua 2 casas";
    }
}