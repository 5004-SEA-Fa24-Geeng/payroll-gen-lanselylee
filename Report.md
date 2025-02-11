# Report for Payroll Generator

This report helps you demonstrate your understanding of the concepts. You should write this report after you have completed the project. 

## Technical Questions

1. What does CSV stand for? 

CSV stands for Comma-Separated Values. It is a plain-text file format used to store tabular data, where each line represents a row, and values within a row are separated by commas.
   

2. Why would you declare `List<IEmployee>` instead of `ArrayList<HourlyEmployee>`?
  Declaring List<IEmployee> instead of ArrayList<HourlyEmployee> makes my code more flexible, maintainable, and scalable. It allows me to store different employee types, swap list implementations, and follow best practices in object-oriented programming.

3. When you have one class referencing another object, such as storing that object as one of the attributes of the first class - what type of relationship is that called (between has-a and is-a)?
has-a

4. Can you provide an example of a has-a relationship in your code (if one exists)?
@Override
public IPayStub runPayroll(double hoursWorked) {
    ...
    return new PayStub(this, netPay.doubleValue(), tax.doubleValue());
}

5. Can you provide an example of an is-a relationship in your code (if one exists)?

public class HourlyEmployee implements IEmployee {


6. What is the difference between an interface and an abstract class?
An interface is like a "contract" that says what a class must do, but it does not provide any actual code.An abstract class is a partially built class that can have some code, but also leaves some methods unfinished for child classes to complete.


7. What is the advantage of using an interface over an abstract class?
A class can implement multiple interfaces but can only extend one abstract class.This gives more flexibility in designing the program.nterfaces focus on what a class must do, while abstract classes can include partial implementations.Using interfaces makes it easier to change implementations without breaking the code. I can swap one implementation for another without affecting other parts of the program.Interfaces allow objects to interact without being tightly connected to specific implementations.This makes it easier to change or update parts of the system without rewriting everything.And it can be used across unrelated classes

8. Is the following code valid or not? `List<int> numbers = new ArrayList<int>();`, explain why or why not. If not, explain how you can fix it. 
Invail.Java generics only work with reference types (objects), not primitive types,int is a primitive type, so it cannot be used with generics (List<int> is invalid)Instead of int, Java provides a wrapper class Integer (List<Integer> is valid).Integer is an object, so it works with generics.sso I should use Integer instead of int:

9. Which class/method is described as the "driver" for your application? 
The "driver" class is PayrollGenerator.The "driver" method is public static void main(String[] args).


10. How do you create a temporary folder for JUnit Testing? 
Use the @TempDir annotation to create a temporary folder.

## Deeper Thinking 

Salary Inequality is a major issue in the United States. Even in STEM fields, women are often paid less for [entry level positions](https://www.gsb.stanford.edu/insights/whats-behind-pay-gap-stem-jobs). However, not paying equal salary can hurt representation in the field, and looking from a business perspective, can hurt the company's bottom line has diversity improves innovation and innovation drives profits. 

Having heard these facts, your employer would like data about their salaries to ensure that they are paying their employees fairly. While this is often done 'after pay' by employee surveys and feedback, they have the idea that maybe the payroll system can help them ensure that they are paying their employees fairly. They have given you free reign to explore this idea.

Think through the issue / making sure to cite any resources you use to help you better understand the topic. Then write a paragraph on what changes you would need to make to the system. For example, would there be any additional data points you would need to store in the employee file? Why? Consider what point in the payroll process you may want to look at the data, as different people could have different pretax benefits and highlight that. 

The answer to this is mostly open. We ask that you cite at least two sources to show your understanding of the issue. The TAs will also give feedback on your answer, though will be liberal in grading as long as you show a good faith effort to understand the issue and making an effort to think about how your design to could help meet your employer's goals of salary equity. 

To address salary inequality effectively, the payroll system should be enhanced to include additional data points beyond salary and tax information, such as gender, ethnicity, job role, years of experience, education level, and starting salary. 

These attributes would allow for a more comprehensive analysis of pay disparities across different demographic groups within the organization. Furthermore, implementing a feature to track historical salary changes and promotions would provide insights into pay progression over time, helping to identify patterns of inequity.

It is essential to analyze this data at key points in the payroll process, particularly during initial salary determinations, salary reviews, and annual payroll audits. Additionally, since different employees may have varying pre-tax benefits (e.g., retirement contributions, health plans), these should be considered separately when assessing take-home pay fairness as well.

