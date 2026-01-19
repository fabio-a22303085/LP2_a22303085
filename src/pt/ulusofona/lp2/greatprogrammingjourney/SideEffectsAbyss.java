package pt.ulusofona.lp2.greatprogrammingjourney;

public class SideEffectsAbyss extends Abyss {
    public SideEffectsAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 1)) { // Tool 1: Prog Funcional
            return "A Programação Funcional evitou efeitos secundários! Estás salvo.";
        }
        player.voltarPosicaoAnterior(2);
        return "Efeitos secundários! Recua para a posição de 2 movimentos atrás";
    }
}