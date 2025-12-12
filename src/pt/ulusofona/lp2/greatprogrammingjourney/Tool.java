package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool extends BoardElement {

    public Tool(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String interact(Player player, GameManager game) {
        player.apanharFerramenta(this);
        return "Apanhaste a ferramenta: " + this.title;
    }
}