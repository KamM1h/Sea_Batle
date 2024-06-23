public class Battle {
    private Player player1;
    private Player player2;

    public Battle(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        battle();
    }

    private void battle(){
        boolean end_game = false;
        int turn = 1;
        while(!end_game){
            while (turn == 1){
                player1.getOpponent_batlfield().showBattlefield();
                player1.getBattlefield().showBattlefield();
                if(!shotCheck(player1.shot(), player2)){
                    turn = 2;
                }
                player1.changeOpponentBattlefield();
                if(player2.countLiveShips() == 0) end_game = true;
            }
            while (turn == 2){
                player1.getOpponent_batlfield().showBattlefield();
                player1.getBattlefield().showBattlefield();
                if(!shotCheck(player2.shot(), player1)){
                    turn = 1;
                }
                if(player1.countLiveShips() == 0) end_game = true;
            }
        }
    }

    private boolean shotCheck(Shot shot, Player opponent){
        switch(opponent.checkOpponentShot(shot.getCoordinates())){
            case "damaged":
                shot.setResult("попал");
                System.out.println("damaged");
                return true;
            case "dead":
                shot.setResult("убил");
                System.out.println("dead");
                return true;
            case "miss":
                shot.setResult("мимо");
                System.out.println("miss");
                return false;
            default:
                System.out.println("replay");
                return true;
        }
    }
}
