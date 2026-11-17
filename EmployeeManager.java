import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A console application for managing employee details.
 * Compile with: javac EmployeeManager.java
 * Run with:     java EmployeeManager
 */
public class EmployeeManager {
    private final List<Employee> employees = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new EmployeeManager().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Employee Management ===");
            System.out.println("1. Add employee");
            System.out.println("2. Display all employees");
            System.out.println("3. Search employee by ID");
            System.out.println("4. Exit");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1–4.");
            }
        }
        scanner.close();
    }

    private void addEmployee() {
        int id = readInt("Employee ID: ");
        if (findEmployee(id) != null) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readNonEmptyLine("Name: ");
        String department = readNonEmptyLine("Department: ");
        double salary = readSalary("Salary: ");
        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.printf("\n%-12s %-24s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee employee : employees) {
            System.out.printf("%-12d %-24s %-20s %12.2f%n",
                    employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary());
        }
    }

    private void searchEmployee() {
        int id = readInt("Enter employee ID to search: ");
        Employee employee = findEmployee(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("\nEmployee found:");
        System.out.println("ID:         " + employee.getId());
        System.out.println("Name:       " + employee.getName());
        System.out.println("Department: " + employee.getDepartment());
        System.out.printf("Salary:     %.2f%n", employee.getSalary());
    }

    private Employee findEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readSalary(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful message for malformed and negative values.
            }
            System.out.println("Please enter a valid non-negative salary.");
        }
    }

    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private int getId() {
            return id;
        }

        private String getName() {
            return name;
        }

        private String getDepartment() {
            return department;
        }

        private double getSalary() {
            return salary;
        }
    }
}
