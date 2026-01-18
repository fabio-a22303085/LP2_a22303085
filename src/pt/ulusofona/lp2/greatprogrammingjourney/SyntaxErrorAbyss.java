package pt.ulusofona.lp2.greatprogrammingjourney;

public class SyntaxErrorAbyss extends Abyss {
    public SyntaxErrorAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 4)) { // Tool 4: IDE
            return "O IDE corrigiu o Erro de Sintaxe! Estás salvo.";
        }
        player.move(-1);
        return "Caiu num erro de sintaxe! Recua 1 casa";
    }
}