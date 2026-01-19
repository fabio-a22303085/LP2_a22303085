package pt.ulusofona.lp2.greatprogrammingjourney;

public class BlueScreenAbyss extends Abyss {
    public BlueScreenAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        // Nenhuma ferramenta anula
        player.setEmJogo("Derrotado");
        player.setCausaDerrota("Blue Screen of Death");
        return "Blue Screen of Death! O sistema morreu.";
    }
}