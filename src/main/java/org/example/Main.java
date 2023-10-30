package org.example;
/*
1. Создать программу управления банковским счетом (Account).

Программа должна позволять пользователю вводить начальный баланс счета, сумму
депозита и сумму снятия средств. При этом она должна обрабатывать следующие исключительные ситуации:
Попытка создать счет с отрицательным начальным балансом должна вызывать исключение IllegalArgumentException с соответствующим сообщением.
Попытка внести депозит с отрицательной суммой должна вызывать исключение IllegalArgumentException с соответствующим сообщением.
Попытка снять средства, сумма которых превышает текущий баланс, должна вызывать исключение InsufficientFundsException
 с сообщением о недостаточных средствах и текущим балансом.
Продемонстрируйте работу вашего приложения:
Программа должна обрабатывать все исключения с помощью конструкции try-catch,
выводя соответствующие сообщения об ошибках.

2*.
Создать несколько типов счетов, унаследованных от Account, например: CreditAcciunt, DebitAccount.
Создать класс (Transaction), позволяющий проводить транзакции между счетами (переводить сумму с одного счета на другой)
Класс Transaction должен возбуждать исключение в случае неудачной попытки перевести деньги с одного счета на другой.
Продемонстрируйте работу вашего приложения:
Программа должна обрабатывать все исключения с помощью конструкции try-catch, выводя соответствующие сообщения об ошибках.

 */
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class Account {
    private double balance;

    public Account(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным.");
        }
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма депозита не может быть отрицательной.");
        }
        this.balance += amount;
        System.out.println("Депозит в размере " + amount + " успешно внесен. Текущий баланс: " + balance);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма снятия не может быть отрицательной.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Недостаточно средств на счете. Текущий баланс: " + balance);
        }
        this.balance -= amount;
        System.out.println("Сумма " + amount + " успешно снята. Текущий баланс: " + balance);
    }

    public double getBalance() {
        return balance;
    }
}

class Transaction {
    public static void transfer(Account fromAccount, Account toAccount, double amount) throws InsufficientFundsException {
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Перевод успешно выполнен.");
        } catch (InsufficientFundsException e) {
            throw new InsufficientFundsException("Перевод не выполнен: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем счет с начальным балансом 1000
            Account account1 = new Account(1000);

            // Создаем еще один счет с начальным балансом 500
            Account account2 = new Account(500);
            // Создаем еще один счет с начальным балансом - 500
            Account account3 = new Account(-500);

            // Переводим 700 с первого счета на второй
            Transaction.transfer(account1, account2, 700);

            // Пытаемся снять больше, чем есть на счете
            Transaction.transfer(account1, account2, 500);

        } catch (IllegalArgumentException | InsufficientFundsException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
