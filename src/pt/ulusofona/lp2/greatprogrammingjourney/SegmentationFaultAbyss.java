package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int position) {
        super(9, position, "Segmentation Fault");
    }

    @Override
    public String interact(Player player, GameManager game) {
        List<Player> todosOsJogadores = game.getPlayers();
        int posicaoAtual = player.getPosicao();
        int contagem = 0;

        // 1. Primeiro contamos quantos estão na casa (vivos)
        for (Player p : todosOsJogadores) {
            if (p.getPosicao() == posicaoAtual && !p.getEstado().equals("Derrotado")) {
                contagem++;
            }
        }

        // 2. Se houver 2 ou mais, TODOS recuam
        if (contagem >= 2) {
            for (Player p : todosOsJogadores) {
                if (p.getPosicao() == posicaoAtual && !p.getEstado().equals("Derrotado")) {
                    p.move(-3);
                }
            }
            return "Segmentation Fault! Todos os jogadores na casa recuam 3 casas.";
        }

        return "Segmentation Fault! (Sem efeito, estavas sozinho)";
    }
}