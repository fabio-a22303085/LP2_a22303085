package pt.ulusofona.lp2.greatprogrammingjourney;

public class SimpleTool extends Tool {
    public SimpleTool(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        apanhar(player);
        return "Apanhaste a ferramenta: " + this.title;
    }
}