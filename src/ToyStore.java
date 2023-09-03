import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

class Toy {
    int id;
    String name;
    int quantity;
    double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }
}

public class ToyStore {
    ArrayList<Toy> toys;
    ArrayList<Toy> prizeToys;

    public ToyStore() {
        this.toys = new ArrayList<>();
        this.prizeToys = new ArrayList<>();
    }

    public void addNewToy(int id, String name, int quantity, double weight) {
        Toy newToy = new Toy(id, name, quantity, weight);
        toys.add(newToy);
    }

    public void updateToyWeight(int id, double newWeight) {
        for (Toy toy : toys) {
            if (toy.id == id) {
                toy.weight = newWeight;
                return;
            }
        }
    }

    public void choosePrizeToy() {
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.weight;
        }

        Random rand = new Random();
        double randomValue = rand.nextDouble() * totalWeight;

        double cumulativeWeight = 0.0;
        for (Toy toy : toys) {
            cumulativeWeight += toy.weight;
            if (randomValue <= cumulativeWeight) {
                if (toy.quantity > 0) {
                    prizeToys.add(toy);
                    toy.quantity--;
                    return;
                }
            }
        }
    }

    public void getPrizeToy() {
        if (prizeToys.size() == 0) {
            System.out.println("No prize toys are available");
            return;
        }

        Toy toy = prizeToys.remove(0);

        try {
            FileWriter writer = new FileWriter("prize_toys.txt", true);
            writer.write("ID: " + toy.id + ", Name: " + toy.name + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyStore store = new ToyStore();
        store.addNewToy(1, "Car", 5, 0.2);
        store.addNewToy(2, "Doll", 3, 0.3);
        store.addNewToy(3, "Ball", 7, 0.5);

        store.choosePrizeToy();
        store.choosePrizeToy();

        store.getPrizeToy();
    }
}
