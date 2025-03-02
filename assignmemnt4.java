import java.util.*;

class EmployeeScheduler {
    private Map<String, Map<String, List<String>>> schedule;
    private Map<String, Employee> employees;
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[] SHIFTS = {"morning", "afternoon", "evening"};

    public EmployeeScheduler() {
        schedule = new HashMap<>();
        employees = new HashMap<>();
        for (String day : DAYS) {
            schedule.put(day, new HashMap<>());
            for (String shift : SHIFTS) {
                schedule.get(day).put(shift, new ArrayList<>());
            }
        }
    }

    public void addEmployee(String name, List<String> preferences) {
        employees.put(name, new Employee(name, preferences));
    }

    public void assignShifts() {
        Random rand = new Random();
        for (String day : DAYS) {
            List<String> availableEmployees = new ArrayList<>(employees.keySet());
            Collections.shuffle(availableEmployees);
            for (String shift : SHIFTS) {
                List<String> assigned = new ArrayList<>();
                for (String empName : availableEmployees) {
                    Employee emp = employees.get(empName);
                    if (emp.getAssignedDays() < 5 && emp.getPreferences().contains(shift) && !assigned.contains(empName)) {
                        schedule.get(day).get(shift).add(empName);
                        emp.incrementAssignedDays();
                        assigned.add(empName);
                        if (schedule.get(day).get(shift).size() >= 2) break;
                    }
                }
                while (schedule.get(day).get(shift).size() < 2) {
                    String extraEmp = availableEmployees.get(rand.nextInt(availableEmployees.size()));
                    if (employees.get(extraEmp).getAssignedDays() < 5) {
                        schedule.get(day).get(shift).add(extraEmp);
                        employees.get(extraEmp).incrementAssignedDays();
                    }
                }
            }
        }
    }

    public void displaySchedule() {
        for (String day : DAYS) {
            System.out.println(day + ":");
            for (String shift : SHIFTS) {
                System.out.println("  " + shift.substring(0, 1).toUpperCase() + shift.substring(1) + ": " + String.join(", ", schedule.get(day).get(shift)));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EmployeeScheduler scheduler = new EmployeeScheduler();
        scheduler.addEmployee("Alice", Arrays.asList("morning", "afternoon"));
        scheduler.addEmployee("Bob", Arrays.asList("afternoon", "evening"));
        scheduler.addEmployee("Charlie", Arrays.asList("morning", "evening"));
        scheduler.addEmployee("David", Arrays.asList("morning"));
        scheduler.addEmployee("Eve", Arrays.asList("evening"));

        scheduler.assignShifts();
        scheduler.displaySchedule();
    }
}

class Employee {
    private String name;
    private List<String> preferences;
    private int assignedDays;

    public Employee(String name, List<String> preferences) {
        this.name = name;
        this.preferences = preferences;
        this.assignedDays = 0;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public int getAssignedDays() {
        return assignedDays;
    }

    public void incrementAssignedDays() {
        assignedDays++;
    }
}
