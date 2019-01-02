package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sese.entities.Customer;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.CustomerRepository;
import sese.requests.CustomerRequest;
import sese.requests.ReservationRequest;
import sese.responses.CustomerResponse;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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

        customerRepository.save(customer);
    }

    public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest)
    {
        Optional<Customer> customerOptional=customerRepository.findById(customerId);
        Customer customer;

        if(customerOptional.isPresent())
            customer=customerOptional.get();

        else
            throw new SeseException(SeseError.NO_CUSTOMER);

        customer.setName(customerRequest.getName());
        customer.setBirthday(customerRequest.getBirthday());
        customer.setGender(customerRequest.getGender());
        customer.setBillingAddress(customerRequest.getBillingAddress());
        customer.setCompanyName(customerRequest.getCompanyName());
        customer.setNote(customerRequest.getNote());
        customer.setDiscount(customerRequest.getDiscount());
        customer.setTelephoneNumber(customerRequest.getTelephoneNumber());
        customer.setEmail(customerRequest.getEmail());
        customer.setWeb(customerRequest.getWeb());
        customer.setFax(customerRequest.getFax());

        customerRepository.save(customer);
        return new CustomerResponse(customer);
    }
}
