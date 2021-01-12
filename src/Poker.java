import java.util.*;

public class Poker {

    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

    private Hand player;
    private List<Card> deck;
    private final Scanner in;
    private int chips;
    private int ante;
    private boolean checkb;
    private int check;
    private boolean endgame;
    private int numCards;
    private int card1;
    private int card2;
    private int card3;

    public Poker() {
        this.player = new Hand();
        chips = 0;
        this.in = new Scanner(System.in);
        checkb = false;
        endgame = false;
        numCards = -1;
        card1 = 0;
        card2 = 0;
        card3 = 0;
    }

    public void play() {
        do {
            this.player = new Hand();
            card1 = 0;
            card2 = 0;
            card3 = 0;
            numCards = -1;
            endgame = false;
            String temp;
            if (chips == 0) {
                do {
                    int i = 0;
                    System.out.print("\nHow many chips would you like to buy in for: ");
                    temp = in.nextLine();

                    for (char c : temp.toCharArray()) {
                        i++;
                        if (!Character.isDigit(c)) {
                            System.out.println("\nPlease enter a valid integer!");
                            break;
                        }
                    }
                    if (i == temp.toCharArray().length) {
                        try {
                            chips = Integer.parseInt(temp);
                            if(chips > 0) {
                                checkb = true;
                            } else {
                                System.out.println("\nNumber needs to be greater than 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nInput too large or not a valid input.");
                        }
                    }
                } while (checkb = false);
            }
            checkb = false;
            do {
                System.out.print("\nCurrent chips: " + chips);
                System.out.print("\nAnte: ");
                temp = in.next() + in.nextLine();
                try {
                    check = Integer.parseInt(temp);
                    if (check < 1 || check > 25) {
                        System.out.println("Ante must be between 1 and 25 chips.");
                    } else if (check > chips) {
                        System.out.println("Ante must be between 1 and " + chips);
                    } else {
                        ante = check;
                        checkb = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            } while (checkb == false);
            checkb = false;
            shuffleAndDeal();
            showHand();
            do {
                System.out.println("\nChoose the number of cards you would like to trade out. (0, 1, 2, or 3 cards. Must be an integer.)");
                System.out.print("Number of cards: ");
                try {
                    numCards = Integer.parseInt(in.next());
                    if (numCards < 0 || numCards > 3) { System.out.print("\nInput must be between 0 and 3(inclusive)."); }
                } catch (NumberFormatException e) {
                    System.out.print("\nInput too large or not a valid input.");
                }
            } while (numCards < 0 || numCards > 3);

            discardCards();
            player.sortHand();
            showHand();

            checkWin();
        }while (endgame == false);
        endgame = false;
        closeGame();
    }

    public void closeGame() {
        in.close();
    }

    public void checkWin() {
        if (player.rFlushC()) {
            System.out.println("\nYou had a royal flush! You got 100x your bet back.");
            ante *= 100;
            chips += ante;
        } else if (player.straightFC()) {
            System.out.println("\nYou had a straight flush! You got 50x your bet back.");
            ante *= 50;
            chips += ante;
        } else if (player.foakC()) {
            System.out.println("\nYou had a four-of-a-kind! You got 25x your bet back.");
            ante *= 25;
            chips += ante;
        } else if (player.houseC()) {
            System.out.println("\nYou had a full house! You got 15x your bet back.");
            ante *= 15;
            chips += ante;
        } else if (player.flushC()) {
            System.out.println("\nYou had a flush! You got 10x your bet back.");
            ante *= 10;
            chips += ante;
        } else if (player.straightC()) {
            System.out.println("\nYou had a straight! You got 5x your bet back.");
            ante *= 5;
            chips += ante;
        } else if (player.toakC()) {
            System.out.println("\nYou had a three-of-a-kind! You got 3x your bet back.");
            ante *= 3;
            chips += ante;
        } else if (player.tPairC()) {
            System.out.println("\nYou had a two pairs! You got 2x your bet back.");
            ante *= 2;
            chips += ante;
        } else if (player.pairC()) {
            System.out.println("\nYou had a pair! You got your bet back.");
        } else {
            System.out.println("\nYou lost! The house keeps your bet.");
            chips -= ante;
        }

        String answer = "";
        do {
            System.out.println("Would you like the play again?");
            System.out.print("(Y)es or (N)o: ");
            answer = in.next();
            if (!answer.trim().toUpperCase().equals("Y") && !answer.trim().toUpperCase().equals("N")) {
                System.out.println("\nPlease enter a 'Y' for yes or an 'N' for no.");
            }
        } while (!answer.trim().toUpperCase().equals("Y") && !answer.trim().toUpperCase().equals("N"));
        if (answer.trim().toUpperCase().equals("N")) {
            endgame = true;
        }
    }

    public void showHand() {
        System.out.println("Your hand: " + player.getHand().toString().replaceAll("\\[", "").replaceAll("\\]",""));
    }

    private void shuffleAndDeal() {
        if (deck == null) {
            initializeDeck();
        }
        Collections.shuffle(deck);

        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));
        }

        player.sortHand();
    }

    private void discardCards() {
        boolean check = false;
        if (numCards > 0) {
            System.out.println("\nPlease enter the cards you wish to trade(1 is first card, 2 is second card, etc.).");
            do {
                try {
                    System.out.print("Card 1: ");
                    card1 = Integer.parseInt(in.next());
                    if (card1 < 1 || card1 > 5) {
                        System.out.println("You must pick a card between 1 and 5.");
                    } else {
                        check = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input is not a valid input.");
                }
            } while (check == false);
            check = false;
            if (numCards > 1) {
                do {
                    try {
                        System.out.print("Card 2: ");
                        card2 = Integer.parseInt(in.next());
                        if (card2 < 1 || card2 > 5) {
                            System.out.println("You must pick a card between 1 and 5.");
                        } else if (card2 == card1) {
                            System.out.println("That card has already been selected.");
                        } else {
                            check = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input is not a valid input.");
                    }
                } while (check == false);
                check = false;
                if (numCards > 2) {
                    do {
                        try {
                            System.out.print("Card 3: ");
                            card3 = Integer.parseInt(in.next());
                            if (card3 < 1 || card3 > 5) {
                                System.out.println("You must pick a card between 1 and 5.");
                            } else if (card3 == card1 || card3 == card2) {
                                System.out.println("That card has already been selected");
                            } else {
                                check = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input is not a valid input.");
                        }
                    } while (check == false);
                    check = false;
                    sortCards();
                    player.discardCard(player.getCards().get(card1-1));
                    player.discardCard(player.getCards().get(card2-2));
                    player.discardCard(player.getCards().get(card3-3));
                    player.takeCard(deck.remove(0));
                    player.takeCard(deck.remove(0));
                    player.takeCard(deck.remove(0));
                } else {
                    sortCards();
                    player.discardCard(player.getCards().get(card1-1));
                    player.discardCard(player.getCards().get(card2-2));
                    player.takeCard(deck.remove(0));
                    player.takeCard(deck.remove(0));
                }
            } else {
                player.discardCard(player.getCards().get(card1-1));
                player.takeCard(deck.remove(0));
            }
        }
    }
    private void sortCards() {
        for (int i = 0; i < 3; i++) {
            if (card1 > card2) {
                int temp = card1;
                card1 = card2;
                card2 = temp;
            }
            if (card2 > card3 && card3 != 0) {
                int temp = card2;
                card2 = card3;
                card3 = temp;
            }
        }
    }
    private void initializeDeck() {
        deck = new ArrayList<>(52);

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    public static void main(String[] args) { new Poker().play(); }
}