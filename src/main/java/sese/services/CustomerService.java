package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sese.entities.Customer;
import sese.repositories.CustomerRepository;
import sese.responses.CustomerResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    private LogService logService;

    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();

        /*if(CollectionUtils.isEmpty(customers)) {
            throw new SeseException(SeseError.NO_CUSTOMER);
        }*/

        return customers.stream().map(CustomerResponse::new).collect(Collectors.toList());
    }

    /**
     * id must be null
     * @param customer
     */
    public void addNewCustomer(Customer customer){
        if(customer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
        if(customer.getId() != null){
            throw new IllegalArgumentException("Customer id must be null");
        }

        Customer saved = customerRepository.save(customer);

        logService.logAction("Ein Kunde mit der Id '" + saved.getId() + "' wurde erstellt.");
    }
}
