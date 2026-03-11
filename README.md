# proiect_java

This java program has 4 classes (without main) and they do the following:
The "Carte" class defines the types of cards used in the games and they are the standard ones.
The "Pachet" is where the decks are created using "Carte" variables.
The "Razboi" class is the War game mode, it has a GUI where you can play rounds; because the project is not done and I only coded the rules of this game, it consists of only drawing one card from each of the 2 players' decks and checking whether one of them has a higher value (and that players receives the cards) or if they are equal, a "Razboi" state is started and they put down the equal number of cards from their deck and the one whose last placed card is of higher value wins. The game ends when one of the players wins all the cards.
The "Makao" class is the Makao game mode. I have spent a lot more time polishing this game mode than the other, and here the 2 players have their card hands displayed in the GUI. Here the player who empties their hand first wins the game and they can do that by discarding a card that matches the rank or the suite of the last placed card. I have coded the special rule cards such as: the 2, 3 and the Joker cards can force the opponent to draw cards unless they also place those cards; the 4 card is used to stop the previous mentioned cards from their effect; 7 is used to change the current suite; the Ace is used to skip the next opponent.

