package pt.ulusofona.lp2.greatprogrammingjourney;

public class ProgramacaoFuncionalTool extends Tool {
    public ProgramacaoFuncionalTool(int id, String title) {
        super(id, title);
    }

    @Override
    public String interact(Player jogador, GameManager game) {
        // É AQUI que o jogador ganha a ferramenta!
        jogador.apanharFerramenta(this);

        return "Apanhou Programação Funcional";
    }

}