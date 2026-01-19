package pt.ulusofona.lp2.greatprogrammingjourney;

public class LogicErrorAbyss extends Abyss {
    public LogicErrorAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 2)) { // Tool 2: Unit Tests
            return "Os Testes Unitários apanharam o erro! Estás salvo.";
        }
        int recuo = (int) Math.floor(game.getNrSpaces() / 2.0);
        player.move(-recuo);
        return "Erro de Lógica! Recua " + recuo + " casas.";
    }
}
