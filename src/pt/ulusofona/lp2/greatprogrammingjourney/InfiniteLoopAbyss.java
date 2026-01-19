package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.List;

public class InfiniteLoopAbyss extends Abyss {

    public InfiniteLoopAbyss(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {

        if (tentarUsarFerramenta(player, 5)) {
            return "O Professor detetou o Ciclo Infinito e tirou-te de lá antes de entrares. Estás salvo.";
        }

        int jogadoresVivos = 0;
        List<Player> todosJogadores = game.getListaPlayers();

        for (Player p : todosJogadores) {
            if (!p.getEstado().equals("Derrotado")) {
                jogadoresVivos++;
            }
        }

        if (jogadoresVivos <= 1) {
            player.setEmJogo("Derrotado");
            player.setTurnosPreso(0);
            return "Entraste num Ciclo Infinito e não há ninguém para te reiniciar. O jogo acabou para ti.";
        }

        // TENTAR LIBERTAR OUTRO JOGADOR (Troca)
        boolean libertouAlguem = false;
        String nomeLibertado = "";

        for (Player p : todosJogadores) {
            // Se for outro jogador, estiver na mesma casa E estiver "Preso"
            if (p.getId() != player.getId() &&
                    p.getPosicao() == player.getPosicao() &&
                    p.getEstado().equals("Preso")) {

                // Liberta o prisioneiro anterior
                p.setEmJogo("Em Jogo");
                p.setTurnosPreso(0);
                libertouAlguem = true;
                nomeLibertado = p.getNome();
                break;
            }
        }

        // PRENDER O JOGADOR ATUAL
        player.setEmJogo("Preso");

        player.setTurnosPreso(100);

        if (libertouAlguem) {
            return "Entraste no Ciclo Infinito. Agora és tu que estás preso.";
        } else {
            return "Entraste num Ciclo Infinito. Ficas preso aqui até que outro programador caia nesta casa.";
        }
    }
}