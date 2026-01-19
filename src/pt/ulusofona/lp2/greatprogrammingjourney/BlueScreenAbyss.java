package pt.ulusofona.lp2.greatprogrammingjourney;

public class BlueScreenAbyss extends Abyss {
    public BlueScreenAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        player.setEmJogo("Derrotado");
        player.setTurnosPreso(0);
        return "Blue Screen of Death! O teu PC morreu. Game Over para ti.";
    }
}