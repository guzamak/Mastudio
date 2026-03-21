/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.components.dashboard;

import app.components.lib.*;
import java.awt.GridLayout;

/**
 *
 * @author poke
 */
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class BookingCalendar extends JPanel implements DateSelectionListener{

    private MaPanel calendarPanel;
    private MaPanel headerPanel;
    private MaLabel monthLabel;

    private int month;
    private int year;
    private LocalDate selectedDate;
    private DateSelectionListener listener;

    public void addDateSelectionListener(DateSelectionListener listener) {
        this.listener = listener;
    }

    public BookingCalendar() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); 
        LocalDate now = LocalDate.now();
        month = now.getMonthValue();
        year = now.getYear();

        headerPanel = new MaPanel();
        headerPanel.setBackground(Color.WHITE); 
        headerPanel.setLayout(new FlowLayout());

        MaButton prevButton = new MaButton();
        prevButton.setText("<");
        prevButton.setButtonColor(Macolor.trans);
        prevButton.setBorderColor(Macolor.trans);
        prevButton.setTextColor(Macolor.magreen);
        MaButton nextButton = new MaButton();
        nextButton.setText(">");
        nextButton.setButtonColor(Macolor.trans);
        nextButton.setBorderColor(Macolor.trans);
        nextButton.setTextColor(Macolor.magreen);

        monthLabel = new MaLabel();
        monthLabel.setTextColor(Macolor.magreen);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);
        headerPanel.setBorderColor(Macolor.trans);

        add(headerPanel, BorderLayout.NORTH);

        calendarPanel = new MaPanel();
        calendarPanel.setBorderColor(Macolor.trans);
        calendarPanel.setLayout(new GridBagLayout());
        calendarPanel.setBackground(Color.white);

        add(calendarPanel, BorderLayout.CENTER);

        prevButton.addActionListener(e -> {
            month--;
            if (month < 1) {
                month = 12;
                year--;
            }
            updateCalendar();
        });

        nextButton.addActionListener(e -> {
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
            updateCalendar();
        });

        updateCalendar();
    }

    private void updateCalendar() {

        calendarPanel.removeAll();

        calendarPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        calendarPanel.setPadding(10);
        gbc.insets = new Insets(2,2,2,2);

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int col = 0; col < 7; col++) {
            gbc.gridx = col;
            gbc.gridy = 0;
            gbc.weightx = 1.0;              // equal width for all columns
            gbc.weighty = 0.1;              // small height for first row
            gbc.fill = GridBagConstraints.BOTH;
            JLabel lbl = new MaLabel();
            lbl.setText(days[col]);
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 12f));
            calendarPanel.add(lbl, gbc);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstDay = LocalDate.of(year, month, 1);
        int startDay = firstDay.getDayOfWeek().getValue() % 7;

        int row = 1;
        int col = 0;

// empty cells before first day
        for (int i = 0; i < startDay; i++) {
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.weightx = 1.0;
            gbc.weighty = 0.18;             // bigger height for date rows
            gbc.fill = GridBagConstraints.BOTH;
            calendarPanel.add(new JLabel(""), gbc);
            col++;
        }

// add day buttons
        for (int day = 1; day <= daysInMonth; day++) {
            int currentDay = day; 
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.weightx = 1.0;
            gbc.weighty = 0.18;
            gbc.fill = GridBagConstraints.BOTH;
            MaButton dateBtn = new MaButton();
            dateBtn.setText(String.valueOf(day));
            // Highlight if this is the selected date
            if (selectedDate != null
                    && selectedDate.getYear() == year
                    && selectedDate.getMonthValue() == month
                    && selectedDate.getDayOfMonth() == day) {
                dateBtn.setBorderColor(Macolor.magreen);
                dateBtn.setTextColor(Macolor.magreen);
                dateBtn.setBackground(Macolor.seablue); 
            } else {
                dateBtn.setBackground(Macolor.trans); 
                dateBtn.setTextColor(Macolor.mablue);
                dateBtn.setBorderColor(Macolor.mablue);
            }

            dateBtn.addActionListener(e -> {
                // Set the selected date
                selectedDate = LocalDate.of(year, month, currentDay);
//                System.out.println("Selected date: " + selectedDate);
                updateCalendar(); // refresh to update highlighting
                if (listener != null) {
                    listener.dateSelected(selectedDate);
                }
            });
            calendarPanel.add(dateBtn, gbc);

            col++;
            if (col == 7) {  // move to next row
                col = 0;
                row++;
            }
        }

// fill remaining empty cells until 6x7
        while (row <= 5) {
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.weightx = 1.0;
            gbc.weighty = 0.18;
            gbc.fill = GridBagConstraints.BOTH;
            calendarPanel.add(new JLabel(""), gbc);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
        monthLabel.setText(yearMonth.getMonth() + " " + year);

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }


    public LocalDate getSelectedDate() {
        return selectedDate;
    }

//    custom event
    @Override
    public void dateSelected(LocalDate selectedDate) {
         listener.dateSelected(selectedDate);
    }


}
