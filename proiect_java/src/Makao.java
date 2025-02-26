import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Makao {
    private JFrame frame;
    private JLabel deckLabel, lastCardLabel, turnLabel;
    private JPanel player1HandPanel, player2HandPanel;
    private JButton drawButton1, drawButton2;
    private List<Carte> player1Hand, player2Hand;
    private List<Carte> deck, discardPile;
    private Carte lastCard;
    private int currentPlayer;
    private int cardsToDraw = 0;

    public Makao() {
        initializeGame();
        setupUI();
        updateButtonStates();
    }

    private void initializeGame() {
        Pachet pachet = new Pachet();
        pachet.addCard(new Carte("Joker", "Negru"));
        pachet.addCard(new Carte("Joker", "Rosu"));
        pachet.shuffle();

        deck = new ArrayList<>(pachet.getCards());
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        discardPile = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            player1Hand.add(deck.remove(0));
            player2Hand.add(deck.remove(0));
        }

        do {
            lastCard = deck.remove(0);
        } while (isSpecialCard(lastCard));

        discardPile.add(lastCard);
        currentPlayer = 1;
    }

    private void setupUI() {
        frame = new JFrame("Makao");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new BorderLayout());
        player2HandPanel = new JPanel();
        player2HandPanel.setLayout(new GridLayout(1, player2Hand.size()));
        updateHandPanel(player2HandPanel, player2Hand, 2);

        drawButton2 = new JButton("Trage carte (Jucător 2)");
        drawButton2.addActionListener(e -> {
            if (currentPlayer == 2) {
                drawCard(2);
            } else {
                JOptionPane.showMessageDialog(frame, "Nu este rândul Jucătorului 2!");
            }
        });

        topPanel.add(drawButton2, BorderLayout.NORTH);
        topPanel.add(player2HandPanel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        turnLabel = new JLabel("Este rândul Jucătorului 1", SwingConstants.CENTER);
        lastCardLabel = new JLabel("Ultima carte jucată: " + lastCard, SwingConstants.CENTER);
        deckLabel = new JLabel("Cărți rămase în teanc: " + deck.size(), SwingConstants.CENTER);
        centerPanel.add(turnLabel);
        centerPanel.add(lastCardLabel);
        centerPanel.add(deckLabel);
        frame.add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        player1HandPanel = new JPanel();
        player1HandPanel.setLayout(new GridLayout(1, player1Hand.size()));
        updateHandPanel(player1HandPanel, player1Hand, 1);

        drawButton1 = new JButton("Trage carte (Jucător 1)");
        drawButton1.addActionListener(e -> {
            if (currentPlayer == 1) {
                drawCard(1);
            } else {
                JOptionPane.showMessageDialog(frame, "Nu este rândul Jucătorului 1!");
            }
        });

        bottomPanel.add(drawButton1, BorderLayout.NORTH);
        bottomPanel.add(player1HandPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateHandPanel(JPanel handPanel, List<Carte> hand, int player) {
        handPanel.removeAll();


        int numRows = (int) Math.ceil(hand.size() / 6.0);
        handPanel.setLayout(new GridLayout(numRows, Math.min(hand.size(), 6), 10, 10));

        for (Carte carte : hand) {
            JButton cardButton = new JButton(carte.toString());
            cardButton.addActionListener(e -> {
                if (currentPlayer == player) {
                    playCard(carte, player);
                } else {
                    JOptionPane.showMessageDialog(frame, "Nu este rândul Jucătorului " + player + "!");
                }
            });
            handPanel.add(cardButton);
        }

        handPanel.revalidate();
        handPanel.repaint();
    }


    private void updateButtonStates() {
        drawButton1.setEnabled(currentPlayer == 1);
        drawButton2.setEnabled(currentPlayer == 2);

        for (Component component : player1HandPanel.getComponents()) {
            component.setEnabled(currentPlayer == 1);
        }

        for (Component component : player2HandPanel.getComponents()) {
            component.setEnabled(currentPlayer == 2);
        }
    }

    private void playCard(Carte carte, int player) {
        List<Carte> currentHand = (player == 1) ? player1Hand : player2Hand;

        if (isValidMove(carte)) {
            currentHand.remove(carte);
            discardPile.add(carte);
            lastCard = carte;

            if (player == 1) {
                updateHandPanel(player1HandPanel, player1Hand, 1);
            } else {
                updateHandPanel(player2HandPanel, player2Hand, 2);
            }

            lastCardLabel.setText("Ultima carte jucată: " + lastCard);

            if (currentHand.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Jucătorul " + player + " a câștigat!");
                System.exit(0);
            }

            applySpecialRules(carte);

            if (cardsToDraw == 0) {
                switchPlayer();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Mutare invalidă!");
        }
    }

    private void drawCard(int player) {
        List<Carte> currentHand = (player == 1) ? player1Hand : player2Hand;

        if (!deck.isEmpty()) {
            Carte drawnCard = deck.remove(0);
            currentHand.add(drawnCard);

            if (player == 1) {
                updateHandPanel(player1HandPanel, player1Hand, 1);
            } else {
                updateHandPanel(player2HandPanel, player2Hand, 2);
            }

            deckLabel.setText("Cărți rămase în teanc: " + deck.size());


            if (cardsToDraw > 0) {
                cardsToDraw--;
            }


            if (cardsToDraw == 0) {
                switchPlayer();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Nu mai sunt cărți în teanc!");
        }
    }



    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        turnLabel.setText("Este rândul Jucătorului " + currentPlayer);
        updateButtonStates();
    }

    private boolean isSpecialCard(Carte carte) {
        String rank = carte.getRank();
        return rank.equals("2") || rank.equals("3") || rank.equals("4") || rank.equals("K") || rank.equals("As") || rank.equals("Joker");
    }

    private boolean isValidMove(Carte carte) {

        if (lastCard.getRank().equals("Joker") && lastCard.getSuit().equals("Negru")) {
            return carte.getSuit().equals("Trefla") || carte.getSuit().equals("Inima neagra");
        }

        if (carte.getRank().equals("Joker") && carte.getSuit().equals("Negru")) {
            return lastCard.getSuit().equals("Trefla") || lastCard.getSuit().equals("Inima neagra");
        }

        if (carte.getRank().equals("4") && cardsToDraw > 0) {
            return true;
        }


        if (lastCard.getRank().equals("Joker") && lastCard.getSuit().equals("Rosu")) {
            return carte.getSuit().equals("Inima rosie") || carte.getSuit().equals("Romb");
        }

        if (carte.getRank().equals("Joker") && carte.getSuit().equals("Rosu")) {
            return lastCard.getSuit().equals("Inima rosie") || lastCard.getSuit().equals("Romb");
        }

        if (carte.getRank().equals("7") && cardsToDraw == 0) {
            return true;
        }


        return carte.getSuit().equals(lastCard.getSuit()) || carte.getRank().equals(lastCard.getRank());
    }



    private void applySpecialRules(Carte carte) {
        String rank = carte.getRank();
        String suit = carte.getSuit();

        switch (rank) {
            case "2":
                cardsToDraw += 2;
                JOptionPane.showMessageDialog(frame, "Următorul jucător trebuie să ia " + cardsToDraw + " cărți!");
                switchPlayer();
                break;

            case "3":
                cardsToDraw += 3;
                JOptionPane.showMessageDialog(frame, "Următorul jucător trebuie să ia " + cardsToDraw + " cărți!");
                switchPlayer();
                break;


            case "Joker":
                if (suit.equals("Joker Negru")) {
                    cardsToDraw += 5;
                    JOptionPane.showMessageDialog(frame, "Următorul jucător trebuie să ia " + cardsToDraw + " cărți!");
                    switchPlayer();
                } else if (suit.equals("Joker Rosu")) {
                    cardsToDraw += 10;
                    JOptionPane.showMessageDialog(frame, "Următorul jucător trebuie să ia " + cardsToDraw + " cărți!");
                    switchPlayer();
                }
                break;

            case "7":
                String[] options = {"Inima rosie", "Romb", "Trefla", "Inima neagra"};
                String newSuit = null;

                while (newSuit == null || newSuit.isEmpty()) {
                    newSuit = (String) JOptionPane.showInputDialog(
                            frame,
                            "Alege un semn:",
                            "Schimbare Semn",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            options[0]
                    );


                    if (newSuit == null) {
                        JOptionPane.showMessageDialog(frame, "Trebuie să alegi un semn pentru a continua!");
                    }
                }


                lastCard = new Carte(newSuit, lastCard.getRank());
                lastCardLabel.setText("Ultima carte jucată: " + lastCard);
                break;


            case "As":
                JOptionPane.showMessageDialog(frame, "Jucătorul următor își pierde rândul!");
                switchPlayer();

                break;

            case "4":

                if (cardsToDraw > 0) {
                    JOptionPane.showMessageDialog(frame, "Penalizarea de a lua " + cardsToDraw + " cărți a fost anulată!");
                    cardsToDraw = 0;

                }
                break;

            default:
                break;
        }
    }



}
