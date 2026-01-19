package pt.ulusofona.lp2.greatprogrammingjourney;

public class ErroSintaxeAbyss extends Abyss {
    public ErroSintaxeAbyss(int position) {
        super(0, position, "Erro de sintaxe");
    }

    @Override
    public String interact(Player player, GameManager game) {
        // 1. Verificar se o jogador tem o IDE (ID 4)
        if (player.temFerramenta(4)) {
            player.removerFerramenta(4); // Importante: a ferramenta gasta-se!
            return "O IDE corrigiu o Erro de Sintaxe! Estás salvo.";
        }

        // 2. Penalização: Voltar ao início (Posição 1 ou 0, conforme o teu worldSize)
        player.setPosicao(1);
        return "Erro de sintaxe! Voltaste ao início do projeto.";
    }
}