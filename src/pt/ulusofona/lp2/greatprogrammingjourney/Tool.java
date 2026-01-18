package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Tool extends BoardElement {

    public Tool(int id, String titulo) {
        super(id, titulo);
    }

    @Override
    public String getTypePrefix() {
        return "T";
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    // Método auxiliar para as subclasses usarem
    protected void apanhar(Player p) {
        p.apanharFerramenta(this);
    }
}