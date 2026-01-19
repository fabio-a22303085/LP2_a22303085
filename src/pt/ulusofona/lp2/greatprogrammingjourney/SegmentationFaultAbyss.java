package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.List;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int position) {
        super(9, position, "Segmentation Fault");
    }

    @Override
    public String interact(Player player, GameManager game) {
        List<Player> todos = game.getPlayers();
        List<Player> jogadoresNaCasa = new ArrayList<>();

        // Identifica todos os que estão nesta posição específica
        for (Player p : todos) {
            if (p.getPosicao() == this.position && !p.getEstado().equals("Derrotado")) {
                jogadoresNaCasa.add(p);
            }
        }

        // Só ativa se houver pelo menos 2 jogadores
        if (jogadoresNaCasa.size() >= 2) {
            for (Player p : jogadoresNaCasa) {
                p.move(-3); // Todos recuam 3 casas
            }
            return "Segmentation Fault! Todos os jogadores na casa recuam 3 casas.";
        }

        return "Segmentation Fault! (Sem efeito, estavas sozinho)";
    }}