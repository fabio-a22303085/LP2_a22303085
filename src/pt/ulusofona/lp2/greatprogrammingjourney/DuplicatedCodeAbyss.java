package pt.ulusofona.lp2.greatprogrammingjourney;

public class DuplicatedCodeAbyss extends Abyss {
    public DuplicatedCodeAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 0)) { // Tool 0: Inheritance
            return "Código Duplicado anulado por Inheritance";
        }
        // Volta para a posição anterior (anula movimento)
        player.voltarPosicaoAnterior(1);
        return "Código duplicado! O movimento foi anulado.";
    }
}