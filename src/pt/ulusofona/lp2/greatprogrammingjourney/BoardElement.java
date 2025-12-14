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

    public abstract String interact(Player player, GameManager game);

    //(retorna "A" ou "T")
    public abstract String getTypePrefix();

    //(retorna 0 ou 1)
    public abstract int getTypeId();

}