package pt.ulusofona.lp2.greatprogrammingjourney;

public class DuplicatedCodeAbyss extends Abyss {
    public DuplicatedCodeAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 0)) { // Tool 0: Herança
            return "Usaste Herança para evitar código duplicado! Estás salvo.";
        }
        player.voltarPosicaoAnterior(1);
        return "Código duplicado! Recua para a casa anterior";
    }
}