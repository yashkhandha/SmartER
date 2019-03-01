/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.Resident;
import restws.ResidentCredential;

/**
 *
 * @author yashkhandha
 */
@Stateless
@Path("restws.residentcredential")
public class ResidentCredentialFacadeREST extends AbstractFacade<ResidentCredential> {

    @PersistenceContext(unitName = "SmartERPU")
    private EntityManager em;

    public ResidentCredentialFacadeREST() {
        super(ResidentCredential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ResidentCredential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, ResidentCredential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    //GET all
    @GET
    @Path("findAllResidents")
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<ResidentCredential> findAll() {
        return super.findAll();
    }

    //GET from to
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ResidentCredential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    //GET count
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    //GET by username
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ResidentCredential find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    //GET by passwordhash
    @GET
    @Path("findByPasswordhash/{passwordhash}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResidentCredential> findByPasswordHash(@PathParam("passwordhash") String passwordhash){
        Query query = em.createNamedQuery("ResidentCredential.findByPasswordhash");
        query.setParameter("passwordhash",passwordhash);
        return query.getResultList();
    }
    
    //GET by registrationdate
    @GET
    @Path("findByRegistrationdate/{registrationdate}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResidentCredential> findByRegistrationdate(@PathParam("registrationdate") String registrationdate) throws ParseException{
        Query query = em.createNamedQuery("ResidentCredential.findByRegistrationdate");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(registrationdate);
        query.setParameter("registrationdate", date);
        return query.getResultList();
    }
    
    //GET by resid
    @GET
    @Path("findByResid/{resid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResidentCredential> findByResid(@PathParam("resid") int resid){
        Query query = em.createNamedQuery("ResidentCredential.findByResid");
        query.setParameter("resid",resid);
        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
