package user;

public class Customer extends User{
    private double balance;

    public Customer(int id, String password, String email, String type, double balance) {
        super(id, password, email, type);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    
}
