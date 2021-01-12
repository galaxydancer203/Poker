import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private ArrayList<Card> cards;

    public Hand() { cards = new ArrayList<>(); }

    public List<String> getHand() {
        List<Card> temp = cards;
        List<String> returns = new ArrayList<>();
        for(Card i : temp) {
            returns.add(i.toString());
        }
        return returns;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public void discardCard(Card card) {
        cards.remove(card);
    }

    public boolean rFlushC() {
        String suits = "TJQKA";
        ArrayList<Card> temp = cards;

        String cSuit = temp.get(0).getSuit();

        for(Card i : temp) {
            if (suits.contains(i.getRank()) && i.getSuit().equals(cSuit)) {
                suits.replace(i.getRank(), "");
            }
        }

        if(suits.equals("")) {
            return true;
        }

        return false;
    }

    public boolean straightFC() {
        int[] temp = new int[5];
        int count = 0;
        String suit = cards.get(0).getSuit();
        boolean suitC = true;

        for(Card i : cards) {
            try {
                int number = Integer.parseInt(i.getRank());

                temp[count] = number;
            } catch (NumberFormatException e) {
                int number = i.getOrderedRank(i.getRank());

                temp[count] = number;
            }
            count++;
        }

        for(Card i : cards) {
            if (!i.getSuit().equals(suit)) {
                suitC = false;
                break;
            }
        }

        Arrays.sort(temp);

        if(temp[0]+1 == temp[1] &&
                temp[1]+1 == temp[2] &&
                temp[2]+1 == temp[3] &&
                temp[3]+1 == temp[4] &&
                suitC == true) {
            return true;
        }
        return false;
    }

    public boolean foakC() {
        String[] temp = new String[5];
        int count = 0;

        for(Card i : cards) {
            temp[count] = i.getRank();
            count++;
        }

        Arrays.sort(temp);

        if (temp[0].equals(temp[1]) && temp[0].equals(temp[2]) && temp[0].equals(temp[3]) ||
                temp[1].equals(temp[2]) && temp[1].equals(temp[3]) && temp[1].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean houseC() {
        String[] temp = new String[5];
        int count = 0;

        for(Card i : cards) {
            temp[count] = i.getRank();
            count++;
        }

        Arrays.sort(temp);

        if (temp[0].equals(temp[1]) && temp[2].equals(temp[3]) && temp[2].equals(temp[4]) ||
                temp[0].equals(temp[1]) && temp[0].equals(temp[2]) && temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean flushC() {
        ArrayList<Card> temp = cards;
        String suit = temp.get(0).getSuit();

        for(Card i : temp) {
            if (!suit.equals(i.getSuit())) {
                return false;
            }
        }
        return true;
    }

    public boolean straightC() {
        int[] temp = new int[5];
        int count = 0;

        for(Card i : cards) {
            try {
                int number = Integer.parseInt(i.getRank());

                temp[count] = number;
            } catch (NumberFormatException e) {
                int number = i.getOrderedRank(i.getRank());

                temp[count] = number;
            }
            count++;
        }

        Arrays.sort(temp);

        if(temp[0]+1 == temp[1] &&
                temp[1]+1 == temp[2] &&
                temp[2]+1 == temp[3] &&
                temp[3]+1 == temp[4]) {
            return true;
        }
        return false;
    }

    public boolean toakC() {
        String[] temp = new String[5];
        int count = 0;

        for(Card i : cards) {
            temp[count] = i.getRank();
            count++;
        }

        Arrays.sort(temp);

        if (temp[0].equals(temp[1]) && temp[0].equals(temp[2]) ||
                temp[1].equals(temp[2]) && temp[1].equals(temp[3]) ||
                temp[2].equals(temp[3]) && temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean tPairC() {
        String[] temp = new String[5];
        int count = 0;

        for(Card i : cards) {
            temp[count] = i.getRank();
            count++;
        }

        Arrays.sort(temp);

        if (temp[0].equals(temp[1]) && temp[2].equals(temp[3]) ||
                temp[0].equals(temp[1]) && temp[3].equals(temp[4]) ||
                temp[1].equals(temp[2]) && temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public boolean pairC() {
        String[] temp = new String[5];
        int count = 0;

        for(Card i : cards) {
            temp[count] = i.getRank();
            count++;
        }

        Arrays.sort(temp);

        if (temp[0].equals(temp[1]) ||
                temp[1].equals(temp[2]) ||
                temp[2].equals(temp[3]) ||
                temp[3].equals(temp[4])) {
            return true;
        }
        return false;
    }

    public void sortHand() {
        List<Card> temp = cards;
        temp.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());     // order by suit if
            }                                                                                   // ranks are the same

            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());         // otherwise, by rank
        });
    }
}