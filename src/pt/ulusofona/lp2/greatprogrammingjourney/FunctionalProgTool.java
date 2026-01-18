package pt.ulusofona.lp2.greatprogrammingjourney;

public class FunctionalProgTool extends Tool {
    public FunctionalProgTool(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        // Lógica específica: Jogadores de C recusam-se
        if (player.getPrimeiraLinguagem().equalsIgnoreCase("C")) {
            return "Programadores de C recusam-se a usar Programação Funcional!";
        }

        apanhar(player);
        return "Apanhaste Programação Funcional!";
    }
}