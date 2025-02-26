public class Carte {
    private String suita;
    private String rank;

    public Carte(String suita, String rank) {
        this.suita = suita;
        this.rank = rank;
    }

    public String getSuit() {
        return suita;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " de " + suita;
    }
}