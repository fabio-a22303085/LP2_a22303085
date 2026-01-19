package pt.ulusofona.lp2.greatprogrammingjourney;

public class CodigoDuplicadoAbyss extends Abyss {
    public CodigoDuplicadoAbyss(int position) {
        super(5, position, "Código Duplicado");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 0)) { // ID 0 = Herança
            return "Usaste Herança para evitar código duplicado! Estás salvo.";
        }
        player.voltarPosicaoAnterior(1);
        return "Código duplicado! Recua para a casa anterior";
    }
}