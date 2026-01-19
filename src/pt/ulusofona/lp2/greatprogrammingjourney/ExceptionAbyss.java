package pt.ulusofona.lp2.greatprogrammingjourney;

public class ExceptionAbyss extends Abyss {
    public ExceptionAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) {
            return "O Try-Catch apanhou a exceção. Estás salvo.";
        }

        // Falha: Recua 2 turnos (regra comum para Exceptions genéricas)
        player.voltarDoisTurnos();
        return "Lançada uma Exception não tratada! Voltas 2 posições no histórico.";
    }
}