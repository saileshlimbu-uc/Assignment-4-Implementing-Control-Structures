import random

class EmployeeScheduler:
    def __init__(self):
        self.schedule = {day: {'morning': [], 'afternoon': [], 'evening': []} for day in ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']}
        self.employees = {}
    
    def add_employee(self, name, preferences):
        self.employees[name] = {'preferences': preferences, 'assigned_days': 0}
    
    def assign_shifts(self):
        for day in self.schedule:
            available_employees = list(self.employees.keys())
            random.shuffle(available_employees)
            for shift in self.schedule[day]:
                assigned = []
                for emp in available_employees:
                    if self.employees[emp]['assigned_days'] < 5 and emp not in assigned:
                        preferred_shifts = self.employees[emp]['preferences']
                        if shift in preferred_shifts and emp not in self.schedule[day]['morning'] + self.schedule[day]['afternoon'] + self.schedule[day]['evening']:
                            self.schedule[day][shift].append(emp)
                            self.employees[emp]['assigned_days'] += 1
                            assigned.append(emp)
                        if len(self.schedule[day][shift]) >= 2:
                            break
                while len(self.schedule[day][shift]) < 2:
                    extra_emp = random.choice([e for e in available_employees if self.employees[e]['assigned_days'] < 5])
                    self.schedule[day][shift].append(extra_emp)
                    self.employees[extra_emp]['assigned_days'] += 1
    
    def display_schedule(self):
        for day, shifts in self.schedule.items():
            print(f"{day}:")
            for shift, employees in shifts.items():
                print(f"  {shift.capitalize()}: {', '.join(employees)}")
            print()

# Example Usage
scheduler = EmployeeScheduler()
scheduler.add_employee("Alice", ["morning", "afternoon"])
scheduler.add_employee("Bob", ["afternoon", "evening"])
scheduler.add_employee("Charlie", ["morning", "evening"])
scheduler.add_employee("David", ["morning"])
scheduler.add_employee("Eve", ["evening"])

scheduler.assign_shifts()
scheduler.display_schedule()
