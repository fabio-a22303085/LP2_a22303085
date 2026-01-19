package pt.ulusofona.lp2.greatprogrammingjourney;

public class EfeitosSecundariosAbyss extends Abyss {
    public EfeitosSecundariosAbyss(int position) {
        super(6, position, "Efeitos Secundários");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 1)) { // ID 1 = Prog Funcional
            return "A Programação Funcional evitou efeitos secundários! Estás salvo.";
        }
        player.voltarDoisTurnos(); // Usar o método que criaste no Player
        return "Efeitos secundários! Recua para a posição de 2 movimentos atrás";
    }
}