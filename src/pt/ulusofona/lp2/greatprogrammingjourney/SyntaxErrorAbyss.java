package pt.ulusofona.lp2.greatprogrammingjourney;

public class SyntaxErrorAbyss extends Abyss {

    public SyntaxErrorAbyss(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 4)) {
            return "O IDE corrigiu a sintaxe automaticamente. Estás salvo.";
        }

        // Falha: Recua 1 casa
        int pos = player.getPosicao();
        if (pos > 1) player.setPosicao(pos - 1);
        return "Erro de Sintaxe! Falta um ';'. Recuas 1 casa.";
    }
}