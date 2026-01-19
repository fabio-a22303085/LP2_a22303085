package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.*;

public class Player {
    private int id, posicao;
    private String nome, cor, linguagens;
    private String emJogo;
    private String primeiraLinguagem;
    private int turnosPreso = 0;
    private int ultimoDado = 0;
    private String causaMorte = "";

    private Set<Tool> ferramentas = new HashSet<>();
    private Set<String> nomeFerramentas = new HashSet<>();
    private List<Integer> historicoPosicoes = new ArrayList<>();

    public Player(int id, int posicao, String nome, String cor, String linguagens) {
        this.id = id;
        this.posicao = posicao;
        this.nome = nome;
        this.cor = cor;
        this.emJogo = "Em Jogo";

        String[] linguasArray = linguagens.split(";");
        this.primeiraLinguagem = linguasArray[0].trim(); // CORRETO!
        Arrays.sort(linguasArray, String.CASE_INSENSITIVE_ORDER);
        this.linguagens = String.join("; ", linguasArray);

        historicoPosicoes.add(posicao); // Histórico inicia com posição inicial
    }

    public String getEstado() {
        return emJogo;
    }
    public int getId() { return id; }
    public int getPosicao() { return posicao; }
    public String getNome() { return nome; }
    public String getCor() { return cor; }
    public String getLinguagens() { return linguagens; }
    public String getEmJogo() { return emJogo; }
    public String getPrimeiraLinguagem() { return primeiraLinguagem; }
    public int getTurnosPreso() { return turnosPreso; }
    public int getUltimoDado() { return ultimoDado; }
    public String getCausaMorte() { return causaMorte; }

    public void setPosicao(int posicao) { this.posicao = posicao; }
    public void setEmJogo(String estado) { this.emJogo = estado; }
    public void setTurnosPreso(int turnos) { this.turnosPreso = turnos; }
    public void setUltimoDado(int valor) { this.ultimoDado = valor; }
    public void setCausaMorte(String causa) { this.causaMorte = causa; }

    public void decrementarTurnosPreso() {
        if (turnosPreso > 0) {
            turnosPreso--;
            if (turnosPreso == 0) emJogo = "Em Jogo";
        }
    }

    public boolean temFerramenta(int idFerramenta) {
        for (Tool t : ferramentas) {
            if (t.getId() == idFerramenta) return true;
        }
        return false;
    }

    public void apanharFerramenta(Tool ferramenta) {
        ferramentas.add(ferramenta);
        nomeFerramentas.add(ferramenta.getTitle());
    }

    public void removerFerramenta(int idFerramenta) {
        Tool toolToRemove = null;
        for (Tool t : ferramentas) if (t.getId() == idFerramenta) toolToRemove = t;
        if (toolToRemove != null) {
            ferramentas.remove(toolToRemove);
            nomeFerramentas.remove(toolToRemove.getTitle());
        }
    }

    public void registarJogada() {
        historicoPosicoes.add(posicao);
    }

    public void voltarPosicaoAnterior(int num) {
        if (historicoPosicoes.size() >= num) {
            posicao = historicoPosicoes.get(historicoPosicoes.size() - num);
        }
    }

    public void voltarDoisTurnos() {
        if (historicoPosicoes.size() >= 2) {
            posicao = historicoPosicoes.get(historicoPosicoes.size() - 2);
        } else {
            posicao = 0; // fallback
        }
    }

    public void move(int deslocamento) {
        posicao += deslocamento;
        if (posicao < 0) posicao = 0;
    }

    public void kill(String causa) {
        emJogo = "Derrotado";
        causaMorte = causa;
    }

    public String getFerramentasToString() {
        if (nomeFerramentas.isEmpty()) return "";
        List<String> lista = new ArrayList<>(nomeFerramentas);
        Collections.sort(lista);
        return String.join(";", lista);
    }

    public int getNumeroJogadas() { return historicoPosicoes.size() - 1; }

    public String getDataForSave() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(":").append(posicao).append(":").append(nome).append(":")
                .append(cor).append(":").append(emJogo).append(":").append(turnosPreso).append(":")
                .append(ultimoDado).append(":").append(linguagens.isEmpty() ? "NULL" : linguagens).append(":");

        if (nomeFerramentas.isEmpty()) sb.append("NULL");
        else sb.append(String.join(",", nomeFerramentas));
        sb.append(":").append(causaMorte.isEmpty() ? "NULL" : causaMorte).append(":");

        if (historicoPosicoes.isEmpty()) sb.append("NULL");
        else {
            for (int p : historicoPosicoes) sb.append(p).append(",");
            sb.deleteCharAt(sb.length()-1);
        }

        return sb.toString();
    }

    public void restaurarHistorico(String historicoStr) {
        historicoPosicoes.clear();
        if (!historicoStr.equals("NULL") && !historicoStr.isEmpty()) {
            String[] posicoes = historicoStr.split(",");
            for (String s : posicoes) {
                try { historicoPosicoes.add(Integer.parseInt(s)); } catch (NumberFormatException ignored) {}
            }
        } else historicoPosicoes.add(posicao);
    }

    @Override
    public String toString() {
        String ferramentasStr = getFerramentasToString();
        if (ferramentasStr.isEmpty()) ferramentasStr = "No tools";
        return id + " | " + nome + " | " + posicao + " | " + ferramentasStr + " | " + linguagens + " | " + emJogo;
    }
}
