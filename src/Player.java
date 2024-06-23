import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private Battlefield battlefield, opponent_batlfield;
    private ArrayList<Ship> ships;
    private ArrayList<Shot> shot_history;

    public Player(String name){
        this.name = name;
        battlefield = new Battlefield();
        opponent_batlfield = new Battlefield();
        ships = new ArrayList<>();
        shot_history = new ArrayList<>();
    }

    public void placementShips(){
        battlefield.showBattlefield();
        int countOnePointShip = 6;
        int countTwoPointShip = 5;
        int countThreePointShip = 4;
        int countFourPointShip = 3;
        int countFivePointShip = 2;
        int countSixPointShip = 1;
        int countAllShip = countOnePointShip +countTwoPointShip + countThreePointShip + countFourPointShip
                + countFivePointShip + countSixPointShip;
        String messCountShipError = "Превышено максимальное количесвто кораблей этого типа";

        int i = 0;
        while(i < countAllShip) {
            String shipCoordinates;
            System.out.println("Выберите корабль:\n" +
                    "1. 1 клетка (Осталось " + countOnePointShip + ")" + "\n" +
                    "2. 2 клетки (Осталось " + countTwoPointShip + ")" + "\n" +
                    "3. 3 клетки (Осталось " + countThreePointShip + ")" + "\n" +
                    "4. 4 клетки (Осталось " + countFourPointShip + ")" + "\n" +
                    "5. 5 клеток (Осталось " + countFivePointShip + ")" + "\n" +
                    "6. 6 клеток (Осталось " + countSixPointShip + ")"+ "\n" +
                    "7. Автоматическое заполнение");

            Scanner inp = new Scanner(System.in);
            switch(inp.nextLine()){
                case "1":
                    if(countOnePointShip >0) {
                        if(addShip(1)){
                            countOnePointShip --;
                            i++;
                        }
                    }else{
                        System.out.println(messCountShipError);
                    }
                    break;

                case "2":
                    if(countTwoPointShip > 0) {
                        if(addShip(2)){
                            countTwoPointShip --;
                            i++;
                        }
                    }else{
                        System.out.println(messCountShipError);
                    }
                    break;
                case "3":
                    if(countThreePointShip > 0) {
                        if(addShip(3)){
                            countThreePointShip --;
                            i++;
                        }
                    }else{
                        System.out.println(messCountShipError);
                    }
                    break;
                case "4":
                    if(countFourPointShip > 0) {
                        if(addShip(4)){
                            countFourPointShip --;
                            i++;
                        }
                    }else{
                        System.out.println(messCountShipError);
                    }
                    break;
                case "5":
                    if(countFivePointShip > 0) {
                        if(addShip(5)){
                            countFivePointShip --;
                            i++;
                        }
                    }else{
                        System.out.println(messCountShipError);
                    }
                    break;
                case "6":
                    if(countSixPointShip > 0) {
                        if(addShip(6)){
                            countSixPointShip --;
                            i++;
                        }
                    }else{
                        System.out.println(messCountShipError);
                    }
                    break;
                case "7":
                    autoPlacementShips();
                    i = countAllShip;
                    break;
                default:
                    System.out.println("Такой команды не существует");

            }
        }
    }

    private void autoPlacementShips(){
        for (int i = 0; i < 21; i++) {
            if (i == 0) {
                if (!autoAdd(6)) {
                    i--;
                }
            } else if (i > 0 && i <= 2) {
                if (!autoAdd(5)) {
                    i--;
                }
            } else if (i > 2 && i <= 5) {
                if (!autoAdd(4)) {
                    i--;
                }
            } else if (i > 5 && i <= 9) {
                if (!autoAdd(3)) {
                    i--;
                }
            } else if (i > 9 && i <= 14) {
                if (!autoAdd(2)) {
                    i--;
                }
            } else {
                if (!autoAdd(1)) {
                    i--;
                }
            }
        }
        battlefield.showBattlefield();
    }
    private boolean autoAdd(int count_points){
        int orientation = (int) (Math.random() * 2);
        int start_pointX = (int) (Math.random() * 16) + 1;
        int start_pointY = (int) (Math.random() * 16) + 1;
        if (battlefield.addShipToBattlefield(count_points, new int[]{start_pointX, start_pointY}, orientation)) {
            ships.add(new Ship(count_points, new int[]{start_pointX, start_pointY}, orientation));
            return true;
        } else {
            return false;
        }
    }


    //Возвращает true если корабль добавлен и false, если нет
    private boolean addShip(int count_points){
        int orientation = chooseShipOrientation();
        String messStartPoint = "Укажите точку начала корабля в формате '1A'" +
                "(Точкой начала корабля является крайняя левая или верхняя граница, взависимости от расположения)";
        Scanner inp = new Scanner(System.in);
        System.out.println(messStartPoint);
        String shipCoordinates = inp.nextLine();
        if(shipCoordinates.length() <= 3) {
            int[] coordinates = checkCoordinates(shipCoordinates);
            if (battlefield.addShipToBattlefield(count_points, coordinates, orientation)) {
                ships.add(new Ship(count_points, coordinates, orientation));
                return true;
            } else {
                System.out.println("Корабль не может располагаться в этом метсе");
                return false;
            }
        } else {
            System.out.println("Неверно введена координата");
            return false;
        }
    }


    private int[] checkCoordinates(String coordinates){
        char[] coordArr = coordinates.toUpperCase().toCharArray();
        int x, y;
        if (coordinates.length() == 2){
            x = Character.getNumericValue(coordArr[0]);
            y = ((int) coordArr[1]) - 64;
        } else{
            x = Integer.parseInt("" + coordArr[0] + coordArr[1]);
            y = (int) coordArr[2] - 64;
        }
        return new int[]{x, y};
    }

    private int chooseShipOrientation(){
        Scanner inp = new Scanner(System.in);
        System.out.println("Укажите расположения корабля:\n" +
                "1. По горизонтали;\n" +
                "2. По вертикали.");
        switch(inp.nextLine()){
            case "1":
                return 0;
            case "2":
                return 1;
            default:
                System.out.println("Такой команды не существует!");
                return 2;
        }
    }

    public Shot shot(){
        Scanner inp = new Scanner(System.in);
        System.out.println("Укажите точку, куда вы хотите выстрелить в формате '1A'");
        String coordinates = inp.nextLine();
        if(coordinates.length() <= 3){
            Shot shot = new Shot(checkCoordinates(coordinates));
            shot_history.add(shot);
            return shot;
        }
        else{
            System.out.println("Такой координаты не существует");
            return null;
        }
    }

    public String checkOpponentShot(int[] coordinates){
        if(battlefield.getBattlefieldMatrix()[coordinates[0]][coordinates[1]].equals("|X")){
            for(int i = 0; i < ships.size(); i++){
                for (int j = 0; j < ships.get(i).getCoordinates().length; j++){
                    if (ships.get(i).getCoordinates()[j] == coordinates){
                        if(ships.get(i).isIs_alive()){
                            ships.get(i).getAffected_points()[j] = coordinates;
                            for (int k = 0; k < ships.get(i).getAffected_points().length; k++){
                                if(ships.get(i).getAffected_points()[0] == null){
                                    return "damaged";
                                }
                            }
                            ships.get(i).dead();
                            dieShip(ships.get(i));
                            return "dead";
                        }
                        else{
                            return "miss";
                        }
                    }
                }
            }
        } else if(battlefield.getBattlefieldMatrix()[coordinates[0]][coordinates[1]].equals("|*")) {
            return "replay";
        }else{
            return "miss";
        }
        return "miss";
    }

    private void dieShip(Ship ship){
        int x = ship.getCoordinates()[0][0];
        int y = ship.getCoordinates()[0][1];
        switch (ship.getOrientation()){
            case 0:
                //top side
                for (int i = -1; i < ship.getCount_points(); i++) {
                    battlefield.getBattlefieldMatrix()[x - 1][y + i] = "|*";
                }
                //bottom side
                if(x != 16) {
                    for (int i = -1; i <= ship.getCount_points(); i++) {
                        battlefield.getBattlefieldMatrix()[x + 1][y + i] = "|*";
                    }
                }
                battlefield.getBattlefieldMatrix()[x][y - 1] = "|*";
                battlefield.getBattlefieldMatrix()[x][y + ship.getCount_points()] = "|*";
                break;
            case 1:
                //left side
                for (int i = -1; i < ship.getCount_points(); i++) {
                    battlefield.getBattlefieldMatrix()[x + i][y - 1] = "|*";
                }
                //bottom side
                if(x != 16) {
                    for (int i = -1; i <= ship.getCount_points(); i++) {
                        battlefield.getBattlefieldMatrix()[x + i][y + 1] = "|*";
                    }
                }
                battlefield.getBattlefieldMatrix()[x - 1][y] = "|*";
                battlefield.getBattlefieldMatrix()[x + ship.getCount_points()][y] = "|*";
                break;
        }
    }

    public void changeOpponentBattlefield(){
        switch (shot_history.get(0).getResult()){
            case "Попал":
                opponent_batlfield.addPointToBattlefield(shot_history.get(0).getCoordinates(), "|X");
                break;
            case "Мимо":
                opponent_batlfield.addPointToBattlefield(shot_history.get(0).getCoordinates(), "|*");
                break;
            case "Убил":
                ArrayList<int[]> coordinates = new ArrayList<>(1);
                coordinates.add(shot_history.get(0).getCoordinates());
                int o = 1;
                while (shot_history.get(o).getResult() != "Мимо" | shot_history.get(o).getResult() != "Убил"){
                    coordinates.add(shot_history.get(o).getCoordinates());
                    o++;
                }
                ArrayList<int[]> sortedCoordinates = sortList(coordinates);
                for (int i = 0; i < sortedCoordinates.size(); i++){
                    if(sortedCoordinates.get(i)[0] == sortedCoordinates.get(i+1)[0]) {
                        if (i == 0) {
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] - 1},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] - 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] + 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                        } else if (i == sortedCoordinates.size() - 1) {
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] + 1},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] - 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] + 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                        } else {
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] - 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] + 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                        }
                    } else {
                        if (i == 0) {
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] - 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] - 1},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] + 1},
                                    "|*");
                        } else if (i == sortedCoordinates.size() - 1) {
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0] + 1,
                                            sortedCoordinates.get(i)[1]},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] - 1},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] + 1},
                                    "|*");
                        } else {
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] - 1},
                                    "|*");
                            opponent_batlfield.addPointToBattlefield(
                                    new int[]{sortedCoordinates.get(i)[0],
                                            sortedCoordinates.get(i)[1] + 1},
                                    "|*");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private ArrayList<int[]> sortList(ArrayList<int[]> list){
        int count_changes = 1;
        int i = list.size() - 1;
        int j;
        int[] arr;
        while (i > 0){
            j = i;
            while(list.get(j)[1] > list.get(j - 1)[1]){
                list.add(j - 1, list.get(j));
                list.remove(j + 1);
                j--;
            }
            i--;
        }
        return list;
    }

    public int countLiveShips(){
        int result = 0;
        for (int i = 0; i < ships.size(); i++){
            if(ships.get(i).isIs_alive()){
                result++;
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public Battlefield getOpponent_batlfield() {
        return opponent_batlfield;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public ArrayList<Shot> getShot_history() {
        return shot_history;
    }
}
