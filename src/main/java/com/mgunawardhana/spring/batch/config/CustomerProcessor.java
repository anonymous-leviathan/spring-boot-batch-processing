/**
 * Developed By: mGunawardhana
 * Date: 29/05/2024
 * Time: 19:02
 */
package com.mgunawardhana.spring.batch.config;

import com.mgunawardhana.spring.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        return customer;
    }
}
