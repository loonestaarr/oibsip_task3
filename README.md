# oibsip_task3
# ATM Console Application

This project is a console-based ATM application built using Java. It consists of five different classes and provides various ATM functionalities such as viewing transaction history, withdrawing money, depositing money, transferring money, and quitting the application.

## Features

- **User Authentication**: Prompts the user for a user ID and PIN to access the ATM functionalities.
- **Transaction History**: Allows users to view their transaction history.
- **Withdraw**: Enables users to withdraw money from their account.
- **Deposit**: Enables users to deposit money into their account.
- **Transfer**: Allows users to transfer money to another account.
- **Quit**: Exits the application.

## Classes

1. **User**: Represents a user with a user ID and PIN.
2. **Account**: Represents an account with an account number, balance, and transaction history.
3. **Transaction**: Represents a transaction with type, amount, and post-transaction balance.
4. **ATMOperations**: Contains static methods for various ATM operations.
5. **ATM**: The main class that drives the application.

## Setup and Execution

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/yourusername/atm-console-application.git
    cd atm-console-application
    ```

2. **Compile the Code**:
    ```bash
    javac ATM.java
    ```

3. **Run the Application**:
    ```bash
    java ATM
    ```

## Initial Setup

The application initializes with two users and two accounts:

- **Users**:
  - User ID: `user1`, PIN: `pin1`
  - User ID: `user2`, PIN: `pin2`
- **Accounts**:
  - Account Number: `ACC1` (associated with `user1`), Initial Balance: `1000.0`
  - Account Number: `ACC2` (associated with `user2`), Initial Balance: `2000.0`

## Sample Interaction

### User Authentication
