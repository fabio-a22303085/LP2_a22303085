package pt.ulusofona.lp2.greatprogrammingjourney;

public class CrashAbyss extends Abyss {
    public CrashAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) {
            return "O teu Tratamento de Excepções preveniu o Crash. O sistema recuperou e continuas na mesma posição.";
        }

        player.setPosicao(1);
        return "Crash! Volta à primeira casa do jogo";
    }
}