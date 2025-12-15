package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.*;

public class Player {
    int id, posicao;
    String nome, cor, linguagens;
    String emJogo;

    private int turnosPreso = 0;
    private int ultimoDado = 0;

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
        if (turnosPreso > 0){turnosPreso--;}
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
        // Usar Iterator para remover com segurança enquanto percorremos
        Iterator<Tool> it = ferramentas.iterator();

        while (it.hasNext()) {
            Tool t = it.next();
            if (t.getId() == idFerramenta) {

                // Removemos também do Set de nomes (se ainda o estiveres a usar)
                nomeFerramentas.remove(t.getTitle());

                // Removemos da lista de ferramentas de forma segura
                it.remove();

                return; // Já encontrámos e removemos, podemos sair
            }
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

            return id + " | " + nome + " | " + posicao + " | " + getFerramentasToString() + " | " + linguagens + " | " + emJogo;
        }
    }

    public String getFerramentasToString() {
        if (nomeFerramentas.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (String t: nomeFerramentas) {
            sb.append(t);
            sb.append(";");

        }
        sb.deleteCharAt(sb.length()-1);

        return sb.toString().replaceAll(";", "; ");
    }

    public String getEstado() {
        return emJogo;
    }

    public void registarJogada() {
        // Guarda a posição ATUAL na lista de histórico
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
            // Se não houver histórico suficiente, volta ao início (segurança)
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

}
