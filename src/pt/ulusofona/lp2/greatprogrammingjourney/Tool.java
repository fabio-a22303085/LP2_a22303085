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

    // Getter necessário para o Player listar o nome
    public String getTitle() {
        return super.getTitle();
    }

    // Método auxiliar (opcional, mantido por compatibilidade)
    protected void apanhar(Player p) {
        p.apanharFerramenta(this);
    }

    // === INTERAÇÃO PADRÃO ===
    // Todas as ferramentas usam isto, a menos que faças Override numa específica
    @Override
    public String interact(Player player, GameManager game) {
        player.apanharFerramenta(this);
        return "Apanhaste a ferramenta: " + this.getTitle();
    }
}