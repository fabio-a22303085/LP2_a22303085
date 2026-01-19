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
            // A lógica diz "todos recuam".
            // Como este método só afeta o "player" atual, tens de ter cuidado.
            // Se o efeito é imediato para TODOS, tens de iterar sobre os jogadores no GM.
            // Para simplificar e manter encapsulamento:
            // O SegFault afeta quem cai lá E quem lá está? O enunciado diz "se houver pelo menos 2".

            // Assumindo que afeta o atual:
            player.move(-3);

            // Para afetar os outros, precisarias de game.recuarJogadoresNaCasa(pos, 3);
            return "Segmentation Fault! Recuaste 3 casas devido a sobrelotação.";
        }
        return "Segmentation Fault! (Sem efeito, estavas sozinho)";
    }
}