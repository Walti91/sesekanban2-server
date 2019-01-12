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

    public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest)
    {
        Optional<Customer> customerOptional=customerRepository.findById(customerId);
        Customer customer;

        if(customerOptional.isPresent())
            customer=customerOptional.get();

        else
            throw new SeseException(SeseError.NO_CUSTOMER);

        if(customerRequest.getName()!=null)
            customer.setName(customerRequest.getName());
        if(customerRequest.getBirthday()!=null)
            customer.setBirthday(customerRequest.getBirthday());
        if(customerRequest.getGender()!=null)
            customer.setGender(customerRequest.getGender());
        if(customerRequest.getBillingAddress()!=null)
            customer.setBillingAddress(customerRequest.getBillingAddress());
        if(customerRequest.getCompanyName()!=null)
            customer.setCompanyName(customerRequest.getCompanyName());
        if(customerRequest.getNote()!=null)
            customer.setNote(customerRequest.getNote());
        if(customerRequest.getDiscount()!=null)
            customer.setDiscount(customerRequest.getDiscount());
        if(customerRequest.getTelephoneNumber()!=null)
            customer.setTelephoneNumber(customerRequest.getTelephoneNumber());
        if(customerRequest.getEmail()!=null)
            customer.setEmail(customerRequest.getEmail());
        if(customerRequest.getWeb()!=null)
            customer.setWeb(customerRequest.getWeb());
        if(customerRequest.getFax()!=null)
            customer.setFax(customerRequest.getFax());

        customerRepository.save(customer);
        return new CustomerResponse(customer);
    }

    public CustomerResponse getCustomerById(Long customerId)
    {
        Optional<Customer> customerOptional=customerRepository.findById(customerId);
        Customer customer;

        if(customerOptional.isPresent())
            customer=customerOptional.get();

        else
            throw new SeseException(SeseError.NO_CUSTOMER);

        return new CustomerResponse(customer);


    }
}
