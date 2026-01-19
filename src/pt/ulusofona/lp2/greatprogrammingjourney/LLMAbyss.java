package pt.ulusofona.lp2.greatprogrammingjourney;

public class LLMAbyss extends Abyss {

    public LLMAbyss(int position) {
        // ID 20, conforme as regras novas
        super(20, position, "LLM");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (player.getNumeroJogadas() > 3) {
            // Comportamento Boost: avança o mesmo número de casas do último dado
            int boost = player.getUltimoDado();
            player.move(boost);
            return "Caiu no LLM mas já tem experiência! Avança quantas casas quantas as do ultimo movimento.";
        }
        else {

            if (tentarUsarFerramenta(player, 5)) {
                return "O Professor ajudou-te a verificar o código do LLM! Estás salvo.";
            } else {
                // Efeito: Volta para a posição anterior
                player.voltarPosicaoAnterior(1);
                return "Copiaste código do LLM sem saber! Voltas à posição anterior.";
            }
        }
    }
}