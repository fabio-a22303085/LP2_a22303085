package pt.ulusofona.lp2.greatprogrammingjourney;

public class LLMAbyss extends Abyss {

    public LLMAbyss(int position) {
        // ID 20, conforme as regras novas
        super(20, position, "LLM");
    }

    @Override
    public String interact(Player player, GameManager game) {
        // Regra: "A partir da 4 ronda" (ou seja, já fez mais de 3 movimentos)
        if (player.getNumeroJogadas() > 3) {
            // Comportamento Boost: avança o mesmo número de casas do último dado
            int boost = player.getUltimoDado();
            player.move(boost);
            return "O LLM alucinou a teu favor! Avanças mais " + boost + " casas.";
        }
        else {
            // Comportamento Abismo (antes da 4ª ronda)
            // Tool que anula: Teacher Help (ID 5)
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