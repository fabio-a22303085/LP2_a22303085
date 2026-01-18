package pt.ulusofona.lp2.greatprogrammingjourney;

public class LLMAbyss extends Abyss {
    public LLMAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        // Se ainda estiver nas primeiras 3 jogadas
        if (player.getNumeroDeJogadas() <= 4) {
            if (tentarUsarFerramenta(player, 5)) { // Tool 5: Ajuda Professor
                return "O Professor corrigiu as alucinações do LLM. Estás salvo.";
            }
            player.voltarPosicaoAnterior(1);
            return "O LLM alucinou nas primeiras rondas! Voltas à posição anterior.";
        } else {
            // A partir da 4ª ronda, avança sempre
            int ultimoMovimento = player.getUltimoDado();

            // Avança o mesmo número de casas do último dado
            player.move(ultimoMovimento);

            return "O LLM acelerou o desenvolvimento! Avanças mais " + ultimoMovimento + " casas extra.";
        }
    }
}