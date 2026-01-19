package pt.ulusofona.lp2.greatprogrammingjourney;

public class ExceptionAbyss extends Abyss {
    public ExceptionAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) { // Tool 3: Exception Handling
            return "Exception anulado por Exception Handling";
        }
        player.move(-2);
        return "Exception! Recuas 2 casas.";
    }
}