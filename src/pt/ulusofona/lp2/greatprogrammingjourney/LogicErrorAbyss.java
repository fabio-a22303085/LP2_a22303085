package pt.ulusofona.lp2.greatprogrammingjourney;

public class LogicErrorAbyss extends Abyss {
    public LogicErrorAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 2)) { // Tool 2: Unit Tests
            return "Erro de Lógica anulado por Unit Tests";
        }

        int ultimoDado = player.getUltimoDado();
        int recuo = ultimoDado / 2; // Divisão inteira já arredonda para baixo (floor)

        player.move(-recuo);
        return "Erro de Lógica! Recuas " + recuo + " casas.";
    }
}