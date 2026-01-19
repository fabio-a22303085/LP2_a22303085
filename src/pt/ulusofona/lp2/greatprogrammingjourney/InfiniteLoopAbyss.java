package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class InfiniteLoopAbyss extends Abyss {

    public InfiniteLoopAbyss(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        // 1. Tenta usar a Ferramenta (Ajuda do Professor - ID 5)
        // A regra diz: "Se tiver ferramenta... não fica preso, mas também NÃO liberta o que lá estava."
        if (tentarUsarFerramenta(player, 5)) {
            return "O Professor percebeu que ias entrar num loop e impediu-te. Estás a salvo.";
        }

        // 2. Lógica de Libertar outro jogador (Troca)
        List<Player> jogadores = game.getListaPlayers();
        boolean libertouAlguem = false;
        String nomeLiberto = "";

        for (Player p : jogadores) {
            // Se for outro jogador, estiver na mesma casa, e estiver "Preso"
            if (p.getId() != player.getId()
                    && p.getPosicao() == player.getPosicao()
                    && p.getEstado().equals("Preso")) {

                // Liberta o prisioneiro anterior
                p.setEmJogo("Em Jogo");
                p.setTurnosPreso(0);

                libertouAlguem = true;
                nomeLiberto = p.getNome();
                // Apenas um é libertado (assumindo que só lá cabe um preso de cada vez)
                break;
            }
        }

        // 3. Prender o jogador atual
        player.setEmJogo("Preso");
        // Definimos um valor alto para garantir que ele não sai por contagem de turnos normal
        // (A menos que alguém o venha salvar)
        player.setTurnosPreso(999);

        if (libertouAlguem) {
            return "Ciclo Infinito! Ficaste preso no loop, mas a tua chegada libertou o " + nomeLiberto + "!";
        } else {
            return "Ciclo Infinito! O processo bloqueou e ficaste Preso nesta casa.";
        }
    }
}