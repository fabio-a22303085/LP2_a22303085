package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class Abyss extends BoardElement {
    protected String imagePng; // Para gerir imagens se necessário

    public Abyss(int id, int position, String title) { // Position não é estritamente necessária aqui se o tabuleiro gere, mas ok
        super(id, title);
    }

    // Método auxiliar que todos os abismos filhos podem usar
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
