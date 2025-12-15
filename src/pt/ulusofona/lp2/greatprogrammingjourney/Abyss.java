package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.List;

public class Abyss extends BoardElement{
    private List<Integer> pos = new ArrayList<>();
    private String descricao;

    public Abyss(int idTipo, int posicao, String titulo, String descricao) {

        super(idTipo, titulo);
        this.pos.add(posicao);
        this.descricao = descricao;
    }

    @Override
    public String interact(Player player, GameManager game) {
        String msg = "";

        switch (this.id) {
            case 0: // ERRO DE SINTAXE (Tool: IDE - ID 4)
                // Efeito: Recua 1 casa
                if (tentarUsarFerramenta(player, 4)) {
                    msg = "O IDE corrigiu o Erro de Sintaxe! Estás salvo.";
                } else {
                    player.move(-1);
                    msg = "Erro de Sintaxe! Esqueceste-te de um ponto e vírgula. Recuaste 1 casa.";
                }
                break;

            case 1: // ERRO DE LÓGICA (Tool: Unit Tests - ID 2)
                // Efeito: Recua metade do dado
                if (tentarUsarFerramenta(player, 2)) {
                    msg = "Os Testes Unitários apanharam o erro! Estás salvo.";
                } else {
                    // Nota: Precisas de ter getUltimoDado() no Player
                    int recuo = (int) Math.floor(game.getNrSpaces() / 2.0);
                    player.move(-recuo);
                    msg = "Erro de Lógica! A lógica falhou. Recuaste " + recuo + " casas.";
                }
                break;

            case 2: // EXCEPTION (Tool: Exception Handling - ID 3)
                // Efeito: Recua 2 casas
                if (tentarUsarFerramenta(player, 3)) {
                    msg = "Apanhaste a Exception com um Try-Catch! Estás salvo.";
                } else {
                    player.move(-2);
                    msg = "Exception não tratada! O programa rebentou. Recuaste 2 casas.";
                }
                break;

            case 3: // FILE NOT FOUND (Tool: Exception Handling - ID 3)
                // Efeito: Recua 3 casas
                if (tentarUsarFerramenta(player, 3)) {
                    msg = "Trataste a FileNotFoundException! Estás salvo.";
                } else {
                    player.move(-3);
                    msg = "Ficheiro não encontrado! Recuaste 3 casas.";
                }
                break;

            case 4: // CRASH (Sem Tool)
                // Efeito: Volta à casa 0 (início)
                player.setPosicao(1);
                msg = "CRASH TOTAL! O sistema foi abaixo. Voltaste ao início.";
                break;

            case 5: // CÓDIGO DUPLICADO (Tool: Herança - ID 0)
                // Efeito: Volta para a posição anterior
                if (tentarUsarFerramenta(player, 0)) {
                    msg = "Usaste Herança para evitar código duplicado! Estás salvo.";
                } else {
                    // Nota: O Player precisa de guardar histórico (posicaoAnterior)
                    player.voltarPosicaoAnterior(1); // fazer historico
                    msg = "Código Duplicado! Tiveste de refazer o trabalho. Voltaste para a casa anterior.";
                }
                break;

            case 6: // EFEITOS SECUNDÁRIOS (Tool: Prog. Funcional - ID 1)
                // Efeito: Volta para onde estava há 2 turnos
                if (tentarUsarFerramenta(player, 1)) {
                    msg = "A Programação Funcional evitou efeitos secundários! Estás salvo.";
                } else {
                    // Nota: O Player precisa de guardar histórico (posicaoHa2Turnos)
                    player.voltarPosicaoAnterior(2); // fazer historico, hashmap
                    msg = "Efeitos Secundários imprevistos! Voltaste à posição de há 2 turnos.";
                }
                break;

            case 7: // BSOD (Sem Tool)
                // Efeito: Morre (sai do jogo)
                player.setEmJogo("Derrotado");
                msg = "BLUE SCREEN OF DEATH! O teu PC pifou. Fim de jogo para ti.";
                break;

            case 8: // CICLO INFINITO (Tool: Teacher Help - ID 5)
                // Efeito: Fica preso
                // Nota: A parte de "libertar outros" é complexa de fazer aqui sem acesso à lista de todos
                if (tentarUsarFerramenta(player, 5)) {
                    msg = "O Professor ajudou-te a sair do Ciclo Infinito!";
                } else {
                    // Vamos assumir que fica preso 1 turno (ou define um valor fixo)
                    player.setEmJogo("Preso");
                    msg = "Entraste num Ciclo Infinito! Ficas preso.";
                } // logica para quando alguem entrar, o preso anterior é libertado e o que vem fica preso!!
                break;

            case 9: // SEGMENTATION FAULT (Sem Tool)
                if (game.getSlotInfo(player.getPosicao())[0].split(",").length > 1) {
                    player.move(-3);
                    msg = "Segmentation Fault! Acesso inválido à memória. Recuaste 3 casas.";
                }
                break;

            default:
                msg = "Caíste num abismo desconhecido.";
        }

        return msg;
    }

    public String getDescricao() {
        return descricao;
    }

    private boolean tentarUsarFerramenta(Player p, int idTool) {
        if (p.temFerramenta(idTool)) {
            p.removerFerramenta(idTool);
            return true;
        }
        return false;
    }

    @Override
    public String getTypePrefix() {
        return "A"; //Abyss
    }

    @Override
    public int getTypeId() {
        return 0; // 0 representa Abyss
    }

}