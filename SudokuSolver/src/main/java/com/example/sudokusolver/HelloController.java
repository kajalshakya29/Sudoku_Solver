
package com.example.sudokusolver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Pos;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private GridPane gridPane;


    private TextField[][] fields = new TextField[9][9];
    private int[][] grid = new int[9][9];
    @FXML
    protected void initialize() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField field = new TextField(); // Placeholder text
                field.setPrefWidth(30);
                field.setPrefHeight(30);
                field.setAlignment(javafx.geometry.Pos.CENTER);

                // Clear on focus
                field.setOnMouseClicked(event -> {
                    if (field.getText().equals("Enter the number here")) {
                        field.setText("");
                    }
                });

                gridPane.add(field, j, i);
                fields[i][j] = field;
            }
        }
    }
    @FXML
    private void onClearButtonClick(ActionEvent event) {
        // Clear the text fields
        for (TextField[] row : fields) {
            for (TextField field : row) {
                field.clear();
            }
        }
    }

    @FXML
    protected void onSolveButtonClick() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = fields[i][j].getText();
                if (text.isEmpty()) {
                    grid[i][j] = 0;
                } else {
                    grid[i][j] = Integer.parseInt(text);
                }
            }
        }


        if (solveGrid(grid, 0, 0)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fields[i][j].setText(String.valueOf(grid[i][j]));
                }
            }
        } else {
            welcomeText.setText("No solution exists");
        }
    }
    private boolean solveGrid(int[][] grid, int row, int col) {
        if (row == 9 - 1 && col == 9)
            return true;

        if (col == 9) {
            row++;
            col = 0;
        }

        if (grid[row][col] != 0)
            return solveGrid(grid, row, col + 1);

        for (int num = 1; num <= 9; num++) {
            if (isValid(grid, row, col, num)) {
                grid[row][col] = num;
                if (solveGrid(grid, row, col + 1))
                    return true;
            }
            grid[row][col] = 0;
        }
        return false;
    }

    private boolean isValid(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num)
                return false;
        }

        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num)
                return false;
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == num)
                    return false;
            }
        }
        return true;
    }
}