package pt.ulusofona.lp2.greatprogrammingjourney;

public class CrashAbyss extends Abyss {
    public CrashAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        player.setPosicao(1); // Volta ao início
        return "Crash! Volta à primeira casa do jogo";
    }
}