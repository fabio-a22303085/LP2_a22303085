package pt.ulusofona.lp2.greatprogrammingjourney;

public class ErroSintaxeAbyss extends Abyss {
    public ErroSintaxeAbyss(int position) {
        super(0, position, "Erro de sintaxe");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 4)) { // ID 4 = IDE
            return "O IDE corrigiu o Erro de Sintaxe! Estás salvo.";
        }
        player.move(-1);
        return "Caiu num erro de sintaxe! Recua 1 casa";
    }
}