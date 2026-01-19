package pt.ulusofona.lp2.greatprogrammingjourney;

public class CicloInfinitoAbyss extends Abyss {
    public CicloInfinitoAbyss(int position) {
        super(8, position, "Ciclo Infinito");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 1)) {
            return "O Professor ajudou-te a sair do Ciclo Infinito!";
        }

        for (Player p : game.getPlayers()) {
            if (p.getId() != player.getId() && p.getPosicao() == this.position) {

                p.setEmJogo("Em Jogo");
            }
        }

        player.setEmJogo("Preso");
        return "Ciclo infinito! Fica preso nesta casa 1 turno.";
    }
}