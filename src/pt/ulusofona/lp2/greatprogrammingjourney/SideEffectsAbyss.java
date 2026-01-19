package pt.ulusofona.lp2.greatprogrammingjourney;

public class SideEffectsAbyss extends Abyss {
    public SideEffectsAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 1)) {
            return "A imutabilidade da Programação Funcional evitou efeitos secundários.";
        }

        player.voltarPosicaoAnterior(1);
        return "Efeitos Secundários alteraram variáveis globais! O programa ficou instável.";
    }
}