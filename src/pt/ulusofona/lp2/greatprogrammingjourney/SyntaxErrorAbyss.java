package pt.ulusofona.lp2.greatprogrammingjourney;

public class SyntaxErrorAbyss extends Abyss {

    public SyntaxErrorAbyss(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 4)) {
            return "O IDE detetou o erro de sintaxe e corrigiu o ponto e vírgula em falta. Estás salvo.";
        }

        int posAtual = player.getPosicao();

        if (posAtual > 1) {
            player.setPosicao(posAtual - 1);
        }

        return "Erro de Sintaxe! Esqueceste-te de fechar um parêntesis. Recuas 1 casa.";
    }
}