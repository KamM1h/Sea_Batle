import java.util.ArrayList;

public class Bot extends Player {
    private int[] destoyedShipsOpponent;
    private ArrayList<int[]> shot_plan;

    public Bot() {
        super("Bot");
        destoyedShipsOpponent = new int[]{0, 0, 0, 0, 0, 0};
        shot_plan = new ArrayList<>();
    }

    @Override
    public void placementShips() {
        for (int i = 0; i < 21; i++) {
            if (i == 0) {
                if (!addShip(6)) {
                    i--;
                }
            } else if (i > 0 && i <= 2) {
                if (!addShip(5)) {
                    i--;
                }
            } else if (i > 2 && i <= 5) {
                if (!addShip(4)) {
                    i--;
                }
            } else if (i > 5 && i <= 9) {
                if (!addShip(3)) {
                    i--;
                }
            } else if (i > 9 && i <= 14) {
                if (!addShip(2)) {
                    i--;
                }
            } else {
                if (!addShip(1)) {
                    i--;
                }
            }
        }
        System.out.println("Bot:");
        super.getBattlefield().showBattlefield();
    }

    private boolean addShip(int count_points) {
        int orientation = (int) (Math.random() * 2);
        int start_pointX = (int) (Math.random() * 16) + 1;
        int start_pointY = (int) (Math.random() * 16) + 1;
        if (super.getBattlefield().addShipToBattlefield(count_points, new int[]{start_pointX, start_pointY}, orientation)) {
            super.getShips().add(new Ship(count_points, new int[]{start_pointX, start_pointY}, orientation));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Shot shot() {
        int x, y;
        int [] result;
        Shot shot;
        if(getShot_history().size() > 0 && getShot_history().get(0).getResult() == "Убил") {
            determiningTypeLastKilledShip();
        }
        if(getShot_history().size() > 0 && getShot_history().get(0).getResult() == "Мимо" && getShot_history().get(1).getResult() != "Убил" &&
                (getShot_history().get(2).getResult() != "Убил" && (getShot_history().get(0).getResult() == "Попал" |
                (getShot_history().get(1).getResult() == "Попал" | getShot_history().get(2).getResult() == "Попал")))){

            if(getShot_history().get(0).getResult() == "Попал" && getShot_history().get(1).getResult() == "Попал"){
                int[] coordFrstShot = getShot_history().get(0).getCoordinates();
                int[] coordScdShot = getShot_history().get(0).getCoordinates();
                if(coordFrstShot[0] == coordScdShot[0]){
                    shot = new Shot(new int[]{ coordFrstShot[0], coordFrstShot[1] + 1});
                    getShot_history().add(shot);
                    return shot;
                }
                else{
                    shot = new Shot(new int[]{ coordFrstShot[0] + 1, coordFrstShot[1]});
                    getShot_history().add(shot);
                    return shot;
                }
            }else if (getShot_history().get(0).getResult() == "Мимо" && (getShot_history().get(1).getResult() == "Попал"
                && getShot_history().get(2).getResult() == "Попал")){
                int i = 2;
                while(getShot_history().get(i).getResult() != "Мимо"){
                    i++;
                }
                if (getShot_history().get(i).getCoordinates()[0] == getShot_history().get(i-1).getCoordinates()[0]){
                    shot = new Shot(new int[]{getShot_history().get(i).getCoordinates()[0], getShot_history().get(i).getCoordinates()[1] --});
                    getShot_history().add(shot);
                    return shot;
                }
                else {
                    shot = new Shot(new int[]{getShot_history().get(i).getCoordinates()[0] --, getShot_history().get(i).getCoordinates()[1]});
                    getShot_history().add(shot);
                    return shot;
                }
            } else if (getShot_history().get(0).getResult() == "Попал") {
                shot = new Shot(new int[]{getShot_history().get(0).getCoordinates()[0], getShot_history().get(0).getCoordinates()[1] + 1});
                getShot_history().add(shot);
                return shot;
            } else if (getShot_history().get(0).getResult() == "Мимо" && getShot_history().get(1).getResult() == "Попал") {
                shot = new Shot(new int[]{getShot_history().get(1).getCoordinates()[0] + 1, getShot_history().get(1).getCoordinates()[1]});
                getShot_history().add(shot);
                return shot;
            } else if (getShot_history().get(0).getResult() == "Мимо" && getShot_history().get(1).getResult() == "Мимо"
                    && (getShot_history().get(2).getResult() == "Попал")){
                shot = new Shot(new int[]{getShot_history().get(2).getCoordinates()[0], getShot_history().get(2).getCoordinates()[1] - 1});
                getShot_history().add(shot);
                return shot;
            } else if (getShot_history().get(0).getResult() == "Мимо" && getShot_history().get(1).getResult() == "Мимо"
                    && (getShot_history().get(2).getResult() == "Мимо"
                    && getShot_history().get(3).getResult() == "Попал")){
                shot = new Shot(new int[]{getShot_history().get(3).getCoordinates()[0] - 1, getShot_history().get(3).getCoordinates()[1]});
                getShot_history().add(shot);
                return shot;
            }
        } else if(shot_plan.isEmpty()) {
            if (getShot_history().isEmpty()) {
                x = (int) (Math.random() * 16) + 1;
                y = (int) (Math.random() * 16) + 1;
                shot = new Shot(new int[]{x, y});
                getShot_history().add(shot);
                return shot;
            } else if (getShot_history().get(0).getResult() == "Убил" | getShot_history().get(0).getResult() == "Мимо") {
                if(destoyedShipsOpponent[5] < 1){
                    if(findShipHorizontal(6)){
                        result = shot_plan.get(0);
                        shot_plan.remove(0);
                        shot = new Shot(result);
                        getShot_history().add(shot);
                        return shot;
                    }
                }
                else if(destoyedShipsOpponent[4] < 2){
                    if(findShipHorizontal(5)){
                        result = shot_plan.get(0);
                        shot_plan.remove(0);
                        shot = new Shot(result);
                        getShot_history().add(shot);
                        return shot;
                    }
                }
                else if(destoyedShipsOpponent[3] < 3){
                    if(findShipHorizontal(4)){
                        result = shot_plan.get(0);
                        shot_plan.remove(0);
                        shot = new Shot(result);
                        getShot_history().add(shot);
                        return shot;
                    }
                }
                else if(destoyedShipsOpponent[3] < 4){
                    if(findShipHorizontal(4)){
                        result = shot_plan.get(0);
                        shot_plan.remove(0);
                        shot = new Shot(result);
                        getShot_history().add(shot);
                        return shot;
                    }
                }
            }
        } else{
            result = shot_plan.get(0);
            shot_plan.remove(0);
            shot = new Shot(result);
            getShot_history().add(shot);
            return shot;
        }
        return null;
    }

    private boolean findShipHorizontal(int ship_type) {
        int count_free_fields = 0;
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (getOpponent_batlfield().getBattlefieldMatrix()[i][j] == "| ") {
                    count_free_fields++;
                } else if (count_free_fields >= ship_type) {
                    if (ship_type != 1) {
                        for (int k = 1; k < count_free_fields + 1; k += 2) {
                            shot_plan.add(0, new int[]{i, j - k});
                            return true;
                        }
                    } else {
                        for (int k = 1; k < count_free_fields + 1; k++) {
                            shot_plan.add(0, new int[]{i, j - k});
                            return true;
                        }
                    }
                    count_free_fields = 0;
                } else {
                    count_free_fields = 0;
                }
            }
        }
        return findShipVertical(ship_type);
    }

