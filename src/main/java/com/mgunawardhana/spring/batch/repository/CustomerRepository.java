/**
 * Developed By: mGunawardhana
 * Date: 29/05/2024
 * Time: 14:09
 */
package com.mgunawardhana.spring.batch.repository;

import com.mgunawardhana.spring.batch.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
