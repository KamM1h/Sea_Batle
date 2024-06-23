public class Singleplayer {
    private Player player;
    private Bot bot;


    public Singleplayer(Player player){
        this.player = player;
        bot = new Bot();
    }

    public void startGame(){
        player.placementShips();
        bot.placementShips();

        new Battle(player, bot);
    }
}
