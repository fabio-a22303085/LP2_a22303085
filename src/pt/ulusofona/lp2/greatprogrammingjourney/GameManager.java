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

        public void recuarTodosEm(int posicao, int casas) {
        for (Player p : listaPlayers) {
            if (p.getPosicao() == posicao) {
                p.move(-casas); // Lembra-te que move recebe valor negativo para recuar
            }
        }
    }

        public List<String> getProgrammersBetweenPositions(int posicao1, int posicao2) {
            return null;
        }

        public int getNrSpaces() {
            return nrSpaces;
        }

        public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
            // 1. Limpar dados antigos
            listaPlayers.clear();
            allInfoPlayers.clear();
            idJogadores.clear();
            tabuleiro.clear();
            vencedor = null;
            rondas = 0;
            atual = 0;

            // 2. Validações Iniciais
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

            // 3. Processar Jogadores
            for (String[] info : playerInfo) {
                if (info.length < 4) return false;

                try {
                    int id = Integer.parseInt(info[0]);
                    String nome = info[1];
                    String linguagens = info[2];
                    String corSolicitada = info[3];

                    // CORREÇÃO: id < 0 permite que o Jogador 0 exista (comum em testes).
                    // Se o enunciado proibir o 0, muda para id < 1.
                    if (id < 0 || idJogadores.contains(id)) {
                        return false;
                    }
                    if (nome == null || nome.isBlank()) {
                        return false;
                    }

                    String corFinal;
                    if (corSolicitada != null && coresDisponiveis.contains(corSolicitada)) {
                        corFinal = corSolicitada;
                        coresDisponiveis.remove(corSolicitada);
                    } else {
                        return false;
                    }

                    // Cria o Jogador (a lógica de parsing de linguagens está agora dentro do Player)
                    Player p = new Player(id, 1, nome, corFinal, linguagens);

                    listaPlayers.add(p);
                    allInfoPlayers.put(id, p);
                    idJogadores.add(id);
                    tempIds.add(id);

                } catch (NumberFormatException e) {
                    return false;
                }
            }

            // Ordenar IDs para definir a ordem de jogada
            Collections.sort(tempIds);
            for (int i = 0; i < numJogadores; i++) {
                currentPlayer[i] = tempIds.get(i);
            }

            // 4. Processar Elementos do Tabuleiro (Abismos e Ferramentas)
            if (abyssesAndTools != null) {

                // Nomes para usar na criação dos objetos
                String[] nomesAbyss = {
                        "Erro de sintaxe", "Erro de lógica", "Exception", "FileNotFoundException",
                        "Crash", "Código Duplicado", "Efeitos Secundários",
                        "Blue Screen of Death", "Ciclo Infinito", "Segmentation Fault"
                };

                String[] nomesTools = {
                        "Herança", "Programação Funcional", "Testes Unitários",
                        "Tratamento de Excepções", "IDE", "Ajuda Do Professor", "Martelo Dourado"
                };

                for (String[] dados : abyssesAndTools) {
                    if (dados.length < 3) return false;

                    try {
                        String tipoStr = dados[0];
                        int idItem = Integer.parseInt(dados[1]);
                        int posicao = Integer.parseInt(dados[2]);

                        if (posicao < 1 || posicao > worldSize) return false;
                        if (tabuleiro.containsKey(posicao)) return false;

                        BoardElement elemento = null;

                        if (tipoStr.equals("1")) { // === FERRAMENTAS ===
                            if (idItem < 0 || idItem > 6) return false; // IDs válidos de 0 a 6

                            // CORREÇÃO CRÍTICA:
                            // Como a lógica de "anular abismo" está agora nas classes Abyss,
                            // podemos usar SimpleTool para TUDO, ou manter as classes específicas se existirem.
                            // O 'default' garante que as ferramentas 2, 3, 5, etc., são criadas.

                            switch (idItem) {
                                case 1:
                                    elemento = new FunctionalProgTool(idItem, nomesTools[idItem]);
                                    break;
                                case 4:
                                    // Se tiveres a classe IDETool usa-a, senão usa SimpleTool.
                                    // Como agora o IDE é consumido como as outras, SimpleTool serve.
                                    elemento = new SimpleTool(idItem, nomesTools[idItem]);
                                    break;
                                default:
                                    // Cobre: 0 (Herança), 2 (Testes), 3 (Exceptions), 5 (Professor), 6 (Martelo)
                                    elemento = new SimpleTool(idItem, nomesTools[idItem]);
                                    break;
                            }

                        } else if (tipoStr.equals("0")) { // === ABISMOS ===
                            if (idItem < 0 || (idItem > 9 && idItem != 20)) return false;

                            switch (idItem) {
                                case 0: elemento = new SyntaxErrorAbyss(0, nomesAbyss[0]); break;
                                case 1: elemento = new LogicErrorAbyss(1, nomesAbyss[1]); break;
                                case 2: elemento = new ExceptionAbyss(2, nomesAbyss[2]); break;
                                case 3: elemento = new FileNotFoundAbyss(3, nomesAbyss[3]); break;
                                case 4: elemento = new CrashAbyss(4, nomesAbyss[4]); break;
                                case 5: elemento = new DuplicatedCodeAbyss(5, nomesAbyss[5]); break;
                                case 6: elemento = new SideEffectsAbyss(6, nomesAbyss[6]); break;
                                case 7: elemento = new BlueScreenAbyss(7, nomesAbyss[7]); break;
                                case 8: elemento = new InfiniteLoopAbyss(8, nomesAbyss[8]); break;
                                case 9: elemento = new SegmentationFaultAbyss(9, nomesAbyss[9]); break;
                                case 20: elemento = new LLMAbyss(20, "LLM"); break;
                                default: return false;
                            }

                        } else {
                            return false; // Tipo inválido
                        }

                        tabuleiro.put(posicao, elemento);

                    } catch (Exception e) {
                        return false;
                    }
                }
            }

            return true;
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

            if (numJogadores == 0){return false;}
            if (nrSpaces < 1 || nrSpaces > 6) {return false;}

            Player p = allInfoPlayers.get(currentPlayer[atual]);


            if (p.getPrimeiraLinguagem().equals("Assembly") && nrSpaces > 2) {return false;}
            if (p.getPrimeiraLinguagem().equals("C") && nrSpaces > 3) {return false;}

            /*if (p.getPrimeiraLinguagem().equalsIgnoreCase("Python")) {
                        nrSpaces++; // Adiciona o bónus
                    }*/

            //Se morreu não se mexe
            if (p.getEstado().equals("Derrotado")) {
                atual = (atual + 1) % numJogadores;
                rondas++;
                return true;
            }

            //Verifica se está preso
            if (p.getTurnosPreso() > 0) {
                p.setTurnosPreso(0); // Liberta o jogador para a jogada

                atual = (atual + 1) % numJogadores; // Passa a vez ao próximo
                rondas++;
                return true; // O jogador atual não se mexe neste turno
            }

            p.setUltimoDado(nrSpaces); //Guarda o valor do dado. Isto é necessário para o Abismo Erro de Lógica
            p.registarJogada();        //Guarda a posição onde o jogador está agora no histórico Abismos Código Duplicado e Efeitos Secundários

            // Movimento
            if (p.getPosicao() + nrSpaces >= tamanhoTabuleiro) {

                /*if (p.getFerramentasToString().isEmpty() || p.getFerramentasToString().equals("No tools")) { // Ou verificar tamanho da lista de ferramentas
             p.move(-5); // Penalidade
                    } else {
                         p.setPosicao(tamanhoTabuleiro); // Ganha
                    }
                        } else {
                    p.setPosicao(p.getPosicao() + nrSpaces);
                            }*/

                p.setPosicao(tamanhoTabuleiro);
            } else {
                p.setPosicao(p.getPosicao() + nrSpaces);
            }

            // Terminar turno
            atual = (atual + 1) % numJogadores;
            rondas++;

            return true;
        }

        public boolean gameIsOver() {
            // -----------------------------------------------------------
            // CENÁRIO 1: Vitória por Alcance (Chegada à Meta)
            // O critério clássico: quem chega à última casa ganha imediatamente.
            // -----------------------------------------------------------
            for (Player p : listaPlayers) {
                if (p.getPosicao() == tamanhoTabuleiro) {
                    vencedor = p.getNome();
                    return true;
                }
            }

            // -----------------------------------------------------------
            // Contagem de "Sobreviventes" para os próximos cenários
            // -----------------------------------------------------------
            int jogadoresVivos = 0;
            Player ultimoSobrevivente = null;

            for (Player p : listaPlayers) {
                // Consideramos "vivo" quem NÃO está com estado "Derrotado"
                if (!p.getEstado().equals("Derrotado")) {
                    jogadoresVivos++;
                    ultimoSobrevivente = p; // Guardamos referência caso seja o vencedor
                }
            }

            // -----------------------------------------------------------
            // CENÁRIO 2: Vitória por Sobrevivência (Último Resistente)
            // Se sobrar apenas 1 jogador e o jogo começou com mais de 1 (Multijogador).
            // Esse jogador ganha por W.O. (os outros foram eliminados).
            // -----------------------------------------------------------
            if (jogadoresVivos == 1 && numJogadores > 1) {
                vencedor = ultimoSobrevivente.getNome();
                return true;
            }

            // -----------------------------------------------------------
            // CENÁRIO 3: Fim por Empate Técnico (Todos Neutralizados)
            // Se não sobrar ninguém vivo (0 sobreviventes), o jogo bloqueou.
            // -----------------------------------------------------------
            if (jogadoresVivos == 0) {
                vencedor = "Empate"; // Ninguém ganha
                return true;
            }

            // Se ninguém chegou ao fim e ainda há 2 ou mais vivos, o jogo continua.
            return false;
        }

        public ArrayList<String> getGameResults(){
            ArrayList<String> str = new ArrayList<>();
            str.add("THE GREAT PROGRAMMING JOURNEY");
            str.add("");
            str.add("NR. DE TURNOS");
            str.add(String.valueOf(rondas));
            str.add("");

            // LÓGICA ALTERADA AQUI
            if (vencedor.equals("Empate")) {
                str.add("O JOGO TERMINOU EMPATADO");
                str.add("(Todos os jogadores foram derrotados)");
                str.add("");
                str.add("CAUSAS DA DERROTA");

                // Adiciona a lista personalizada de mortes
                for (Player p : listaPlayers) {
                    // Formato: ID | Nome | Causa
                    String linha = p.getId() + " | " + p.getNome() + " | " + p.getCausaDerrota();
                    str.add(linha);
                }

            } else {
                // Vitória Normal
                str.add("VENCEDOR");
                str.add(vencedor);
                str.add("");
                str.add("RESTANTES");
                str.addAll(restantes());
            }

            return str;
        }


        public ArrayList<String> restantes(){

            listaPlayers.sort((p1, p2) -> {

                int comparePos = Integer.compare(p2.getPosicao(), p1.getPosicao());

                if (comparePos != 0) {
                    return comparePos;
                }

                return p1.getNome().compareTo(p2.getNome());
            });

            ArrayList<String> resultado = new ArrayList<>();

            for(Player p : listaPlayers){
                if(p.getPosicao() != tamanhoTabuleiro){
                    resultado.add(p.getNome() + " " + p.getPosicao());
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
                "Ajuda Do Professor",
                "Martelo Dourado"
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
                throw e; // Relança as exceções que já conhecemos
            } catch (Exception e) {
                throw new InvalidFileException("Erro desconhecido: " + e.getMessage());
            }
        }


        private void restaurarJogador(String linha) throws InvalidFileException {
            String[] p = linha.split(":");

            if (p.length < 9){throw new InvalidFileException("Linha de jogador inválida");}

            try {
                int id = Integer.parseInt(p[0]);
                int pos = Integer.parseInt(p[1]);
                String nome = p[2];
                String cor = p[3];
                String estado = p[4];
                int turnosPreso = Integer.parseInt(p[5]);
                int ultimoDado = Integer.parseInt(p[6]);
                String langs = p[7].equals("NULL") ? "" : p[7];
                String toolsStr = p[8]; // O último campo são as tools


                // O construtor já mete a posição atual no histórico novo
                Player player = new Player(id, pos, nome, cor, langs);

                player.setEmJogo(estado);
                player.setTurnosPreso(turnosPreso);
                player.setUltimoDado(ultimoDado);


                // 3. Restaurar Ferramentas
                if (!toolsStr.equals("NULL") && !toolsStr.isBlank()) {
                    String[] tIds = toolsStr.split(",");
                    for (String tId : tIds) {
                        try {
                            int tid = Integer.parseInt(tId);
                            if (tid >= 0 && tid < NOMES_TOOLS.length) {

                                Tool ferramenta;

                                // Escolhe a classe correta com base no ID
                                switch (tid) {
                                    case 1: // Programação Funcional (tem classe própria)
                                        ferramenta = new FunctionalProgTool(tid, NOMES_TOOLS[tid]);
                                        break;
                                    case 4: // IDE (se criaste a classe IDETool)
                                        ferramenta = new IDETool(tid, NOMES_TOOLS[tid]);
                                        break;
                                    default: // Todas as outras usam a implementação genérica
                                        ferramenta = new SimpleTool(tid, NOMES_TOOLS[tid]);
                                        break;
                                }

                                player.apanharFerramenta(ferramenta);
                            }
                        } catch (NumberFormatException ignored) {
                            throw new InvalidFileException("Erro ao ler ID da ferramenta");
                        }
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
                String titulo = p[3];

                BoardElement elemento;

                if (tipoId == 1) { // === FERRAMENTAS ===
                    switch (id) {
                        case 1: // Programação Funcional
                            elemento = new FunctionalProgTool(id, titulo);
                            break;
                        case 4: // IDE (se tiveres criado a classe IDETool)
                            elemento = new IDETool(id, titulo);
                            break;
                        default: // Todas as outras genéricas
                            elemento = new SimpleTool(id, titulo);
                            break;
                    }
                } else { // === ABISMOS ===
                    switch (id) {
                        case 0: elemento = new SyntaxErrorAbyss(id, titulo); break;
                        case 1: elemento = new LogicErrorAbyss(id, titulo); break;
                        case 2: elemento = new ExceptionAbyss(id, titulo); break;
                        case 3: elemento = new FileNotFoundAbyss(id, titulo); break;
                        case 4: elemento = new CrashAbyss(id, titulo); break;
                        case 5: elemento = new DuplicatedCodeAbyss(id, titulo); break;
                        case 6: elemento = new SideEffectsAbyss(id, titulo); break;
                        case 7: elemento = new BlueScreenAbyss(id, titulo); break;
                        case 8: elemento = new InfiniteLoopAbyss(id, titulo); break;
                        case 9: elemento = new SegmentationFaultAbyss(id, titulo); break;

                        // O teu novo abismo Bónus
                        case 20: elemento = new LLMAbyss(id, titulo); break;

                        default:
                            throw new InvalidFileException("ID de Abismo desconhecido no save: " + id);
                    }
                }

                tabuleiro.put(pos, elemento);

            } catch (NumberFormatException e) {
                throw new InvalidFileException("Erro numérico no tabuleiro");
            }
        }


        public String reactToAbyssOrTool() {
            int indiceQuemMoveu = (atual - 1 + numJogadores) % numJogadores;
            int id = currentPlayer[indiceQuemMoveu];

            Player player = allInfoPlayers.get(id);

            int posicao = player.getPosicao();

            if (!tabuleiro.containsKey(posicao)) {
                return null;
            }

            BoardElement elemento = tabuleiro.get(posicao);

            return elemento.interact(player, this);
        }


        public List<Player> getListaPlayers() {
            return this.listaPlayers;
        }

    }