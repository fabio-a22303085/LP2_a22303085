package pt.ulusofona.lp2.greatprogrammingjourney;

public class IDETool extends Tool {
    public IDETool(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        apanhar(player);

        // Lógica de bónus (se quiseres ativar)
        player.move(2);
        return "Apanhaste o IDE e avançaste mais um pouco devido à produtividade!";
    }
}