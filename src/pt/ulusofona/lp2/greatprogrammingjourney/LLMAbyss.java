package pt.ulusofona.lp2.greatprogrammingjourney;

public class LLMAbyss extends Abyss {

    public LLMAbyss(int position) {
        // ID 20, conforme as regras novas
        super(20, position, "LLM");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (player.getNumeroJogadas() > 3) {
            int boost = player.getUltimoDado();
            player.move(boost);
            return "Caiu no LLM mas já tem experiência! Avança tantas casas quantas as do último movimento";
        }
        else {

            if (tentarUsarFerramenta(player, 5)) {
                return "LLM anulado pela ajuda do Professor";
            } else {
                // Efeito: Volta para a posição anterior
                player.voltarPosicaoAnterior(1);
                return "Caiu no LLM!! Recua à posição onde estavas.";
            }
        }
    }
}