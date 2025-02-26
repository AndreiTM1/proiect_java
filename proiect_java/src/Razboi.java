import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Razboi {
    private JFrame frame;
    private JLabel player1Label, player2Label, resultLabel;
    private JButton playButton;
    private List<Carte> player1Deck, player2Deck;
    private Pachet deck;

    public Razboi() {

        deck = new Pachet();
        deck.shuffle();

        player1Deck = new ArrayList<>();
        player2Deck = new ArrayList<>();



        for (int i = 0; i < 52; i++) {
            if (i % 2 == 0) {
                player1Deck.add(deck.drawCard());
            } else {
                player2Deck.add(deck.drawCard());
            }
        }


        frame = new JFrame("Razboi!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());


        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(2, 1));

        player1Label = new JLabel("Jucatorul 1: " + player1Deck.size() + " carti");
        player2Label = new JLabel("Jucatorul 2: " + player2Deck.size() + " carti");
        playersPanel.add(player1Label);
        playersPanel.add(player2Label);

        frame.add(playersPanel, BorderLayout.NORTH);


        resultLabel = new JLabel("Apasa 'Play' pentru a juca o runda!", SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.CENTER);


        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound();
            }
        });

        frame.add(playButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void playRound() {
        if (player1Deck.isEmpty() || player2Deck.isEmpty()) {
            String winner = player1Deck.isEmpty() ? "Jucatorul 2 castiga meciul!" : "Jucatorul 1 castiga meciul!";
            resultLabel.setText(winner);
            playButton.setEnabled(false);
            return;
        }

        Carte player1Card = player1Deck.remove(0);
        Carte player2Card = player2Deck.remove(0);

        List<Carte> roundCards = new ArrayList<>();
        roundCards.add(player1Card);
        roundCards.add(player2Card);

        int result = compareCards(player1Card, player2Card);

        if (result > 0) {
            resultLabel.setText("Jucatorul 1 castiga runda! " + player1Card + " vs " + player2Card);
            player1Deck.addAll(roundCards);
        } else if (result < 0) {
            resultLabel.setText("Jucatorul 2 castiga runda! " + player1Card + " vs " + player2Card);
            player2Deck.addAll(roundCards);
        } else {
            int value = getCardValue(player1Card);
            if (player1Deck.size() < value || player2Deck.size() < value) {
                String winner = player1Deck.size() < value ? "Jucatorul 2 castiga meciul!" : "Jucatorul 1 castiga meciul!";
                resultLabel.setText(winner);
                playButton.setEnabled(false);
                return;
            }

            for (int i = 0; i < value; i++) {
                roundCards.add(player1Deck.remove(0));
                roundCards.add(player2Deck.remove(0));
            }

            Carte lastCardPlayer1 = roundCards.get(roundCards.size() - 2);
            Carte lastCardPlayer2 = roundCards.get(roundCards.size() - 1);

            result = compareCards(lastCardPlayer1, lastCardPlayer2);
            if (result > 0) {
                resultLabel.setText("Jucatorul 1 castiga razboiul cu " + lastCardPlayer1 + " vs " + lastCardPlayer2);
                player1Deck.addAll(roundCards);
            } else {
                resultLabel.setText("Jucatorul 2 castiga razboiul cu " + lastCardPlayer2 + " vs " + lastCardPlayer1);
                player2Deck.addAll(roundCards);
            }
        }

        player1Label.setText("Jucatorul 1: " + player1Deck.size() + " carti");
        player2Label.setText("Jucatorul 2: " + player2Deck.size() + " carti");
    }


    private int compareCards(Carte card1, Carte card2) {
        return Integer.compare(getCardValue(card1), getCardValue(card2));
    }

    private int getCardValue(Carte card) {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Juvete", "Dama", "Rege", "As"};

        return Arrays.asList(ranks).indexOf(card.getRank()) + 2;
    }


}
