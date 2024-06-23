import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        System.out.println("Введите имя игрока");
        Player player = new Player(inp.nextLine());

        System.out.println("Выберите режим игры\n" +
                            "1. Одиночная игра;\n" +
                            "2. Игра с напарником.");
        switch(inp.nextInt()){
            case 1:
                Singleplayer singleGame = new Singleplayer(player);
                singleGame.startGame();
                break;
            case 2:
                Multiplayer multiGame = new Multiplayer();
                break;
            default:
                System.out.println("Неверный номер команды");
        }
    }
}