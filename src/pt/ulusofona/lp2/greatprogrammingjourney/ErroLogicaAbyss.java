package pt.ulusofona.lp2.greatprogrammingjourney;

public class ErroLogicaAbyss extends Abyss {
    public ErroLogicaAbyss(int position) {
        super(1, position, "Erro de lógica");
    }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 2)) { // ID 2 = Unit Tests
            return "Os Testes Unitários apanharam o erro! Estás salvo.";
        }
        // Precisamos do valor do dado. O GameManager tem esse estado ou o Player tem.
        // O teu Player tem setUltimoDado(), então usamos isso:
        int recuo = (int) Math.floor(player.getUltimoDado() / 2.0);
        player.move(-recuo);
        return "Erro de Lógica! Recua " + recuo + " casas.";
    }
}