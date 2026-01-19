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

    // 1. Necessário para o Player conseguir listar os nomes das ferramentas no toString()
    public String getTitle() {
        return super.getTitle();
    }

    // 2. Auxiliar para apanhar a ferramenta
    protected void apanhar(Player p) {
        p.apanharFerramenta(this);
    }

    @Override
    public String interact(Player player, GameManager game) {
        // A lógica de apanhar a ferramenta:
        player.apanharFerramenta(this);

        // Retorna uma mensagem de sucesso
        return "Apanhaste a ferramenta: " + super.getTitle();
    }
}