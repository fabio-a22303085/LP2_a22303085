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
    private String causaMorte = "";


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

    public int getNumeroJogadas() {
        // Se o histórico tem 1 elemento (pos inicial), fez 0 jogadas.
        // Se tem 2 elementos, fez 1 jogada.
        return historicoPosicoes.size() - 1;
    }

    public void kill(String causa) {
        this.emJogo = "Derrotado";
        this.causaMorte = causa;
    }

    public String getCausaMorte() {
        return causaMorte;
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

        if (causaMorte == null || causaMorte.isEmpty()) {
            sb.append("NULL");
        } else {
            sb.append(causaMorte);
        }
        sb.append(":");

        // --- NOVO: Histórico de Posições (para o LLM e Efeitos Secundários) ---
        // Gravar como "0,4,6,10"
        if (historicoPosicoes.isEmpty()) {
            sb.append("NULL");
        } else {
            StringBuilder histSb = new StringBuilder();
            for (Integer pos : historicoPosicoes) {
                histSb.append(pos).append(",");
            }
            if (histSb.length() > 0) histSb.deleteCharAt(histSb.length() - 1);
            sb.append(histSb.toString());
        }

        return sb.toString();
    }

    public void restaurarHistorico(String historicoStr) {
        this.historicoPosicoes.clear();
        if (!historicoStr.equals("NULL") && !historicoStr.isEmpty()) {
            String[] posicoes = historicoStr.split(",");
            for (String s : posicoes) {
                try {
                    this.historicoPosicoes.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    // Ignorar erro de parsing
                }
            }
        } else {
            // Se não houver histórico, pelo menos a posição atual deve estar lá
            this.historicoPosicoes.add(this.posicao);
        }
    }

    public void setCausaMorte(String causa) {
        this.causaMorte = causa;
    }

}