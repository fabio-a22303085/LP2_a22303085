package pt.ulusofona.lp2.greatprogrammingjourney;

public class InfiniteLoopAbyss extends Abyss {
    public InfiniteLoopAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 5)) { // Tool 5: Ajuda Professor
            return "O Professor ajudou-te a sair do Ciclo Infinito!";
        }
        player.setEmJogo("Preso");
        player.setTurnosPreso(1);
        return "Ciclo infinito! Fica preso nesta casa 1 turno.";
    }
}