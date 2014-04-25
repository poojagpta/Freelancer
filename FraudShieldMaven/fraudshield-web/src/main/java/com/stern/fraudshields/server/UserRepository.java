package com.stern.fraudshields.server;

import java.util.Collection;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.stern.fraudshields.model.Customer;

public class UserRepository {
	
	 /**
     * @return Collection of Message
     */
    public Collection<Customer> getAll() {
        final Objectify service = getService();

        return(service.query(Customer.class).list());
    }

    /**
     * @param message
     */
    public void create(final Customer user) {
        final Objectify service = getService();

        service.put(user);
    }

    /**
     * @param id
     */
    public void deleteById(final Long id) {
        final Objectify service = getService();

        service.delete(Customer.class, id.longValue());
    }

    private Objectify getService() {
        return (ObjectifyService.begin());
    }

}
