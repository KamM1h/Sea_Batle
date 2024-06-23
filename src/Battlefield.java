import java.util.Scanner;

public class Battlefield {
    private String battlefieldMatrix[][];

    public Battlefield(){
        battlefieldMatrix =new String[17][17];
        battlefieldMatrix[0][0] = "  ";
        for(int i = 1; i < 17; i++){
            battlefieldMatrix[i][0] = (i < 10) ? String.valueOf(i) + " " : String.valueOf(i);
            battlefieldMatrix[0][i] =  " " + ((char) (64+i));
        }

        for(int i = 1; i < 17; i++){
            for (int j = 1; j < 17; j++){
                battlefieldMatrix[i][j] = (battlefieldMatrix[i][j] == null) ? "| " : battlefieldMatrix[i][j];
                //battlefieldMatrix[i][j] = (j == 16) ? battlefieldMatrix[i][j] + "|" : battlefieldMatrix[i][j];
            }
        }
    }


    public boolean addShipToBattlefield(int countPoints, int[] coordinates, int orientation){
        int x = coordinates[0];
        int y = coordinates[1];
        switch (orientation){
            case 0:
                if(((y + countPoints - 1) < 17) && (x < 17)) {
                    //top side
                    for (int i = -1; i < countPoints; i++) {
                        if (battlefieldMatrix[x - 1][y + i].equals( "|X")) {
                            return false;
                        }
                    }
                    //bottom side
                    if(x != 16) {
                        for (int i = -1; i < countPoints; i++) {
                            if (battlefieldMatrix[x + 1][y + i].equals("|X")) {
                                return false;
                            }
                        }
                    }
                    if (battlefieldMatrix[x][y - 1].equals( "|X")) {
                        return false;
                    } else if (battlefieldMatrix[x][y + countPoints - 1].equals( "|X")) {
                        return false;
                    } else {
                        for (int i = 0; i < countPoints; i++) {
                            battlefieldMatrix[x][y + i] = "|X";
                        }
                        return true;
                    }
                }
            case 1:
                if((x + countPoints - 1) < 17 && y < 17) {
                    //left side
                    for (int i = -1; i < countPoints; i++) {
                        if (battlefieldMatrix[x + i][y - 1].equals( "|X")) {
                            return false;
                        }
                    }
                    //right side
                    if (y != 16) {
                        for (int i = -1; i < countPoints; i++) {
                            if (battlefieldMatrix[x + i][y + 1].equals("|X")) {
                                return false;
                            }
                        }
                    }
                    if (battlefieldMatrix[x - 1][y].equals( "|X")) {
                        return false;
                    } else if (battlefieldMatrix[x + countPoints - 1][y].equals( "|X")) {
                        return false;
                    } else {
                        for (int i = 0; i < countPoints; i++) {
                            battlefieldMatrix[x + i][y] = "|X";
                        }
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    public void showBattlefield(){
        for(int i = 0; i < 17; i++){
            for(int j = 0; j < 17; j++){
                System.out.print(battlefieldMatrix[i][j]);
                if (j == 16){System.out.print("\n");}
            }
        }
    }

    public void addPointToBattlefield(int[] coordinates, String point){
        if(coordinates[0] > 0 && coordinates[1] > 0) {
            battlefieldMatrix[coordinates[0]][coordinates[1]] = point;
        }
    }

    public String[][] getBattlefieldMatrix() {
        return battlefieldMatrix;
    }
}
