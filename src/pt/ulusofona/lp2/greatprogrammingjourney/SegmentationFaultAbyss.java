package pt.ulusofona.lp2.greatprogrammingjourney;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        game.recuarTodosEm(player.getPosicao(), 3);
        return "Segmentation Fault! Todos os programadores nesta casa recuam 3 casas";
    }
}