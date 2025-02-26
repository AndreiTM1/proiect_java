import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pachet {
    private List<Carte> cards;

    public Pachet() {
        cards = new ArrayList<>();
        String[] suite = {"Inima rosie", "Romb", "Trefla", "Inima neagra"};
        String[] rankuri = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Juvete", "Dama", "Rege", "As"};

        for (String suita : suite) {
            for (String rank : rankuri) {
                cards.add(new Carte(suita, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Carte drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Nu mai sunt carti in pachet");
        }
        return cards.remove(0);
    }

    public void addCard(Carte carte) {
        cards.add(carte);
    }

    // Metodă pentru a obține toate cărțile din pachet
    public List<Carte> getCards() {
        return cards;
    }
}
