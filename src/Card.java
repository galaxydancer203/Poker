import java.util.ArrayList;
import java.util.List;

public class Card {

    private String rank;
    private String suit;
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }
    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }
    @Override
    public String toString() {
        return rank + suit;
    }

    public static int getOrderedRank(String rank) {
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException e) {
            switch (rank) {
                case "T": return 11;    // for 10s, Jacks, Queens, Kings, and Aces, we need to apply a numeric value to simplify the sorting of hands and books.
                case "J": return 12;
                case "Q": return 13;
                case "K": return 14;
                case "A": return 15;
            }
        }

        return -1;
    }

    public static int getOrderedSuit(String suit) {
        switch (suit) {
            case "C":
                return 1;     // we give each suit a numeric
            case "D":
                return 2;     // value to simplify the sorting
            case "H":
                return 3;     // of hands.
            case "S":
                return 4;
        }
        return -1;
    }
    public Card getCardByRank(String rank) {
        if (rank == null) return null;
        if (rank.length() != 1) return null;

//         return a card based on a rank, returning null if the provided
//         rank is invalid or unrecognized

        if (rank.equals("T") ||
                rank.equals("J") ||
                rank.equals("Q") ||
                rank.equals("K") ||
                rank.equals("A"))
        {
            return new Card(rank, null);
        } else {
            try {
                int number = Integer.parseInt(rank);

                if (number > 1 && number < 10) {
                    return new Card(rank, null);
                }
            } catch (NumberFormatException e) {
                return null;    // this happens if rank isn't a number
            }
        }
        return null;
    }
}