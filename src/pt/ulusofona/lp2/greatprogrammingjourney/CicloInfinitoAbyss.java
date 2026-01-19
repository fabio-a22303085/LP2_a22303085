package pt.ulusofona.lp2.greatprogrammingjourney;

public class CicloInfinitoAbyss extends Abyss {
    public CicloInfinitoAbyss(int position) {
        super(8, position, "Ciclo Infinito");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 5)) { // ID 5 = Ajuda Prof
            return "O Professor ajudou-te a sair do Ciclo Infinito!";
        }

        for (Player p : game.getPlayers()) {
            if (p.getId() != player.getId() && p.getPosicao() == this.position) {
                p.setTurnosPreso(0);
                p.setEmJogo("Em Jogo");
            }
        }

        player.setEmJogo("Preso");
        player.setTurnosPreso(1);
        return "Ciclo infinito! Fica preso nesta casa 1 turno.";
    }
}