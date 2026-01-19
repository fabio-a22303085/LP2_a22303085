package pt.ulusofona.lp2.greatprogrammingjourney;

public class CrashAbyss extends Abyss {
    public CrashAbyss(int position) {
        super(4, position, "Crash");
    }

    @Override
    public String interact(Player player, GameManager game) {
        player.setPosicao(1); // Volta ao inicio
        return "Crash! Volta à primeira casa do jogo";
    }
}