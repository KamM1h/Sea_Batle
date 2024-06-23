public class Ship {
    private int count_points;
    private boolean is_alive;
    private int [][] coordinates;
    private int orientation; //0-horizotal, 1 - vertical
    private int count_live_points;
    private int [][] affected_points;

    public Ship(int count_points, int[]coordinates, int orientation){
        this.count_points = count_points;
        count_live_points = count_points;
        is_alive = true;
        this.orientation = orientation;
        affected_points = new int [count_points][2];
        this.coordinates = new int[count_points][2];
        switch (orientation){
            case 0:
                for(int i = 0; i < count_points; i++){
                    this.coordinates[i][0] = coordinates[0];
                    this.coordinates[i][1] = coordinates[1] + i;
                }
            case 1:
                for(int i = 0; i < count_points; i++){
                    this.coordinates[i][0] = coordinates[0] + i;
                    this.coordinates[i][1] = coordinates[1];
                }
            default:
                break;
        }
    }

    public void setAffected_points(int[][] affected_points) {
        this.affected_points = affected_points;
    }

    public void dead(){
        is_alive = false;
    }

    public int getCount_live_points() {
        return count_live_points;
    }

    public int getCount_points() {
        return count_points;
    }

    public int getOrientation() {
        return orientation;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public int[][] getAffected_points() {
        return affected_points;
    }

    public boolean isIs_alive() {
        return is_alive;
    }
}
