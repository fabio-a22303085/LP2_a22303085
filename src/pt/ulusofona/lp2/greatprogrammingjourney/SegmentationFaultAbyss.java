package pt.ulusofona.lp2.greatprogrammingjourney;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int position) {
        super(9, position, "Segmentation Fault");
    }

    @Override
    public String interact(Player player, GameManager game) {
        player.setEmJogo("Derrotado");
        player.setCausaMorte("Segmentation Fault");
        return "Segmentation Fault! Perdeu o jogo.";
    }
}