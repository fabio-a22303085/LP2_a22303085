package pt.ulusofona.lp2.greatprogrammingjourney;

public class CicloInfinitoAbyss extends Abyss {
    public CicloInfinitoAbyss(int position) {
        super(8, position, "Ciclo Infinito");
    }

    @Override
    public String interact(Player player, GameManager game) {
        // Efeito comum: Libertar outros (podes implementar aqui ou no GM,
        // mas idealmente o abismo afeta o jogador atual).
        // Se a regra diz "liberta TODOS os outros na mesma casa", precisamos de acesso à lista de players.
        // O GameManager passa-se a si próprio, podes criar um método no GM public void libertarPresosNaCasa(int pos)

        // game.libertarPresosNaCasa(player.getPosicao()); // (Implementar no GM se necessário)

        if (tentarUsarFerramenta(player, 5)) { // ID 5 = Ajuda Prof
            return "O Professor ajudou-te a sair do Ciclo Infinito!";
        }

        player.setEmJogo("Preso");
        player.setTurnosPreso(1);
        return "Ciclo infinito! Fica preso nesta casa 1 turno.";
    }
}