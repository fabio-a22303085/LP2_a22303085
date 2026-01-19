package pt.ulusofona.lp2.greatprogrammingjourney;

public class LLMAbyss extends Abyss {
    public LLMAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        int jogadas = player.getNumeroDeJogadas(); // 0 no início, 1 após primeira jogada

        // "se ainda só tiver jogado 3 rodas" (<= 3)
        if (jogadas <= 3) {
            // Tenta usar ferramenta
            if (tentarUsarFerramenta(player, 5)) { // Tool 5: Ajuda do Professor
                return "LLM anulado por Ajuda do Professor";
            }

            // Sem ferramenta: Volta para a posição anterior
            player.voltarPosicaoAnterior(1);
            return "LLM alucinou! Voltas à posição anterior.";
        }
        else {
            // "A partir da 4 ronda... o resultado será sempre este"
            int bonus = player.getUltimoDado();
            player.move(bonus);
            return "LLM ajudou! Avanças mais " + bonus + " casas.";
        }
    }
}