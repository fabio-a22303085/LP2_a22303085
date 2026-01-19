package pt.ulusofona.lp2.greatprogrammingjourney;

public class SyntaxErrorAbyss extends Abyss {
    public SyntaxErrorAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 4)) { // Tool 4: IDE
            return "Erro de Sintaxe anulado por IDE";
        }
        player.move(-1);
        return "Erro de Sintaxe! Recuas 1 casa.";
    }
}