    package pt.ulusofona.lp2.greatprogrammingjourney;

    import javax.swing.*;
    import java.io.File;
    import java.io.FileNotFoundException;
    import java.util.*;
    import java.io.File;
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

        public int getNrSpaces() {
            return nrSpaces;
        }

        public boolean createInitialBoard(String[][] playerInfo, int worldSize){
            listaPlayers.clear();
            allInfoPlayers.clear();
            idJogadores.clear();

            numJogadores = playerInfo.length;
            if(numJogadores<=1 || numJogadores>4){return false;}//----------
            currentPlayer = new int[numJogadores];
            List<String> cores = new ArrayList<>(Arrays.asList("Purple", "Green", "Blue", "Brown"));
            int cont=0;
            for(int i=0;i<numJogadores;i++){
                String[] dados = playerInfo[i];

                int id= Integer.parseInt(dados[0]);
                if(id<0 || idJogadores.contains(id)){return false;}//----------

                String nome = dados[1];
                if(nome.isBlank() || nome.isEmpty()){return false;}//----------

                String linguagens = dados[2];
                String cor = dados[3];
                if(!cores.contains(cor)){return false;}//----------
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
                int[] countAbyss = new int[10];
                int[] countTools = new int[6];

                String[] nomesAbyss = {"Erro de sintaxe", "Erro de lógica", "Exception", "FileNotFoundException",
                        "Crash", "Código duplicado", "Efeitos secundários",
                        "Blue Screen of Death", "Ciclo infinito", "Segmentation fault"};
                String[] nomesTools = {"Herança", "Programação funcional", "Testes unitários",
                        "Tratamento de excepções", "IDE", "Ajuda do professor"};

                for (String[] dados : abyssesAndTools) {
                    if (dados.length < 3){return false;}

                    try {
                        String tipoStr = dados[0];
                        int idItem = Integer.parseInt(dados[1]);
                        int posicao = Integer.parseInt(dados[2]);

                        if (posicao < 1 || posicao > worldSize){return false;}

                        if (tabuleiro.containsKey(posicao)){return false;}

                        if (tipoStr.equals("0")) { // ABISMO
                            if (idItem < 0 || idItem > 9){return false;}
                        } else if (tipoStr.equals("1")) { // FERRAMENTA
                            if (idItem < 0 || idItem > 5){return false;}
                        } else {
                            return false;
                        }

                        BoardElement elemento = null;

                        if (tipoStr.equals("1")) {
                            elemento = new Tool(idItem, nomesTools[idItem]);
                        } else {
                            switch (idItem) {
                                case 0: elemento = new Abyss(0, posicao, nomesAbyss[0], ""); break;
                                case 1: elemento = new Abyss(1, posicao, nomesAbyss[1], ""); break;
                                case 2: elemento = new Abyss(2, posicao, nomesAbyss[2], ""); break;
                                case 3: elemento = new Abyss(3, posicao, nomesAbyss[3], ""); break;
                                case 4: elemento = new Abyss(4, posicao, nomesAbyss[4], ""); break;
                                case 5: elemento = new Abyss(5, posicao, nomesAbyss[5], ""); break;
                                case 6: elemento = new Abyss(6, posicao, nomesAbyss[6], ""); break;
                                case 7: elemento = new Abyss(7, posicao, nomesAbyss[7], ""); break;
                                case 8: elemento = new Abyss(8, posicao, nomesAbyss[8], ""); break;
                                case 9: elemento = new Abyss(9, posicao, nomesAbyss[9], ""); break;
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
            // 1. Validação de Limites (Requisito: retorna null se inválido)
            if (pos < 1 || pos > tamanhoTabuleiro) {
                return null;
            }

            String[] result = new String[3];

            // --- PARTE 1: Jogadores (Índice 0) ---
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

            // --- PARTE 2: Tabuleiro (Índice 1 e 2) ---
            // Verifica se existe algum Abismo ou Ferramenta nesta posição
            if (tabuleiro.containsKey(pos)) {
                BoardElement elemento = tabuleiro.get(pos);

                // Índice [1]: Descrição/Título
                result[1] = elemento.getTitle();

                // Índice [2]: Tipo formatado (A:ID ou T:ID)
                if (elemento instanceof Tool) {
                    result[2] = "T:" + elemento.getId();
                } else if (elemento instanceof Abyss) {
                    result[2] = "A:" + elemento.getId();
                }
            } else {
                // Se a casa estiver vazia de itens
                result[1] = ""; // Descrição vazia
                result[2] = ""; // Tipo vazio
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

        public boolean moveCurrentPlayer(int nrSpaces){
            this.nrSpaces = nrSpaces;

            if (numJogadores == 0) {
                return false;
            }

            if(nrSpaces<1||nrSpaces>6){return false;}
            Player p= allInfoPlayers.get(currentPlayer[atual]);
            if (p.getPrimeiraLinguagem().equals("Assembly") && nrSpaces>2){
                return false;
            }
            if (p.getPrimeiraLinguagem().equals("C") && nrSpaces>3){
                return false;
            }
            if(p.getPosicao()+nrSpaces>tamanhoTabuleiro){p.setPosicao(tamanhoTabuleiro);return true;}
            p.setPosicao(p.getPosicao()+nrSpaces);
            atual = (atual + 1) % numJogadores;
            rondas++;
            return true;
        }

        public boolean gameIsOver(){
            for(Player p: listaPlayers){
                if(p.getPosicao()==tamanhoTabuleiro){
                    vencedor=p.getNome();
                    rondas++;
                    return true;
                }
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
            str.add(vencedor);
            str.add("");
            str.add("RESTANTES");
            str.addAll(restantes());
            return str;
        }

        public ArrayList<String> restantes(){
            listaPlayers.sort((p1, p2) -> Integer.compare(p2.getPosicao(), p1.getPosicao()));
            ArrayList<String> resultado = new ArrayList<>();

            for(Player p : listaPlayers){
                if(p.getPosicao()!=tamanhoTabuleiro){
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

                    sb.append(p.getNome())
                            .append(" : ")
                            .append(p.getFerramentasToString());

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

                    int tipo = (el instanceof Tool) ? 1 : 0;

                    writer.println(pos + ":" + tipo + ":" + el.getId() + ":" + el.getTitle());
                }

                return true;

            } catch (IOException e) {
                return false;
            }
        }


        public void loadGame(File file) {
            //
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

    }

