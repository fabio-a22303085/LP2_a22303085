package pt.ulusofona.lp2.greatprogrammingjourney;

public class BlueScreenAbyss extends Abyss {
    public BlueScreenAbyss(int position) {
        super(7, position, "Blue Screen of Death");
    }

    @Override
    public String interact(Player player, GameManager game) {
        player.setEmJogo("Derrotado");
        player.kill("Blue Screen"); // Atualiza a causa da morte se adicionares esse campo
        player.setTurnosPreso(0);
        return "Blue Screen of Death! Perde o jogo";
    }
}