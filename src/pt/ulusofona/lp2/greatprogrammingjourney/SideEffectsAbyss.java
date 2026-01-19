package pt.ulusofona.lp2.greatprogrammingjourney;

public class SideEffectsAbyss extends Abyss {
    public SideEffectsAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 1)) { // Tool 1: Prog Funcional
            return "Efeitos Secundários anulado por Programação Funcional";
        }
        // Volta para onde estava há 2 turnos (verificar lógica no Player.java)
        player.voltarDoisTurnos();
        return "Efeitos Secundários! Voltas à posição de há 2 turnos.";
    }
}