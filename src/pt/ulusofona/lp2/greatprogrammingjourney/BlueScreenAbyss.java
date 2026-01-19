package pt.ulusofona.lp2.greatprogrammingjourney;

public class BlueScreenAbyss extends Abyss {
    public BlueScreenAbyss(int position) {
        super(7, position, "Blue Screen of Death");
    }

    @Override
    public String interact(Player player, GameManager game) {
        player.setEmJogo("Derrotado");
        player.kill("Blue Screen");
        return "Blue Screen of Death! Perde o jogo";
    }
}