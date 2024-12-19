All Repository files to be in this folder

# Attempt to write the user access data into the PostgreSQL


# How to use JPA Query Creation from Method Name

Structure: [Operation]By[FieldName][Condition]
find By FirstName

Examples:
// Custom query to find all customers with a specific first name

// List<Customer> findByFirstName(String firstName);
   List<Customer> findByFirstNameIgnoreCase(String firstName);

// Find all customers with no interactions

List<Customer> findByInteractionsIsEmpty();

Documentation link:
https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html