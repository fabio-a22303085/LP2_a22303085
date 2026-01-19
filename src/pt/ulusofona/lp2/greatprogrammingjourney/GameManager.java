package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


public class GameManager {

    private List<Player> listaPlayers = new ArrayList<>();
    private HashMap<Integer,Player> allInfoPlayers = new HashMap<>();
    private List<Integer> idJogadores= new ArrayList<>();
    private HashMap<Integer, BoardElement> tabuleiro = new HashMap<>();


    private int tamanhoTabuleiro;
    private int numJogadores;
    private int[] currentPlayer;
    private int atual = 0;
    private int rondas = 0;
    private String vencedor;
    private int nrSpaces = 0;

    private int idJogadorQueMoveu; // Variável imperativa para clareza

    public List<Player> getPlayers() {
        return listaPlayers;
    }

    public int getNrSpaces() {
        return nrSpaces;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize){
        listaPlayers.clear();
        allInfoPlayers.clear();
        idJogadores.clear();

        numJogadores = playerInfo.length;
        if(numJogadores<=1 || numJogadores>4){return false;}
        currentPlayer = new int[numJogadores];
        List<String> cores = new ArrayList<>(Arrays.asList("Purple", "Green", "Blue", "Brown"));
        int cont=0;
        for(int i=0;i<numJogadores;i++){
            String[] dados = playerInfo[i];

            int id= Integer.parseInt(dados[0]);
            if(id<0 || idJogadores.contains(id)){return false;}

            String nome = dados[1];
            if(nome.isBlank() || nome.isEmpty()){return false;}

            String linguagens = dados[2];
            String cor = dados[3];
            if(!cores.contains(cor)){return false;}
            cores.remove(cor);

            currentPlayer[cont]=id;

            Player p = new Player(id, 1, nome, cor, linguagens);
            listaPlayers.add(p);
            allInfoPlayers.put(id, p);
            idJogadores.add(id);
            cont++;
        }
        if(worldSize<numJogadores*2){return false;}
        tamanhoTabuleiro= worldSize;
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

        if (playerInfo == null || playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        this.numJogadores = playerInfo.length;

        if (worldSize < numJogadores * 2) {
            return false;
        }
        this.tamanhoTabuleiro = worldSize;

        currentPlayer = new int[numJogadores];

        List<String> coresDisponiveis = new ArrayList<>(Arrays.asList("Purple", "Green", "Blue", "Brown"));
        List<Integer> tempIds = new ArrayList<>();

        for (String[] info : playerInfo) {
            if (info.length < 4){return false;}

            try {
                int id = Integer.parseInt(info[0]);
                String nome = info[1];
                String linguagens = info[2];
                String corSolicitada = info[3];

                if (id < 1 || idJogadores.contains(id)){
                    return false;
                }
                if (nome == null || nome.isBlank()){
                    return false;
                }

                String corFinal;
                if (corSolicitada != null && coresDisponiveis.contains(corSolicitada)) {
                    corFinal = corSolicitada;
                    coresDisponiveis.remove(corSolicitada);
                } else {
                    return false;
                }

                Player p = new Player(id, 1, nome, corFinal, linguagens);

                listaPlayers.add(p);
                allInfoPlayers.put(id, p);
                idJogadores.add(id);
                tempIds.add(id);

            } catch (NumberFormatException e) {
                return false;
            }
        }

        Collections.sort(tempIds);
        for (int i = 0; i < numJogadores; i++) {
            currentPlayer[i] = tempIds.get(i);
        }


        if (abyssesAndTools != null) {
            int[] countAbyss = new int[21];
            int[] countTools = new int[6];

            String[] nomesAbyss = {"Erro de sintaxe", "Erro de lógica", "Exception", "FileNotFoundException",
                    "Crash", "Código Duplicado", "Efeitos Secundários",
                    "Blue Screen of Death", "Ciclo Infinito", "Segmentation Fault"};

            String[] nomesTools = {"Herança", "Programação Funcional", "Testes Unitários",
                    "Tratamento de Excepções", "IDE", "Ajuda Do Professor"};

            for (String[] dados : abyssesAndTools) {
                if (dados.length < 3){return false;}

                try {
                    String tipoStr = dados[0];
                    int idItem = Integer.parseInt(dados[1]);
                    int posicao = Integer.parseInt(dados[2]);

                    if (posicao < 1 || posicao > worldSize){return false;}

                    if (tabuleiro.containsKey(posicao)){return false;}

                    if (tipoStr.equals("0")) { // ABISMO
                        if ((idItem < 0 || idItem > 9) && idItem != 20){return false;}
                    } else if (tipoStr.equals("1")) { // FERRAMENTA
                        if (idItem < 0 || idItem > 5){return false;}
                    } else {
                        return false;
                    }

                    BoardElement elemento = null;

                    if (tipoStr.equals("1")) { // Adapta a condição conforme estejas no create ou no restore

                        // Switch para criar a Ferramenta específica
                        switch (idItem) { // ou 'id' se estiveres no restaurarElementoTabuleiro
                            case 0: elemento = new HerancaTool(0, "Herança"); break;
                            case 1: elemento = new ProgramacaoFuncionalTool(1, "Programação Funcional"); break;
                            case 2: elemento = new UnitTestsTool(2, "Testes Unitários"); break;
                            case 3: elemento = new TratamentoExcepcoesTool(3, "Tratamento de Excepções"); break;
                            case 4: elemento = new IDETool(4, "IDE"); break;
                            case 5: elemento = new AjudaProfessorTool(5, "Ajuda do Professor"); break;
                            default: return false; // ou throw exception dependendo do método
                        }

                    } else {
                        // Criação dos Abismos Específicos
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
                    } tabuleiro.put(posicao, elemento);

                } catch (NumberFormatException e) {
                    return false;
                }
            }
        } return true;
    }


    public String[] getSlotInfo(int pos) {
        if (pos < 1 || pos > tamanhoTabuleiro) {
            return null;
        }

        String[] result = new String[3];

        // IDs dos jogadores na casa separados por vírgula
        StringBuilder sbPlayers = new StringBuilder();
        boolean primeiro = true;

        for (Player p : listaPlayers) {
            if (p.getPosicao() == pos) {
                if (!primeiro) {
                    sbPlayers.append(",");
                }
                sbPlayers.append(p.getId());
                primeiro = false;
            }
        }
        result[0] = sbPlayers.toString(); // Retorna "1,2" ou "" se vazio

        // Verifica se existe algum Abismo ou Ferramenta nesta posição
        if (tabuleiro.containsKey(pos)) {
            BoardElement elemento = tabuleiro.get(pos);

            result[1] = elemento.getTitle();

            // O elemento já sabe se é "A" ou "T"
            result[2] = elemento.getTypePrefix() + ":" + elemento.getId();

        } else {
            result[1] = "";
            result[2] = "";
        }

        return result;
    }


    public int getCurrentPlayerID(){
        return currentPlayer[atual];
    }

    public String getImagePng(int nrSquare){
        return null;
    }

    public String[] getProgrammerInfo(int id){
        if(!allInfoPlayers.containsKey(id)){return null;}

        Player p = allInfoPlayers.get(id);
        String[] result = new String[7];
        result[0]=String.valueOf(p.getId());
        result[1]=p.getNome();
        result[2]=p.getLinguagens();
        result[3]=p.getCor();
        result[4]=String.valueOf(p.getPosicao());
        result[5] = p.getFerramentasToString();
        result[6] = p.getEstado();

        return result;
    }

    public String getProgrammerInfoAsStr(int id){
        if(!allInfoPlayers.containsKey(id)){return null;}
        return allInfoPlayers.get(id).toString();
    }


    public boolean moveCurrentPlayer(int nrSpaces) {
        this.nrSpaces = nrSpaces;

        // 1. Validações básicas de input
        if (numJogadores == 0 || nrSpaces < 1 || nrSpaces > 6) { return false; }

        // 2. Fixar quem é o "autor" da jogada para o reactToAbyssOrTool
        idJogadorQueMoveu = currentPlayer[atual];
        Player p = allInfoPlayers.get(idJogadorQueMoveu);

        // 3. Caso: Jogador Preso
        if (p.getTurnosPreso() > 0) {
            p.setTurnosPreso(0);
            p.setEmJogo("Em Jogo");
            rodarTurno();
            return false; // Teste 004: gasta turno mas não move
        }

        // 4. Caso: Restrição de Linguagem (Assembly/C)
        if ((p.getPrimeiraLinguagem().equals("Assembly") && nrSpaces > 2) ||
                (p.getPrimeiraLinguagem().equals("C") && nrSpaces > 3)) {
            rodarTurno(); // IMPORTANTE: Passa a vez mesmo que não mova
            return false; // Teste 004: movimento inválido
        }

        // 5. Caso: Movimento Válido
        p.setUltimoDado(nrSpaces);
        p.registarJogada();
        p.setPosicao(Math.min(p.getPosicao() + nrSpaces, tamanhoTabuleiro));

        rodarTurno();
        return true;
    }

    private void rodarTurno() {
        // Avança pelo menos um
        atual = (atual + 1) % numJogadores;

        // Salta quem está Derrotado
        int s = 0;
        while (allInfoPlayers.get(currentPlayer[atual]).getEstado().equals("Derrotado") && s < numJogadores) {
            atual = (atual + 1) % numJogadores;
            s++;
        }
        rondas++;
    }


    public boolean gameIsOver() {
        // 1. Alguém chegou à meta? (Vitória)
        for (Player p : listaPlayers) {
            if (p.getPosicao() == tamanhoTabuleiro) {
                vencedor = p.getNome();
                return true;
            }
        }

        // 2. Bloqueio por Eliminação: Resta algum jogador que não esteja "Derrotado"?
        boolean alguemVivo = false;
        for (Player p : listaPlayers) {
            if (!p.getEstado().equals("Derrotado")) {
                alguemVivo = true;
                break;
            }
        }

        // Se ninguém está vivo, o jogo termina em empate
        if (!alguemVivo) {
            vencedor = null;
            return true;
        }

        return false;
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> str = new ArrayList<>();
        str.add("THE GREAT PROGRAMMING JOURNEY");
        str.add("");
        str.add("NR. DE TURNOS");
        str.add(String.valueOf(rondas));
        str.add("");
        str.add("VENCEDOR");

        if (vencedor != null) {
            str.add(vencedor);
        } else {
            str.add("O jogo acabou empatado.");
        }

        str.add("");
        str.add("RESTANTES");
        str.addAll(restantes());
        return str;
    }


    public ArrayList<String> restantes() {
        // Ordenar: Quem está mais à frente primeiro, depois por nome
        listaPlayers.sort((p1, p2) -> {
            int comparePos = Integer.compare(p2.getPosicao(), p1.getPosicao());
            if (comparePos != 0) {
                return comparePos;
            }
            return p1.getNome().compareTo(p2.getNome());
        });

        ArrayList<String> resultado = new ArrayList<>();

        for (Player p : listaPlayers) {
            // Ignora o vencedor (quem chegou à meta)
            if (p.getPosicao() != tamanhoTabuleiro) {

                StringBuilder sb = new StringBuilder();
                sb.append(p.getNome())
                        .append(" (").append(p.getId()).append(")");

                // Se estiver morto/derrotado, mostra a causa
                if (p.getEstado().equals("Derrotado")) {
                    sb.append(": ").append(p.getCausaMorte());
                } else {
                    // Se estiver vivo mas perdeu (ex: jogo acabou empatado ou outro ganhou)
                    sb.append(" ").append(p.getPosicao());
                }

                resultado.add(sb.toString());
            }
        }
        return resultado;
    }

    public JPanel getAuthorsPanel(){
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nome: FJ"));
        return panel;
    }

    public HashMap<String, String> customizeBoard(){
        return new HashMap<>();
    }

    public String getProgrammersInfo() {
        if (listaPlayers == null || listaPlayers.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean primeiro = true;

        for (Player p : listaPlayers) {
            if (!p.getEstado().equals("Derrotado")) {

                if (!primeiro) {
                    sb.append(" | ");
                }

                String ferramentas = p.getFerramentasToString();
                if (ferramentas.isEmpty()) {
                    ferramentas = "No tools";
                }
                sb.append(p.getNome())
                        .append(" : ")
                        .append(ferramentas);

                primeiro = false;
            }
        }

        return sb.toString();
    }

    public boolean saveGame(File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

            writer.println(tamanhoTabuleiro);
            writer.println(numJogadores);
            writer.println(atual);
            writer.println(rondas);

            writer.println(listaPlayers.size());

            for (Player p : listaPlayers) {
                writer.println(p.getDataForSave());
            }

            writer.println(tabuleiro.size());

            for (Map.Entry<Integer, BoardElement> entry : tabuleiro.entrySet()) {
                int pos = entry.getKey();
                BoardElement el = entry.getValue();

                // CORREÇÃO AQUI (Sem instanceof):
                // O elemento já sabe se é 0 ou 1
                int tipo = el.getTypeId();

                writer.println(pos + ":" + tipo + ":" + el.getId() + ":" + el.getTitle());
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private static final String[] NOMES_TOOLS = {
            "Herança",
            "Programação Funcional",
            "Testes Unitários",
            "Tratamento de Excepções",
            "IDE",
            "Ajuda Do Professor"
    };

    public void loadGame(File file) throws FileNotFoundException, InvalidFileException {
        if (!file.exists()) {
            throw new FileNotFoundException("Ficheiro não encontrado");
        }

        listaPlayers.clear();
        allInfoPlayers.clear();
        idJogadores.clear();
        tabuleiro.clear();
        currentPlayer = null;

        try (Scanner scanner = new Scanner(file)) {

            if (!scanner.hasNextLine()){throw new InvalidFileException("Ficheiro vazio");}

            try {
                this.tamanhoTabuleiro = Integer.parseInt(scanner.nextLine());
                this.numJogadores = Integer.parseInt(scanner.nextLine());
                this.atual = Integer.parseInt(scanner.nextLine()); // Índice de quem joga a seguir
                this.rondas = Integer.parseInt(scanner.nextLine());

                if (tamanhoTabuleiro <= 0 || numJogadores < 0) {
                    throw new InvalidFileException("Dados globais inválidos");
                }

                this.currentPlayer = new int[numJogadores];

            } catch (NumberFormatException e) {
                throw new InvalidFileException("Erro no cabeçalho");
            }

            if (!scanner.hasNextLine()){throw new InvalidFileException("Falta nº jogadores");}
            int qtdPlayers;
            try {
                qtdPlayers = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { throw new InvalidFileException("Erro numérico qtd players"); }

            for (int i = 0; i < qtdPlayers; i++) {
                if (!scanner.hasNextLine()){throw new InvalidFileException("Fim inesperado (players)");}
                restaurarJogador(scanner.nextLine());
            }

            idJogadores.sort(Integer::compareTo);
            for (int i = 0; i < numJogadores; i++) {
                if (i < idJogadores.size()) {
                    currentPlayer[i] = idJogadores.get(i);
                }
            }

            if (!scanner.hasNextLine()){throw new InvalidFileException("Falta nº elementos tabuleiro");}
            int qtdElementos;
            try {
                qtdElementos = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { throw new InvalidFileException("Erro numérico qtd elementos"); }

            for (int i = 0; i < qtdElementos; i++) {
                if (!scanner.hasNextLine()){throw new InvalidFileException("Fim inesperado (tabuleiro)");}
                restaurarElementoTabuleiro(scanner.nextLine());
            }

        } catch (FileNotFoundException | InvalidFileException e) {
            throw e; // Relança as esperadas
        } catch (Exception e) {
            throw new InvalidFileException("Erro desconhecido: " + e.getMessage());
        }
    }


    private void restaurarJogador(String linha) throws InvalidFileException {
        String[] p = linha.split(":");

        if (p.length < 11){throw new InvalidFileException("Linha de jogador inválida");}

        try {
            int id = Integer.parseInt(p[0]);
            int pos = Integer.parseInt(p[1]);
            String nome = p[2];
            String cor = p[3];
            String estado = p[4];
            int turnosPreso = Integer.parseInt(p[5]);
            int ultimoDado = Integer.parseInt(p[6]);
            String langs = p[7].equals("NULL") ? "" : p[7];
            String toolsStr = p[8];
            String causaMorteStr = p[9];   // NOVO
            String historicoStr = p[10];    //NOVO

            // O construtor já mete a posição atual no histórico novo
            Player player = new Player(id, pos, nome, cor, langs);

            player.setEmJogo(estado);
            player.setTurnosPreso(turnosPreso);
            player.setUltimoDado(ultimoDado);

            // Restaurar Causa Morte
            if (!causaMorteStr.equals("NULL")) {
                player.setCausaMorte(causaMorteStr);
            }

            // Restaurar Histórico (CRUCIAL para o LLM funcionar)
            player.restaurarHistorico(historicoStr);


            // 3. Restaurar Ferramentas
            if (!toolsStr.equals("NULL") && !toolsStr.isBlank()) {
                String[] tIds = toolsStr.split(",");
                for (String tId : tIds) {
                    try {
                        int tid = Integer.parseInt(tId);
                        if (tid >= 0 && tid < NOMES_TOOLS.length) {
                            player.apanharFerramenta(new Tool(tid, NOMES_TOOLS[tid]));
                        }
                    } catch (NumberFormatException ignored) {throw new InvalidFileException("Erro");}
                }
            }

            // 4. Guardar nas listas
            listaPlayers.add(player);
            allInfoPlayers.put(id, player);
            idJogadores.add(id);

        } catch (NumberFormatException e) {
            throw new InvalidFileException("Erro numérico no jogador");
        }
    }


    private void restaurarElementoTabuleiro(String linha) throws InvalidFileException {
        // Formato: Posicao:TipoID:ID:Titulo
        String[] p = linha.split(":");
        if (p.length < 4){throw new InvalidFileException("Linha de tabuleiro inválida");}

        try {
            int pos = Integer.parseInt(p[0]);
            int tipoId = Integer.parseInt(p[1]); // 0 = Abyss, 1 = Tool
            int id = Integer.parseInt(p[2]);
            String titulo = p[3]; // O título já vem no ficheiro

            BoardElement elemento = null;

            if (tipoId == 1) { // Adapta a condição conforme estejas no create ou no restore

                // Switch para criar a Ferramenta específica
                switch (id) { // ou 'id' se estiveres no restaurarElementoTabuleiro
                    case 0: elemento = new HerancaTool(0, "Herança"); break;
                    case 1: elemento = new ProgramacaoFuncionalTool(1, "Programação Funcional"); break;
                    case 2: elemento = new UnitTestsTool(2, "Testes Unitários"); break;
                    case 3: elemento = new TratamentoExcepcoesTool(3, "Tratamento de Excepções"); break;
                    case 4: elemento = new IDETool(4, "IDE"); break;
                    case 5: elemento = new AjudaProfessorTool(5, "Ajuda do Professor"); break;
                    default: throw new InvalidFileException("ID Ferramenta inválido: " + id);
                }

            } else {
                // AQUI: Copia o switch case de cima (CreateInitialBoard) para criares o objeto correto!
                switch (id) {
                    case 0:
                        elemento = new ErroSintaxeAbyss(pos);
                        break;
                    case 1:
                        elemento = new ErroLogicaAbyss(pos);
                        break;
                    case 2:
                        elemento = new ExceptionAbyss(pos);
                        break;
                    case 3:
                        elemento = new FileNotFoundAbyss(pos);
                        break;
                    case 4:
                        elemento = new CrashAbyss(pos);
                        break;
                    case 5:
                        elemento = new CodigoDuplicadoAbyss(pos);
                        break;
                    case 6:
                        elemento = new EfeitosSecundariosAbyss(pos);
                        break;
                    case 7:
                        elemento = new BlueScreenAbyss(pos);
                        break;
                    case 8:
                        elemento = new CicloInfinitoAbyss(pos);
                        break;
                    case 9:
                        elemento = new SegmentationFaultAbyss(pos);
                        break;

                    case 20:
                        elemento = new LLMAbyss(pos);
                        break;

                    default:
                        throw new InvalidFileException("ID de Abismo desconhecido: " + id);
                }
            }

            tabuleiro.put(pos, elemento);

        } catch (NumberFormatException e) {
            throw new InvalidFileException("Erro numérico no tabuleiro");
        }
    }


    public String reactToAbyssOrTool() {
        Player p = allInfoPlayers.get(idJogadorQueMoveu);
        BoardElement el = tabuleiro.get(p.getPosicao());

        if (el == null) { return null; }

        return el.interact(p, this);
    }

}