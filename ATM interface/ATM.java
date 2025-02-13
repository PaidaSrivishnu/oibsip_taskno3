import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ATM extends JFrame
{
    private double balance = 1000.00;   //initial balance
    private final JTextArea display_text;      //text area in the middle
    private final ArrayList<String> transaction_history;   //to store transaction history

    public ATM() 
    {
        transaction_history = new ArrayList<>();
        setTitle("Maze Bank");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 

        display_text = new JTextArea();
        display_text.setEditable(false);
        display_text.setFont(new Font("Courier New",Font.PLAIN,16));
        display_text.setBackground(Color.WHITE);
        display_text.setText("Welcome To Maze Bank\n Please Enter Your PIN :\n");
        add(display_text, BorderLayout.CENTER);

        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(3 , 1));
        buttonPanel2.setLayout(new GridLayout(3 , 1));

        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("       Deposit        ");
        JButton transferButton = new JButton("Transfer");
        JButton transferHistoryButton = new JButton("Transaction History");
        JButton exitButton = new JButton("Exit");

        withdrawButton.addActionListener(new WithdrawAction());
        depositButton.addActionListener(new DepositAction());
        checkBalanceButton.addActionListener(new CheckBalanceAction());
        transferButton.addActionListener(new TransferAction()); 
        transferHistoryButton.addActionListener(new HistoryAction()); 
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel1.add(withdrawButton);
        buttonPanel1.add(depositButton);
        buttonPanel1.add(transferButton);
        buttonPanel2.add(checkBalanceButton);
        buttonPanel2.add(transferHistoryButton);
        buttonPanel2.add(exitButton);

        add(buttonPanel1,BorderLayout.WEST);
        add(buttonPanel2,BorderLayout.EAST);
        String PIN = JOptionPane.showInputDialog("Enter Your PIN: ");
        if(PIN != null && PIN.equals("6898")) 
        {
            display_text.setText("PIN Accepted.\nChoose An Operation.");
        }
        else 
        {
            JOptionPane.showMessageDialog(ATM.this,"Invalid PIN.\nPlease Try Again");
            System.exit(0);
        }
    }

    private class WithdrawAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String amt = JOptionPane.showInputDialog(ATM.this,"Enter Amount to Withdraw: ");
            if(amt != null) 
            {
                try 
                {
                    double amount = Double.parseDouble(amt);
                    if(amount > 0 && amount <= balance) 
                    {
                        balance = balance - amount;
                        transaction_history.add("\nWithdrew: ₹" + amount);
                        display_text.setText("Withdrawl Succesful.\nCurrent Balance: ₹" + balance);
                    }
                    else if(balance <= 0 || amount > balance) 
                    {
                        display_text.setText("Insufficient Funds.");
                    }
                }   
                catch (NumberFormatException ex) 
                {
                    display_text.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class DepositAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String amt = JOptionPane.showInputDialog(ATM.this,"Enter Amount to Deposit: ");
            double amount = Double.parseDouble(amt);
            if(amount > 0) 
            {
                try 
                {   
                    balance = balance + amount;
                    transaction_history.add("\nDeposited: ₹" + amount);
                    display_text.setText("Deposit Succesful.\nCurrent Balance: ₹" + balance);
                }
                catch (NumberFormatException ex) 
                {
                    display_text.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class CheckBalanceAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            display_text.setText("Current Balance: ₹" + balance);
        }
    }
    
    private class TransferAction implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String recp = JOptionPane.showInputDialog(ATM.this,"Enter Recipient's Account No. : ");
            if(recp != null)
            {
                try
                {
                    long recpAcc = Long.parseLong(recp);
                    if(recpAcc > 99999999999L && recpAcc < 1000000000000L)
                    {
                        String amt = JOptionPane.showInputDialog(ATM.this,"Enter Amount: ");
                        double amount = Double.parseDouble(amt);
                        if(amount > 0 && amount <= balance) 
                        {
                            balance = balance - amount;
                            transaction_history.add("\nTransferred Amount: ₹" + amount + "\nTransfer Account: " + recp + "\n");
                            display_text.setText("Transferred Amount Succesfully.\nCurrent Balance: ₹" + balance);
                        }
                        else if(balance <= 0 || amount > balance) 
                        {
                            display_text.setText("Insufficient Funds.");
                        }
                    }
                    else 
                    {
                        display_text.setText("Invalid Account No.\nPlease Try Again");
                    } 
                }
                catch (NumberFormatException ex) 
                {
                    display_text.setText("Invalid Amount.\nPlease Try Again");
                }
            }
        }
    }

    private class HistoryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(transaction_history.isEmpty())
            {
                display_text.setText("No Transaction History \n ");
            }
            else
            {
                StringBuilder history = new StringBuilder("Transaction History : \n");
                for(String transaction : transaction_history)
                {
                    history.append(transaction).append("\n");
                }
                display_text.setText(history.toString());
            }
        }
    }

    public static void main(String[] args) 
    {
            ATM atm = new ATM();
            atm.setVisible(true);
    }
}