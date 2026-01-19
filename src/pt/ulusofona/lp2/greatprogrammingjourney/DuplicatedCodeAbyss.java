package pt.ulusofona.lp2.greatprogrammingjourney;

public class DuplicatedCodeAbyss extends Abyss {
    public DuplicatedCodeAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 0)) {
            return "Usaste Herança para evitar repetição de código. Estás salvo.";
        }

        player.voltarPosicaoAnterior(1);
        return "Código duplicado detetado! Tens de refatorizar. Voltas atrás.";
    }
}