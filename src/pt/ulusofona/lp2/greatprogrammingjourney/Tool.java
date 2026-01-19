package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tool extends BoardElement {
    public Tool(int id, String title) {
        super(id, title);
    }

    @Override
    public String interact(Player player, GameManager game) {
        player.apanharFerramenta(this);
        return "Apanhou a ferramenta: " + this.title;
    }

    @Override
    public String getTypePrefix() { return "T"; }

    @Override
    public int getTypeId() { return 1; }
}
