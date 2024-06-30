var employees = [
    {"firstName":"John", "lastName":"Doe"}, 
    {"firstName":"Anna", "lastName":"Smith"},
    {"firstName":"Peter", "lastName":"Jones"}
];

var doc = new jsPDF();
employees.forEach(function(employee, i){
    doc.text(20, 10 + (i * 10), 
        "First Name: " + employee.firstName +
        "Last Name: " + employee.lastName);
});
doc.save('Test.pdf');