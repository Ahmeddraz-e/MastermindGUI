import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MastermindCustomGUI  extends JFrame {
    private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.PINK};
    private JPanel[][] gridCells; 
    private int currentRow = 0;  
    private int codeLength = 4;  
    private int maxAttempts = 10; 
    private Color[] secretCode; 
    private Color[] currentGuess; 
    private JLabel correctPositionLabel;
    private JLabel correctColorLabel;
    private JButton submitButton; 

    public MastermindCustomGUI () {
        setTitle("Mastermind Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(new BorderLayout());

       
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 4, 5, 5)); 
        gridPanel.setBackground(Color.BLACK);
        gridCells = new JPanel[10][4];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                gridCells[i][j] = new JPanel();
                gridCells[i][j].setPreferredSize(new Dimension(40, 40));
                gridCells[i][j].setBackground(Color.DARK_GRAY);
                gridCells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(gridCells[i][j]);
            }
        }

        
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(1, COLORS.length, 5, 5));
        for (Color color : COLORS) {
            JPanel colorOption = new JPanel();
            colorOption.setBackground(color);
            colorOption.setPreferredSize(new Dimension(40, 40));
            colorOption.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            colorOption.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    handleColorClick(color);
                }
            });
            colorPanel.add(colorOption);
        }

       
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());
        controlsPanel.setBackground(Color.BLACK);

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(Color.RED);
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        

        submitButton = new JButton("Submit Guess");
        submitButton.setBackground(Color.GREEN);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitGuess();
            }
        });

        controlsPanel.add(resetButton);
       
        controlsPanel.add(submitButton); 

        
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new FlowLayout());
        feedbackPanel.setBackground(Color.BLACK);

        correctPositionLabel = new JLabel("Black Ball: 0");
        correctPositionLabel.setForeground(Color.WHITE);
        correctColorLabel = new JLabel("White Ball: 0");
        correctColorLabel.setForeground(Color.WHITE);

        feedbackPanel.add(correctPositionLabel);
        feedbackPanel.add(correctColorLabel);

       
        add(gridPanel, BorderLayout.CENTER);
        add(colorPanel, BorderLayout.SOUTH);
        add(feedbackPanel, BorderLayout.NORTH);
        add(controlsPanel, BorderLayout.EAST);

        
        secretCode = generateSecretCode();

        setVisible(true);
    }

    
    private void handleColorClick(Color selectedColor) {
        if (currentRow < 10) {
            for (int i = 0; i < 4; i++) {
                JPanel cell = gridCells[currentRow][i];
                if (cell.getBackground() == Color.DARK_GRAY) {
                    cell.setBackground(selectedColor);
                    break;
                }
            }
        }
    }

   
    private Color[] generateSecretCode() {
        Color[] code = new Color[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = COLORS[(int) (Math.random() * COLORS.length)];
        }
        return code;
    }

    
   

   
    private void resetGame() {
        currentRow = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                gridCells[i][j].setBackground(Color.DARK_GRAY);
            }
        }
        currentGuess = new Color[codeLength];
        correctPositionLabel.setText("Correct Position: 0");
        correctColorLabel.setText("Correct Color: 0");
        secretCode = generateSecretCode();
    }

    
    private void submitGuess() {
       
        currentGuess = new Color[codeLength];
        for (int i = 0; i < codeLength; i++) {
            currentGuess[i] = gridCells[currentRow][i].getBackground();
        }

        
        checkGuess();

   
        currentRow++;
        if (currentRow >= 10) {
            JOptionPane.showMessageDialog(this, "Game Over! You've used all attempts.");
        }
    }

   
    private void checkGuess() {
        int correctPosition = 0;
        int correctColor = 0;
        boolean[] secretCodeUsed = new boolean[codeLength];
        boolean[] guessUsed = new boolean[codeLength];

     
        for (int i = 0; i < codeLength; i++) {
            if (currentGuess[i].equals(secretCode[i])) {
                correctPosition++;
                secretCodeUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        
        for (int i = 0; i < codeLength; i++) {
            if (!guessUsed[i]) {
                for (int j = 0; j < codeLength; j++) {
                    if (!secretCodeUsed[j] && currentGuess[i].equals(secretCode[j])) {
                        correctColor++;
                        secretCodeUsed[j] = true;
                        break;
                    }
                }
            }
        }

       
        correctPositionLabel.setText("Black Ball: " + correctPosition);
        correctColorLabel.setText("White Ball: " + correctColor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MastermindCustomGUI ::new);
    }
}
