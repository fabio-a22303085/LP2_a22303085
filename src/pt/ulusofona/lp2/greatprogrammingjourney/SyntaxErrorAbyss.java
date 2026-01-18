package pt.ulusofona.lp2.greatprogrammingjourney;

public class SyntaxErrorAbyss extends Abyss {

    public SyntaxErrorAbyss(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        // CORREÇÃO: Tens de passar o número 4 (ID do IDE).
        // Se tiveres 'id' ou '0', ele procura uma ferramenta errada e falha.
        if (tentarUsarFerramenta(player, 4)) {
            return "O IDE ajudou a corrigir o erro de sintaxe. Estás salvo.";
        } else {
            player.voltarPosicaoAnterior(1);
            return "Erro de sintaxe! Falta um ponto e vírgula. Recuas 1 casa.";
        }
    }
}