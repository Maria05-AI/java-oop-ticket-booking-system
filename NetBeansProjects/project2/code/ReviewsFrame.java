/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import oopproject.model.*;
import oopproject.dao.*;
import java.util.ArrayList;


public class ReviewsFrame extends JFrame{
    
    private User currentUser;
    private JTextArea reviewsArea;
    private JTextField ratingField;
    private JTextArea commentField;
    private JComboBox<String> filterComboBox;

    
    private List<Review> reviewsList = new ArrayList<>();

    public ReviewsFrame(User user) {
        this.currentUser = user;

        setTitle("Reviews");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Preload some dummy reviews
        preloadDummyReviews();

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 250));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(245, 248, 250));

        JLabel titleLabel = new JLabel("Write a Review");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(titleLabel);
        inputPanel.add(Box.createVerticalStrut(10));

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.setBackground(new Color(245, 248, 250));
        ratingPanel.add(new JLabel("Rating (1-5):"));
        ratingField = new JTextField(5);
        ratingPanel.add(ratingField);
        inputPanel.add(ratingPanel);
        inputPanel.add(Box.createVerticalStrut(10));

        JLabel commentLabel = new JLabel("Comment:");
        commentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentField = new JTextArea(4, 30);
        commentField.setLineWrap(true);
        commentField.setWrapStyleWord(true);
        JScrollPane commentScroll = new JScrollPane(commentField);
        commentScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(commentLabel);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(commentScroll);
        inputPanel.add(Box.createVerticalStrut(15));

        JButton submitBtn = new JButton("Submit Review");
        submitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitBtn.addActionListener(e -> submitReview());
        inputPanel.add(submitBtn);

        
        JPanel reviewsPanel = new JPanel(new BorderLayout());
        reviewsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        reviewsPanel.setBackground(new Color(245, 248, 250));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(245, 248, 250));
        filterPanel.add(new JLabel("Filter by:"));
        String[] filters = {"All Reviews", "My Reviews"};
        filterComboBox = new JComboBox<>(filters);
        filterComboBox.addActionListener(e -> displayReviews(getFilteredReviews()));
        filterPanel.add(filterComboBox);

        reviewsPanel.add(filterPanel, BorderLayout.NORTH);

        reviewsArea = new JTextArea();
        reviewsArea.setEditable(false);
        reviewsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reviewsArea.setLineWrap(true);
        reviewsArea.setWrapStyleWord(true);
        JScrollPane reviewsScroll = new JScrollPane(reviewsArea);
        reviewsScroll.setBorder(BorderFactory.createTitledBorder("All Reviews"));
        reviewsPanel.add(reviewsScroll, BorderLayout.CENTER);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(reviewsPanel, BorderLayout.CENTER);
        add(mainPanel);

        
        displayReviews(reviewsList);

        setVisible(true);
    }

    
    private void preloadDummyReviews() {
        reviewsList.add(new Review(1, currentUser.getUserID(), 5, "Great service!"));
        reviewsList.add(new Review(2, currentUser.getUserID() + 1, 4, "Comfortable ride."));
        reviewsList.add(new Review(3, currentUser.getUserID() + 2, 3, "Average experience."));
    }

    private List<Review> getFilteredReviews() {
        String filter = (String) filterComboBox.getSelectedItem();
        if ("My Reviews".equals(filter)) {
            List<Review> mine = new ArrayList<>();
            for (Review r : reviewsList) {
                if (r.getUserId() == currentUser.getUserID()) {
                    mine.add(r);
                }
            }
            return mine;
        }
        return new ArrayList<>(reviewsList);
    }

    private void submitReview() {
        String rt = ratingField.getText().trim();
        String cm = commentField.getText().trim();
        int rating;
        try {
            rating = Integer.parseInt(rt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter an integer rating (1-5)", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (rating < 1 || rating > 5 || cm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill rating (1-5) and comment.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Add new review
        reviewsList.add(new Review(reviewsList.size() + 1, currentUser.getUserID(), rating, cm));
        ratingField.setText("");
        commentField.setText("");
        displayReviews(getFilteredReviews());
    }

    private void displayReviews(List<Review> list) {
        StringBuilder sb = new StringBuilder();
        for (Review r : list) {
            sb.append("Review ID: ").append(r.getReviewId()).append(" | User: ").append(r.getUserId()).append("  ");
            sb.append("Rating: ");
            for (int i = 0; i < r.getRating(); i++) sb.append("★");
            for (int i = r.getRating(); i < 5; i++) sb.append("☆");
            sb.append("\n");
            sb.append("Comment: ").append(r.getComment()).append("\n");
            sb.append("------------------------------------------------------\n");
        }
        if (list.isEmpty()) sb.append("No reviews found.\n");
        reviewsArea.setText(sb.toString());
        reviewsArea.setCaretPosition(0);
    }

    
    private static class Review {
        private final int reviewId;
        private final int userId;
        private final int rating;
        private final String comment;
        public Review(int reviewId, int userId, int rating, String comment) {
            this.reviewId = reviewId;
            this.userId = userId;
            this.rating = rating;
            this.comment = comment;
        }
        public int getReviewId() { return reviewId; }
        public int getUserId()   { return userId;   }
        public int getRating()   { return rating;   }
        public String getComment(){ return comment; }
    }
}

