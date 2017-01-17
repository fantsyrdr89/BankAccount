package armstrong.alexandra;

import static armstrong.alexandra.Status.*;
import static armstrong.alexandra.AccountType.*;
import static armstrong.alexandra.Overdraft.*;

/**
 * Created by alexandraarmstrong on 1/17/17.
 */
public class BankAccount {
    private final static int ROUTERNUMBER = 1234567;
    private static int accountCounter = 0;

    private AccountType accountType;
    private long accountNumber;
    private double balance = 0;
    private String accountHolderName;
    private double interestRate = 1.00d;
    private Status status = CLOSED;
    private Overdraft overdraft = DISABLED;
    //private File record;

    BankAccount(AccountType accountType, String accountHolderName){
        this.accountType = accountType;
        this.accountHolderName = accountHolderName;
        accountCounter++;
        accountNumber = Integer.valueOf(String.valueOf(ROUTERNUMBER) + String.valueOf(accountCounter));
        status = OPEN;
    }

    BankAccount(AccountType accountType, String accountHolderName, Overdraft overdraft){
        this(accountType, accountHolderName);
        this.overdraft = overdraft;
    }

    BankAccount(AccountType accountType, String accountHolderName, double interestRate){
        this(accountType, accountHolderName);
        this.interestRate = interestRate;
    }

    BankAccount(AccountType accountType, String accountHolderName, double interestRate, Overdraft overdraft){
        this(accountType, accountHolderName, overdraft);
        this.interestRate = interestRate;
    }


    public AccountType getAccountType(){
        return accountType;
    }

    public long getAccountNumber(){
        return accountNumber;
    }

    public Double getBalance(){
        if (status == FROZEN) {
            return null;
        } else {
            return balance;
        }
    }

    public String getAccountHolderName(){
        return accountHolderName;
    }

    public double getInterestRate(){
        return interestRate;
    }

    public Status getStatus(){
        return status;
    }

    public Overdraft getOverdraft() {
        return overdraft;
    }

    void setStatus(Status status){
        this.status = status;
    }

    public String changeBalance(double amount) {
        if (status == OPEN) {
            if (overdraft == ENABLED) {
                if (amount > 0 || amount > getBalance()) {
                    balance += amount;
                    return "Balance adjusted";
                } else {
                    return "Insufficient Funds";
                }
            } else {
                balance += amount;
                return "Balance adjusted";
            }
        } else {
            return "Balance inaccessible";
        }
    }

    public String transferMoneyToOtherAccount(BankAccount otherAccount, double amount) {
        if (accountHolderName.equalsIgnoreCase(otherAccount.accountHolderName)) {
            if (getBalance() > amount || overdraft != DISABLED) {
                changeBalance(-1 * amount);
                otherAccount.changeBalance(amount);
                return "Transfer Successful";
            } else {
                return "Insufficient funds";
            }
        } else {
            return "Permission Denied";
        }
    }

    public void setAccountHolderName(String name){
        if(status != CLOSED){
            this.accountHolderName = name;
        }
    }

    public String closeAccount(){
        if(getBalance() == 0d){
            setStatus(CLOSED);
            return "Account Closed";
        } else {
            return "Please withdraw funds";
        }
    }

    public String changeFreezeStatus(){
        if (status == FROZEN) {
            setStatus(OPEN);
            return "Account unfrozen";
        } else {
            setStatus(FROZEN);
            return "Account frozen";
        }
    }
}



