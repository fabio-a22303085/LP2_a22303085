package pt.ulusofona.lp2.greatprogrammingjourney;

public class SegmentationFaultAbyss extends Abyss {
    public SegmentationFaultAbyss(int position) {
        super(9, position, "Segmentation Fault");
    }

    @Override
    public String interact(Player player, GameManager game) {
        // Verifica quantos jogadores estão nesta casa
        String idsNaCasa = game.getSlotInfo(player.getPosicao())[0];
        if (idsNaCasa != null && idsNaCasa.split(",").length > 1) {

            player.move(-3);

            // Para afetar os outros, precisarias de game.recuarJogadoresNaCasa(pos, 3);
            return "Segmentation Fault! Recuaste 3 casas devido a sobrelotação.";
        }
        return "Segmentation Fault! (Sem efeito, estavas sozinho)";
    }
}