package pt.ulusofona.lp2.greatprogrammingjourney;

public class BlueScreenAbyss extends Abyss {
    public BlueScreenAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 5)) {
            return "O Professor reiniciou o sistema antes do erro fatal. Escapaste ao Blue Screen!";
        }

        // 2. Aplica a Derrota Imediata
        player.setEmJogo("Derrotado");

        // Limpa quaisquer turnos de prisão que pudessem existir (por segurança)
        player.setTurnosPreso(0);

        player.setCausaDerrota("Blue Screen of Death");
        return "Blue Screen of Death! Perde o jogo";
    }
}