import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;

    public User(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String pin) {
        return this.userPin.equals(pin);
    }
}

class Account {
    private String accountNumber;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction(new Transaction("Deposit", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction(new Transaction("Withdrawal", amount, balance));
            return true;
        }
        return false;
    }

    public boolean transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            addTransaction(new Transaction("Transfer to " + toAccount.getAccountNumber(), amount, balance));
            toAccount.addTransaction(new Transaction("Transfer from " + accountNumber, amount, toAccount.getBalance()));
            return true;
        }
        return false;
    }
}

class Transaction {
    private String type;
    private double amount;
    private double postTransactionBalance;

    public Transaction(String type, double amount, double postTransactionBalance) {
        this.type = type;
        this.amount = amount;
        this.postTransactionBalance = postTransactionBalance;
    }

    @Override
    public String toString() {
        return type + ": " + amount + " | Balance: " + postTransactionBalance;
    }
}

class ATMOperations {
    public static void showTransactionHistory(Account account) {
        System.out.println("Transaction History:");
        for (Transaction transaction : account.getTransactions()) {
            System.out.println(transaction);
        }
    }

    public static void deposit(Account account, double amount) {
        account.deposit(amount);
        System.out.println("Deposited: " + amount);
    }

    public static void withdraw(Account account, double amount) {
        if (account.withdraw(amount)) {
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public static void transfer(Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.transfer(toAccount, amount)) {
            System.out.println("Transferred: " + amount + " to Account: " + toAccount.getAccountNumber());
        } else {
            System.out.println("Transfer failed. Insufficient balance.");
        }
    }
}

public class ATM {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static HashMap<String, String> userAccountMap = new HashMap<>(); // Maps user IDs to account numbers

    public static void main(String[] args) {
        // Setup initial users and accounts
        setupInitialData();

        // User Authentication
        User authenticatedUser = authenticateUser();

        if (authenticatedUser != null) {
            Account userAccount = getAccountByUser(authenticatedUser);

            if (userAccount != null) {
                performOperations(userAccount);
            }
        }
    }

    private static void setupInitialData() {
        // Create users
        users.add(new User("user1", "pin1"));
        users.add(new User("user2", "pin2"));

        // Create accounts
        accounts.add(new Account("ACC1", 1000.0));
        accounts.add(new Account("ACC2", 2000.0));

        // Map users to accounts
        userAccountMap.put("user1", "ACC1");
        userAccountMap.put("user2", "ACC2");

        // Debugging print statements
        System.out.println("Initial Users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId());
        }

        System.out.println("Initial Accounts:");
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
        }
    }

    private static User authenticateUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.validatePin(userPin)) {
                System.out.println("Authentication Successful!");
                return user;
            }
        }

        System.out.println("Authentication Failed!");
        return null;
    }

    private static Account getAccountByUser(User user) {
        String accountNumber = userAccountMap.get(user.getUserId());
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private static void performOperations(Account account) {
        while (true) {
            System.out.println("\nSelect Operation:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    ATMOperations.showTransactionHistory(account);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    ATMOperations.withdraw(account, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    ATMOperations.deposit(account, depositAmount);
                    break;
                case 4:
                    System.out.print("Enter account number to transfer to: ");
                    String toAccountNumber = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    Account toAccount = getAccountByNumber(toAccountNumber);
                    if (toAccount != null) {
                        ATMOperations.transfer(account, toAccount, transferAmount);
                    } else {
                        System.out.println("Invalid account number.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
