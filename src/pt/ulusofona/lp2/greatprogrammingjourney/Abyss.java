package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abyss extends BoardElement {
    protected String imagePng; // Para gerir imagens se necessário
    protected int position;

    public Abyss(int id, int position, String title) {
        super(id, title);
        this.position = position;
    }

    protected boolean tentarUsarFerramenta(Player p, int idTool) {
        if (p.temFerramenta(idTool)) {
            p.removerFerramenta(idTool);
            return true;
        }
        return false;
    }

    @Override
    public String getTypePrefix() { return "A"; }

    @Override
    public int getTypeId() { return 0; }
}