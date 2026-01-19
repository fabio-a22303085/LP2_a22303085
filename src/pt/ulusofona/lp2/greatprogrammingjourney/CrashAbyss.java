package pt.ulusofona.lp2.greatprogrammingjourney;

public class CrashAbyss extends Abyss {
    public CrashAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        // Nenhuma ferramenta anula
        player.setPosicao(1);
        return "Crash! O sistema foi abaixo. Voltas à casa 1.";
    }
}