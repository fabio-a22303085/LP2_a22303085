package pt.ulusofona.lp2.greatprogrammingjourney;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        player.setEmJogo("Derrotado");
        return "Segmentation Fault! Acedeste a memória inválida (Core Dumped). Fim de jogo.";
    }
}