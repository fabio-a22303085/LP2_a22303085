    package pt.ulusofona.lp2.greatprogrammingjourney;

    import javax.swing.*;
    import java.io.File;
    import java.io.FileNotFoundException;
    import java.util.*;

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

            int numJogadores = playerInfo.length;

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

                    Player p = new Player(id, 0, nome, corFinal, linguagens);

                    listaPlayers.add(p);
                    allInfoPlayers.put(id, p);
                    idJogadores.add(id);
                    tempIds.add(id);

                } catch (NumberFormatException e) {
                    return false;
                }
            }

            // DEFINIR QUEM COMEÇA
            Collections.sort(tempIds);
            for (int i = 0; i < numJogadores; i++) {
                currentPlayer[i] = tempIds.get(i);
            }

            if (abyssesAndTools != null) {
                int[] countAbyss = new int[10];
                int[] countTools = new int[6];

                String[] nomesAbyss = {"Erro de Sintaxe", "Erro de Lógica", "Exception", "FileNotFoundException",
                        "Crash", "Código Duplicado", "Efeitos Secundários",
                        "Blue Screen of Death", "Ciclo Infinito", "Segmentation Fault"};
                String[] nomesTools = {"Herança", "Programação Funcional", "Testes Unitários",
                        "Tratamento de Excepções", "IDE", "Ajuda Do Professor"};

                for (String[] dados : abyssesAndTools) {
                    if (dados.length < 3) return false;

                    try {
                        String tipoStr = dados[0];
                        int idItem = Integer.parseInt(dados[1]);
                        int posicao = Integer.parseInt(dados[2]);

                        if (posicao < 1 || posicao > worldSize){return false;}

                        if (tabuleiro.containsKey(posicao)){return false;}

                        if (tipoStr.equals("0")) { // ABISMO
                            if (idItem < 0 || idItem > 9) return false;
                        } else if (tipoStr.equals("1")) { // FERRAMENTA
                            if (idItem < 0 || idItem > 5) return false;
                        } else {
                            return false;
                        }

                        // Criação do Objeto
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
                        }
                        tabuleiro.put(posicao, elemento);

                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }

            return true;
        }


        public String[] getSlotInfo(int pos){
            // meter array dos abyss e das tools
            String[] result = new String[1];
            if(pos<=0 || pos>tamanhoTabuleiro-1){return null;}

            int cont=0;
            ArrayList<Integer> lista = new ArrayList<>();
            for(Player p : listaPlayers){
                if(p.getPosicao()==pos){lista.add(p.getId());}
            }

            if (lista.isEmpty()) {
                result[0]="";
                return result;
            }
            StringBuilder strB = new StringBuilder();
            for (Integer num: lista) {
                cont++;
                strB.append(num.toString());
                if (cont != lista.size()) {
                    strB.append(",");
                }
            }
            result[0]= strB.toString();
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

            if(nrSpaces<1||nrSpaces>6){return false;}
            Player p= allInfoPlayers.get(currentPlayer[atual]);
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

        public String getProgrammersInfo(){
            return "";
        }

        public boolean saveGame(File file){
            return true;
        }

        public void loadGame(File file) {
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

