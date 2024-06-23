public class Shot {
    private int[] coordinates;
    private String result; //попал, промах, убил

    public Shot(int[] coordinates){
        this.coordinates = coordinates;
        result = null;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public String getResult() {
        return result;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
