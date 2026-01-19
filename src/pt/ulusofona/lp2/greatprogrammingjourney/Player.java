package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    int id, posicao;
    String nome, cor, linguagens;
    String emJogo;

    private int turnosPreso = 0;
    private int ultimoDado = 0;

    private String causaDerrota = "";
    private Set<String> nomeFerramentas = new HashSet<>();
    private Set<Tool> ferramentas = new HashSet<>();
    private List<Integer> historicoPosicoes = new ArrayList<>();
    int index = 0;
    String primeiraLinguagem;


    public Player(int id, int posicao, String nome, String cor, String linguagens) {
        this.id = id;
        this.posicao = posicao;
        this.nome = nome;
        this.cor = cor;
        this.emJogo = "Em Jogo";

        String[] linguasArray = linguagens.split(";");
        primeiraLinguagem = linguasArray[0];
        Arrays.sort(linguasArray, String.CASE_INSENSITIVE_ORDER);
        this.linguagens = String.join("; ", linguasArray);
        this.historicoPosicoes.add(this.posicao);
    }

    public void setCausaDerrota(String causa) {
        this.causaDerrota = causa;
    }

    public String getCausaDerrota() {
        return this.causaDerrota;
    }

    public String getPrimeiraLinguagem() {
        return primeiraLinguagem;
    }


    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public String getLinguagens() {
        return linguagens;
    }

    public String getEmJogo() {
        return emJogo;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean temFerramenta(int idFerramenta) {
        for (Tool t : ferramentas) {
            if (t.getId() == idFerramenta) {
                return true;
            }
        }
        return false;
    }

    public void setTurnosPreso(int turnos) {
        this.turnosPreso = turnos;
    }

    public int getTurnosPreso() {
        return turnosPreso;
    }

    public void decrementarTurnosPreso() {
        if (turnosPreso > 0) {
            turnosPreso--;

            if (turnosPreso == 0) {
                this.emJogo = "Em Jogo";
            }
        }
    }

    public void move(int deslocamento) {
        this.posicao += deslocamento;
        if (this.posicao < 0){this.posicao = 0;}
    }

    public void apanharFerramenta(Tool ferramenta) {
        ferramentas.add(ferramenta);
        nomeFerramentas.add(ferramenta.getTitle());
    }

    public void removerFerramenta(int idFerramenta) {
        // Procura a ferramenta para obter o título antes de remover
        Tool toolToRemove = null;
        for (Tool t : ferramentas) {
            if (t.getId() == idFerramenta) {
                toolToRemove = t;
                break; // Parar assim que encontrar
            }
        }

        if (toolToRemove != null) {
            ferramentas.remove(toolToRemove);
            nomeFerramentas.remove(toolToRemove.getTitle());
        }
    }

    public void setEmJogo(String emJogo) {
        this.emJogo = emJogo;
    }

    @Override
    public String toString() {
        if (ferramentas.isEmpty()) {
            return id + " | " + nome + " | " + posicao + " | No tools | " + linguagens + " | " + emJogo;
        } else {

            return id + " | " + nome + " | " + posicao + " | " + getFerramentasToString() + " | " + linguagens + " | " + emJogo ;
        }
    }

    public String getFerramentasToString() {
        if (nomeFerramentas.isEmpty()) {
            return ""; // Ou "No tools", conforme a tua preferência
        }

        // 1. Criar uma lista temporária com o conteúdo do Set
        List<String> listaOrdenada = new ArrayList<>(nomeFerramentas);

        // 2. Ordenar a lista alfabeticamente
        // Se quiseres garantir que "a" e "A" ficam juntos, usa: String.CASE_INSENSITIVE_ORDER
        Collections.sort(listaOrdenada);

        StringBuilder sb = new StringBuilder();

        // 3. Percorrer a lista JÁ ORDENADA
        for (String t : listaOrdenada) {
            sb.append(t);
            sb.append("; ");
        }

        // 4. Remover o último "; " extra
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    public String getEstado() {
        return emJogo;
    }

    public void registarJogada() {
        this.historicoPosicoes.add(this.posicao);
    }

    // 4. Implementação do Abismo 5: "Voltar Posição Anterior"
    public void voltarPosicaoAnterior(int num) {
        // A posição anterior é o último elemento que foi gravado no histórico
        if (!historicoPosicoes.isEmpty()) {

            int posAnterior = historicoPosicoes.get(historicoPosicoes.size() - num);
            this.posicao = posAnterior;
        }
    }

    // 5. Implementação do Abismo 6: "Voltar Dois Turnos"
    public void voltarDoisTurnos() {
        // Precisamos de ter pelo menos 2 registos no histórico para recuar 2 vezes
        if (historicoPosicoes.size() >= 2) {
            // size-1 é o turno anterior. size-2 é há dois turnos.
            int posAntiga = historicoPosicoes.get(historicoPosicoes.size() - 2);
            this.posicao = posAntiga;
        } else {
            // Se não houver histórico suficiente, volta ao início
            this.posicao = 0;
        }

    }
    public void setUltimoDado(int valor) {
        this.ultimoDado = valor;
    }

    public int getUltimoDado() {
        return this.ultimoDado;
    }

    public String getDataForSave() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append(":");
        sb.append(posicao).append(":");
        sb.append(nome).append(":");
        sb.append(cor).append(":");
        sb.append(getEstado()).append(":");
        sb.append(turnosPreso).append(":");
        sb.append(ultimoDado).append(":");

        if (linguagens == null || linguagens.isBlank()) {
            sb.append("NULL");
        } else {
            sb.append(linguagens);
        }
        sb.append(":");

        if (ferramentas.isEmpty()) {
            sb.append("NULL");
        } else {

            for (String t: nomeFerramentas) {
                sb.append(t);
                sb.append(",");

            }
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(":");

        return sb.toString();
    }

    public int getNumeroDeJogadas() {
        // A lista tem a posição inicial + 1 registo por cada jogada feita.
        // Exemplo:
        // Início (sem jogar): Tamanho 1 -> (1-1) = 0 Jogadas
        // Jogou 1 vez: Tamanho 2 -> (2-1) = 1 Jogada
        return historicoPosicoes.size() - 1;
    }

}