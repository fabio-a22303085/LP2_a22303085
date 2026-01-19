package pt.ulusofona.lp2.greatprogrammingjourney;

public class LLMAbyss extends Abyss {

    public LLMAbyss(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (player.getNumeroDeJogadas() < 4) {

            if (tentarUsarFerramenta(player, 5)) {
                return "O Professor reviu o código do LLM e corrigiu as alucinações. Estás salvo.";

            } else {
                player.voltarPosicaoAnterior(1);
                return "O LLM inventou código nas primeiras rondas! Voltas à posição anterior.";
            }

        } else {

            int bonus = player.getUltimoDado();
            player.move(bonus);
            return "O LLM acelerou o desenvolvimento! Avanças mais " + bonus + " casas extra.";
        }
    }
}