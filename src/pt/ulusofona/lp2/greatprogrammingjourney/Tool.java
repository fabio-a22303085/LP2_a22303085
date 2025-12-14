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

    @Override
    public String getTypePrefix() {
        return "T"; //Tool
    }

    @Override
    public int getTypeId() {
        return 1; // 1 representa Tool
    }

}