    private boolean findShipVertical(int ship_type) {
        int count_free_fields = 0;
        for (int j = 0; j < 17; j++) {
            for (int i = 0; i < 17; i++) {
                if (getOpponent_batlfield().getBattlefieldMatrix()[i][j] == "| ") {
                    count_free_fields++;
                } else if (count_free_fields >= ship_type) {
                    if (ship_type != 1) {
                        for (int k = 1; k < count_free_fields + 1; k += 2) {
                            shot_plan.add(0, new int[]{i, j - k});
                            return true;
                        }
                    } else {
                        for (int k = 1; k < count_free_fields + 1; k++) {
                            shot_plan.add(0, new int[]{i, j - k});
                            return true;
                        }
                    }
                    count_free_fields = 0;
                } else {
                    count_free_fields = 0;
                }
            }
        }
        return false;
    }

    private void determiningTypeLastKilledShip(){
        int count_points = 1;
        int i = 1;
        while (getShot_history().get(i).getResult() != "Мимо" | getShot_history().get(i).getResult() != "Убил"){
            count_points++;
            i++;
        }
        switch (count_points){
            case 1:
                destoyedShipsOpponent[0]++;
                break;
            case 2:
                destoyedShipsOpponent[1]++;
                break;
            case 3:
                destoyedShipsOpponent[2]++;
                break;
            case 4:
                destoyedShipsOpponent[3]++;
                break;
            case 5:
                destoyedShipsOpponent[4]++;
                break;
            case 6:
                destoyedShipsOpponent[5]++;
                break;
            default:
                System.out.println("Bot error! determiningTypeLastKilledShip");
                break;
        }
    }

    @Override
    public String checkOpponentShot(int[] coordinates){
        if(getBattlefield().getBattlefieldMatrix()[coordinates[0]][coordinates[1]].equals("|X")){
            for(int i = 0; i < getShips().size(); i++){
                for (int j = 0; j < getShips().get(i).getCoordinates().length; j++){
                    System.out.println(getShips().get(i).getCoordinates()[j]);
                    System.out.println(coordinates);
                    if (getShips().get(i).getCoordinates()[j] == coordinates){
                        if(getShips().get(i).isIs_alive()){
                            System.out.println(1);
                            getShips().get(i).getAffected_points()[j] = coordinates;
                            for (int k = 0; k < getShips().get(i).getAffected_points().length; k++){
                                if(getShips().get(i).getAffected_points()[0] == null){
                                    return "damaged";
                                }
                            }
                            getShips().get(i).dead();
                            dieShip(super.getShips().get(i));
                            return "dead";
                        }
                        else{
                            return "miss";
                        }
                    }
                }
            }
        } else if(super.getBattlefield().getBattlefieldMatrix()[coordinates[0]][coordinates[1]].equals("|*")) {
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
                    getBattlefield().getBattlefieldMatrix()[x - 1][y + i] = "|*";
                }
                //bottom side
                if(x != 16) {
                    for (int i = -1; i <= ship.getCount_points(); i++) {
                        getBattlefield().getBattlefieldMatrix()[x + 1][y + i] = "|*";
                    }
                }
                getBattlefield().getBattlefieldMatrix()[x][y - 1] = "|*";
                getBattlefield().getBattlefieldMatrix()[x][y + ship.getCount_points()] = "|*";
                break;
            case 1:
                //left side
                for (int i = -1; i < ship.getCount_points(); i++) {
                    getBattlefield().getBattlefieldMatrix()[x + i][y - 1] = "|*";
                }
                //bottom side
                if(x != 16) {
                    for (int i = -1; i <= ship.getCount_points(); i++) {
                        getBattlefield().getBattlefieldMatrix()[x + i][y + 1] = "|*";
                    }
                }
                getBattlefield().getBattlefieldMatrix()[x - 1][y] = "|*";
                getBattlefield().getBattlefieldMatrix()[x + ship.getCount_points()][y] = "|*";
                break;
        }
    }
}
