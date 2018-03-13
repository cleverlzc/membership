package com.github.phillipkruger.membership.service;

import com.github.phillipkruger.membership.Membership;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;

@Log
@Stateless
public class MembershipService {
    
    @PersistenceContext(name="com.github.phillipkruger.membership")
    private EntityManager em;
    
    @GraphQLMutation(name = "membership")
    public Membership createMembership(@GraphQLArgument(name = "membership") @NotNull Membership membership){
        membership = em.merge(membership);
        log.log(Level.INFO, "Created membership [{0}]", membership);
        return membership;    
    }
    
    @GraphQLQuery(name = "memberships")
    public List<Membership> getAllMemberships() {
        return em.createNamedQuery(Membership.QUERY_FIND_ALL, Membership.class).getResultList();
    }

    @GraphQLQuery(name = "membership")
    public Membership getMembership(@GraphQLArgument(name = "membershipId") int id) {
        return em.find(Membership.class,id);
    }
}
