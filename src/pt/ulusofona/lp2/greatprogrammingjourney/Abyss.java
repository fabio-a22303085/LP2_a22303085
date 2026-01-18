package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abyss extends BoardElement {

    public Abyss(int id, String titulo) {
        super(id, titulo);
    }

    // Método auxiliar para as classes filhas usarem
    // Alterado para PROTECTED para os filhos terem acesso
    protected boolean tentarUsarFerramenta(Player p, int idTool) {
        if (p.temFerramenta(idTool)) {
            p.removerFerramenta(idTool);
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