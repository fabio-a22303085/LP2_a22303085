package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class GameManager {

    private List<Player> listaPlayers = new ArrayList<>();
    private HashMap<Integer, Player> allInfoPlayers = new HashMap<>();
    private List<Integer> idJogadores = new ArrayList<>();
    private HashMap<Integer, BoardElement> tabuleiro = new HashMap<>();

    private int tamanhoTabuleiro;
    private int numJogadores;
    private int[] currentPlayer;
    private int atual = 0;
    private int rondas = 0;
    private String vencedor;
    private int idJogadorQueMoveu;

    public List<Player> getPlayers() { return listaPlayers; }

    public int getNrSpaces() { return 0; } // Placeholder

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        listaPlayers.clear();
        allInfoPlayers.clear();
        idJogadores.clear();

        numJogadores = playerInfo.length;
        if (numJogadores <= 1 || numJogadores > 4) return false;

        currentPlayer = new int[numJogadores];
        List<String> cores = new ArrayList<>(Arrays.asList("Purple", "Green", "Blue", "Brown"));
        int cont = 0;
        for (String[] dados : playerInfo) {
            int id = Integer.parseInt(dados[0]);
            if (id < 0 || idJogadores.contains(id)) return false;
            String nome = dados[1];
            if (nome.isBlank()) return false;
            String linguagens = dados[2];
            String cor = dados[3];
            if (!cores.contains(cor)) return false;
            cores.remove(cor);
            currentPlayer[cont] = id;

            Player p = new Player(id, 1, nome, cor, linguagens);
            listaPlayers.add(p);
            allInfoPlayers.put(id, p);
            idJogadores.add(id);
            cont++;
        }
        if (worldSize < numJogadores * 2) return false;
        tamanhoTabuleiro = worldSize;
        return true;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        listaPlayers.clear();
        allInfoPlayers.clear();
        idJogadores.clear();
        tabuleiro.clear();
        vencedor = null;
        rondas = 0;
        atual = 0;

        if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) return false;
        this.numJogadores = playerInfo.length;
        if (worldSize < numJogadores * 2) return false;
        this.tamanhoTabuleiro = worldSize;

        currentPlayer = new int[numJogadores];
        List<String> coresDisponiveis = new ArrayList<>(Arrays.asList("Purple", "Green", "Blue", "Brown"));
        List<Integer> tempIds = new ArrayList<>();

        for (String[] info : playerInfo) {
            if (info.length < 4) return false;
            try {
                int id = Integer.parseInt(info[0]);
                String nome = info[1];
                String linguagens = info[2];
                String corSolicitada = info[3];
                if (id < 1 || idJogadores.contains(id)) return false;
                if (nome == null || nome.isBlank()) return false;
                if (!coresDisponiveis.contains(corSolicitada)) return false;
                coresDisponiveis.remove(corSolicitada);

                Player p = new Player(id, 1, nome, corSolicitada, linguagens);
                listaPlayers.add(p);
                allInfoPlayers.put(id, p);
                idJogadores.add(id);
                tempIds.add(id);
            } catch (NumberFormatException e) { return false; }
        }

        Collections.sort(tempIds); // Ordena IDs para respetar teste getCurrentPlayerID
        for (int i = 0; i < numJogadores; i++) currentPlayer[i] = tempIds.get(i);

        if (abyssesAndTools != null) {
            for (String[] dados : abyssesAndTools) {
                if (dados.length < 3) return false;
                try {
                    String tipoStr = dados[0];
                    int idItem = Integer.parseInt(dados[1]);
                    int posicao = Integer.parseInt(dados[2]);
                    if (posicao < 1 || posicao > worldSize) return false;
                    if (tabuleiro.containsKey(posicao)) return false;

                    BoardElement elemento = null;
                    if (tipoStr.equals("1")) {
                        switch (idItem) {
                            case 0: elemento = new HerancaTool(0, "Herança"); break;
                            case 1: elemento = new ProgramacaoFuncionalTool(1, "Programação Funcional"); break;
                            case 2: elemento = new UnitTestsTool(2, "Testes Unitários"); break;
                            case 3: elemento = new TratamentoExcepcoesTool(3, "Tratamento de Excepções"); break;
                            case 4: elemento = new IDETool(4, "IDE"); break;
                            case 5: elemento = new AjudaProfessorTool(5, "Ajuda do Professor"); break;
                            default: return false;
                        }
                    } else {
                        switch (idItem) {
                            case 0: elemento = new ErroSintaxeAbyss(posicao); break;
                            case 1: elemento = new ErroLogicaAbyss(posicao); break;
                            case 2: elemento = new ExceptionAbyss(posicao); break;
                            case 3: elemento = new FileNotFoundAbyss(posicao); break;
                            case 4: elemento = new CrashAbyss(posicao); break;
                            case 5: elemento = new CodigoDuplicadoAbyss(posicao); break;
                            case 6: elemento = new EfeitosSecundariosAbyss(posicao); break;
                            case 7: elemento = new BlueScreenAbyss(posicao); break;
                            case 8: elemento = new CicloInfinitoAbyss(posicao); break;
                            case 9: elemento = new SegmentationFaultAbyss(posicao); break;
                            case 20: elemento = new LLMAbyss(posicao); break;
                            default: return false;
                        }
                    }
                    tabuleiro.put(posicao, elemento);
                } catch (NumberFormatException e) { return false; }
            }
        }

        return true;
    }

    public String[] getSlotInfo(int pos) {
        if (pos < 1 || pos > tamanhoTabuleiro) return null;
        String[] result = new String[3];
        StringBuilder sbPlayers = new StringBuilder();
        boolean primeiro = true;
        for (Player p : listaPlayers) {
            if (p.getPosicao() == pos) {
                if (!primeiro) sbPlayers.append(",");
                sbPlayers.append(p.getId());
                primeiro = false;
            }
        }
        result[0] = sbPlayers.toString();
        if (tabuleiro.containsKey(pos)) {
            BoardElement el = tabuleiro.get(pos);
            result[1] = el.getTitle();
            result[2] = el.getTypePrefix() + ":" + el.getId();
        } else {
            result[1] = "";
            result[2] = "";
        }
        return result;
    }

    public int getCurrentPlayerID() { return currentPlayer[atual]; }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (numJogadores == 0 || nrSpaces < 1 || nrSpaces > 6) return false;

        idJogadorQueMoveu = currentPlayer[atual];
        Player p = allInfoPlayers.get(idJogadorQueMoveu);

        if (p.getEstado().equals("Preso") || p.getTurnosPreso() > 0) {
            p.setTurnosPreso(0);
            p.setEmJogo("Em Jogo"); // Só para próxima ronda
            rodarTurno();
            return false;
        }

        String lang = p.getPrimeiraLinguagem().trim();
        int limite = lang.equalsIgnoreCase("Assembly") ? 2 : (lang.equalsIgnoreCase("C") ? 3 : 6);

        if (nrSpaces > limite) {
            rodarTurno();
            return false;
        }

        p.setUltimoDado(nrSpaces);
        p.registarJogada();
        p.setPosicao(Math.min(p.getPosicao() + nrSpaces, tamanhoTabuleiro));

        rodarTurno();
        return true;
    }

    private void rodarTurno() {
        atual = (atual + 1) % numJogadores;
        int s = 0;
        while (allInfoPlayers.get(currentPlayer[atual]).getEstado().equals("Derrotado") && s < numJogadores) {
            atual = (atual + 1) % numJogadores;
            s++;
        }
        rondas++;
    }

    public boolean gameIsOver() {
        // Vitória
        for (Player p : listaPlayers) {
            if (p.getPosicao() >= tamanhoTabuleiro) {
                vencedor = p.getNome();
                return true;
            }
        }

        boolean alguemPodeMover = false;

        for (Player p : listaPlayers) {
            if (!p.getEstado().equals("Em Jogo")) continue;
            if (p.getTurnosPreso() > 0) continue;
            if (p.getPosicao() >= tamanhoTabuleiro) continue;

            String lang = p.getPrimeiraLinguagem().trim();
            int limite = lang.equalsIgnoreCase("Assembly") ? 2 : (lang.equalsIgnoreCase("C") ? 3 : 6);

            for (int dado = 1; dado <= 6; dado++) {
                if (dado <= limite) {
                    alguemPodeMover = true;
                    break;
                }
            }
            if (alguemPodeMover) break;
        }

        if (!alguemPodeMover) {
            vencedor = null;
            return true;
        }

        return false;
    }

    public ArrayList<String> restantes() {
        listaPlayers.sort((p1, p2) -> {
            int cmp = Integer.compare(p2.getPosicao(), p1.getPosicao());
            if (cmp != 0) return cmp;
            return p1.getNome().compareTo(p2.getNome());
        });

        ArrayList<String> resultado = new ArrayList<>();
        for (Player p : listaPlayers) {
            if (p.getPosicao() == tamanhoTabuleiro) continue;
            StringBuilder sb = new StringBuilder();
            sb.append(p.getNome());
            if (p.getEstado().equals("Derrotado")) sb.append(": ").append(p.getCausaMorte());
            else sb.append(" ").append(p.getPosicao());
            resultado.add(sb.toString());
        }
        return resultado;
    }

    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nome: FJ"));
        return panel;
    }

    public HashMap<String, String> customizeBoard() { return new HashMap<>(); }

    public String getProgrammersInfo() {
        StringBuilder sb = new StringBuilder();
        boolean primeiro = true;
        for (Player p : listaPlayers) {
            if (!p.getEstado().equals("Derrotado")) {
                if (!primeiro) sb.append(" | ");
                String ferramentas = p.getFerramentasToString();
                if (ferramentas.isEmpty()) ferramentas = "No tools";
                sb.append(p.getNome()).append(" : ").append(ferramentas);
                primeiro = false;
            }
        }
        return sb.toString();
    }

    // Métodos saveGame, loadGame e restaurarJogador/Elemento ficam iguais à tua versão final,
    // já tratados corretamente
    // ... (mantém código anterior)
}
