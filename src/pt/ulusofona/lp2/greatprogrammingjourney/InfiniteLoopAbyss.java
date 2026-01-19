package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class InfiniteLoopAbyss extends Abyss {
    public InfiniteLoopAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        StringBuilder msg = new StringBuilder();

        // 1. Efeito Comum (SEMPRE ACONTECE): Libertar outros nesta casa
        List<Player> jogadores = game.getListaPlayers();
        boolean libertou = false;

        for (Player p : jogadores) {
            // Se não for eu, estiver aqui e estiver Preso -> Liberta
            if (p.getId() != player.getId() &&
                    p.getPosicao() == player.getPosicao() &&
                    p.getEstado().equals("Preso")) {

                p.setEmJogo("Em Jogo");
                p.setTurnosPreso(0);
                libertou = true;
            }
        }

        if (libertou) {
            msg.append("Libertaste os jogadores presos aqui! ");
        }

        // 2. Efeito no Jogador Atual
        if (tentarUsarFerramenta(player, 5)) { // Tool 5: Teacher Help
            msg.append("Ciclo Infinito anulado por Teacher Help");
            return msg.toString();
        }

        // Se não tiver ferramenta -> Fica Preso
        player.setEmJogo("Preso");
        player.setTurnosPreso(999); // Valor alto para simular infinito
        player.setCausaDerrota("Ciclo Infinito"); // Caso o jogo acabe aqui
        msg.append("Ciclo Infinito! Ficaste preso no loop.");

        return msg.toString();
    }
}