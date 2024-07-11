
// package RPG;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Player {
    // Random Value
    Random random = new Random();

    // Attributes
    String name;
    public int health;
    public int strength;
    public int attack;
    public int rolled;
    public boolean isDead;

    // Constructor
    public Player(String name, int health, int strength, int attack) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.attack = attack;
        this.rolled = 0;
        this.isDead = false;
    }

    // methods:
    public void rollDie(int opponentRolled) {
        this.rolled = (opponentRolled == 0) ? random.nextInt(6) + 1 : ((opponentRolled <= 1) ? 0 : random.nextInt(opponentRolled - 1) + 1);
    }

    // public void isAttacking() {
    // System.out.println(this.name + " Attacks!");
    // }

    // public void isDefending() {
    // System.out.println(this.name + " Defends!");
    // }

    // public void isWon() {
    // System.out.println(this.name + " Won the Match!");
    // }

    // public void isLost() {
    // System.out.println(this.name + " is Now History!");
    // }

    // public void health() {
    // System.out.print(String.format("%s have %d HP!", this.name, this.health));
    // }

    public void status() {
        System.out.println(String.format("Player Name: %s \n Health: %d \n Strength: %d \n Attack: %d", this.name,
                this.health, this.strength, this.attack));

        if (this.isDead) {
            System.out.println(this.name + " is out of The Arena!");
        }
    }
}

class Arena {
    public boolean matchContinue = true;
    public int currentDamage;
    public String whoWins;

    // Let's assume players are, billy and Cristine
    // Note: Player 0 is Current Rival and Player 1 is opponent Rival, where their
    // positions are changing as per the conditions.
    // public Player rivals[] = new Player[2];
    public ArrayList<Player> rivals = new ArrayList<>(2);

    public Arena(Player player0, Player player1) {
        if (player0.health < player1.health) {
            // rivals[0] = player0;
            // rivals[1] = player1;
            rivals.add(player0);
            rivals.add(player1);
            // matchContinue = true;

        } else {
            // rivals[0] = player1;
            // rivals[1] = player0;
            rivals.add(player1);
            rivals.add(player0);
        }
    }

    public int attack(int rolledDie, int attack) {
        return rolledDie * attack;
    }

    public int defend(int rolledDie, int strength) {
        return rolledDie * strength;
    }

    public void action(int opponentHealth, int strength, int attack) {
        rivals.get(0).rolled = 0;
        rivals.get(1).rolled = 0;
        currentDamage = attack - strength;
        // rivals.get(1).health = opponentHealth - currentDamage;
        rivals.get(1).health = opponentHealth - currentDamage;
        if (rivals.get(1).health < 0) { rivals.get(1).health = 0; }
    }

    public void whiteFlag() {
        rivals.get(0).isDead = rivals.get(0).health == 0;
        rivals.get(1).isDead = rivals.get(1).health == 0;
        whoWins = (rivals.get(0).health == 0) ? rivals.get(1).name : rivals.get(0).name;
        System.out.println(whoWins + " is the Winner!\n\n");
    }

    public void match() {
        while (matchContinue) {
            
            // Attacking
            System.out.println(
                String.format("%s is now Attacking! Current HP is %d", rivals.get(0).name, rivals.get(0).health));
                rivals.get(0).rollDie(rivals.get(1).rolled);
                
                // Attacker Rolling Dice
                System.out.println(
                    String.format("%s rolled the dice and get %d \n", rivals.get(0).name, rivals.get(0).rolled));
                    int me = attack(rivals.get(0).rolled, rivals.get(0).attack);
                    
                    // Defending
                    System.out.println(
                        String.format("%s is now Defending! Current HP is %d", rivals.get(1).name, rivals.get(1).health));
                        rivals.get(1).rollDie(rivals.get(0).rolled);
                        
                        System.out.println(
                            String.format("%s rolled the dice and get %d \n", rivals.get(1).name, rivals.get(1).rolled));
            int opponent = defend(rivals.get(1).rolled, rivals.get(1).strength);

            action(rivals.get(1).health, opponent, me);
            System.out.println(String.format("%s Attacks %d: %s defended %d, took damage of %d and HP is now %d!\n",
            rivals.get(0).name, me, rivals.get(1).name, opponent, currentDamage, rivals.get(1).health));
            
            Collections.swap(rivals, 0, 1);

            if (rivals.get(0).health < 1 || rivals.get(1).health < 1) {
                System.out.println("Match has been Ended!\n");
                whiteFlag();
                rivals.get(0).status();
                System.out.println();
                rivals.get(1).status();
                matchContinue = false;
            }
        }
    }
}

public class MagicalArena {
    public static void main(String[] args) {
        Player Celeste = new Player("Celeste", 50, 5, 10);
        Player Billy = new Player("Billy", 100, 10, 5);

        Arena Battle = new Arena(Billy, Celeste);

        Battle.match();
    }
}
