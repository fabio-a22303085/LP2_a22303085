package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abyss extends BoardElement {

    public Abyss(int id, String titulo) {
        super(id, titulo);
    }

    public abstract String interact(Player player, GameManager game);

    protected boolean tentarUsarFerramenta(Player p, int idTool) {
        if (p.temFerramenta(idTool)) {
            p.removerFerramenta(idTool); // Remove sempre do inventário
            return true;
        }
        return false;
    }

    @Override
    public String getTypePrefix() {
        return "A";
    }

    @Override
    public int getTypeId() {
        return 0;
    }
}