package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class BoardElement {
    protected int id;
    protected String title;

    public BoardElement(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }

    // Método polimórfico: cada filho decide como reage
    public abstract String interact(Player player, GameManager game);

    public abstract String getTypePrefix(); // "A" ou "T"
    public abstract int getTypeId(); // 0 ou 1
}