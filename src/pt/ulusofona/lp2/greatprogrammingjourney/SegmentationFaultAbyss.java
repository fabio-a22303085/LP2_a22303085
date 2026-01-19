package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        List<Player> jogadores = game.getListaPlayers();
        int count = 0;
        int posAtual = player.getPosicao();

        // 1. Contar jogadores na casa
        for (Player p : jogadores) {
            if (p.getPosicao() == posAtual) {
                count++;
            }
        }

        // 2. Verificar condição (pelo menos 2 jogadores)
        if (count < 2) {
            return "Segmentation Fault! O sistema aguentou (apenas 1 jogador).";
        }

        // 3. Aplicar efeito: Todos recuam 3 casas
        game.recuarTodosEm(posAtual, 3);

        return "Segmentation Fault! Memória corrompida. Todos recuam 3 casas.";
    }
}