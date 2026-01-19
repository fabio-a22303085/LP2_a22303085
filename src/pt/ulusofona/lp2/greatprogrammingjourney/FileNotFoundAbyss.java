package pt.ulusofona.lp2.greatprogrammingjourney;

public class FileNotFoundAbyss extends Abyss {
    public FileNotFoundAbyss(int id, String titulo) { super(id, titulo); }

    @Override
    public String interact(Player player, GameManager game) {
        if (tentarUsarFerramenta(player, 3)) {
            return "O ficheiro não existia, mas tinhas tratamento de exceções. Estás salvo.";
        }

        // Falha: Recua para a anterior
        player.voltarPosicaoAnterior(1);
        return "FileNotFoundException! O ficheiro de configuração sumiu. Recuas.";
    }
}