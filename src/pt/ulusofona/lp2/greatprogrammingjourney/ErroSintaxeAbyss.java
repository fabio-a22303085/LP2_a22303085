package pt.ulusofona.lp2.greatprogrammingjourney;

public class ErroSintaxeAbyss extends Abyss {
    public ErroSintaxeAbyss(int position) {
        super(0, position, "Erro de sintaxe");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (player.temFerramenta(4)) {
            player.removerFerramenta(4);
            return "O IDE corrigiu o Erro de Sintaxe! Estás salvo.";
        }
        // O teste quer que recues apenas 1 casa:
        player.setPosicao(Math.max(1, player.getPosicao() - 1));
        return "Caiu num erro de sintaxe! Recua 1 casa";
    }
}