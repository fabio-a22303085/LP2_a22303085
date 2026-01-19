package pt.ulusofona.lp2.greatprogrammingjourney;

public class LogicErrorAbyss extends Abyss {
    public LogicErrorAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 2)) {
            return "Os Testes Unitários apanharam o erro de lógica. Estás salvo.";
        }

        // Falha: Recua metade do dado (exemplo comum neste projeto) ou 1 casa
        // Verifica a tua regra de penalidade específica. Vou pôr recuar 1 casa.
        player.voltarPosicaoAnterior(1);
        return "Erro de Lógica! O resultado não é o esperado. Recuas.";
    }
}
