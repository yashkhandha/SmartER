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

/**
 *
 * @author yashkhandha
 */
@Stateless
@Path("restws.resident")
public class ResidentFacadeREST extends AbstractFacade<Resident> {

    @PersistenceContext(unitName = "SmartERPU")
    private EntityManager em;

    public ResidentFacadeREST() {
        super(Resident.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Resident entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Resident entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    //GET resident by id
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Resident find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    //GET all residents
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Resident> findAll() {
        return super.findAll();
    }

    //GET from to
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Resident> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    //GET count
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //GET by firstname
    @GET
    @Path("findByFirstname/{firstname}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByFirstname(@PathParam("firstname")String firstname){
        Query query = em.createNamedQuery("Resident.findByFirstname");
        query.setParameter("firstname", firstname);
        return query.getResultList();
    }
    
    //GET by surname
    @GET
    @Path("findBySurname/{surname}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findBySurname(@PathParam("surname")String surname){
        Query query = em.createNamedQuery("Resident.findBySurname");
        query.setParameter("surname", surname);
        return query.getResultList();
    }
    
    //GET by dateofbirth
    @GET
    @Path("findByDateofbirth/{dateofbirth}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByDateofbirth(@PathParam("dateofbirth") String dateofbirth) throws ParseException{
        Query query = em.createNamedQuery("Resident.findByDateofbirth");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(dateofbirth);
        query.setParameter("dateofbirth", date);
        return query.getResultList();
    }
    
    //GET by address
    @GET
    @Path("findByAddress/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByAddress(@PathParam("address")String address){
        Query query = em.createNamedQuery("Resident.findByAddress");
        query.setParameter("address", address);
        return query.getResultList();
    }
    
    //GET by postcode
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByPostcode(@PathParam("postcode")String postcode){
        Query query = em.createNamedQuery("Resident.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    //GET by email
    @GET
    @Path("findByEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByEmail(@PathParam("email")String email){
        Query query = em.createNamedQuery("Resident.findByEmail");
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    //GET by mobilenumber
    @GET
    @Path("findByMobilenumber/{mobilenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByMobilenumber(@PathParam("mobilenumber")String mobilenumber){
        Query query = em.createNamedQuery("Resident.findByMobilenumber");
        query.setParameter("mobilenumber", mobilenumber);
        return query.getResultList();
    }
    
    //GET by numberofresidents
    @GET
    @Path("findByNumberofresidents/{numberofresidents}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByNumberofresidents(@PathParam("numberofresidents")int numberofresidents){
        Query query = em.createNamedQuery("Resident.findByNumberofresidents");
        query.setParameter("numberofresidents", numberofresidents);
        return query.getResultList();
    }
    
    //GET by energyprovider
    @GET
    @Path("findByEnergyprovider/{energyprovider}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findByEnergyprovider(@PathParam("energyprovider")String energyprovider){
        Query query = em.createNamedQuery("Resident.findByEnergyprovider");
        query.setParameter("energyprovider", energyprovider);
        return query.getResultList();
    }
    
    //TASK 3.2 resident details with postcode AND numberofresidents
    @GET
    @Path("findResidentByPostcodeANDNumberofresidents/{postcode}/{numberofresidents}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resident> findResidentByPostcodeANDNumberofresidents(@PathParam("postcode") String postcode, 
            @PathParam("numberofresidents") int numberofresidents){
        Query query = em.createQuery("SELECT r from Resident r WHERE r.postcode = :postcode "
                + "AND r.numberofresidents = :numberofresidents",Resident.class);
        query.setParameter("postcode", postcode);
        query.setParameter("numberofresidents", numberofresidents);
        return query.getResultList();
    }
}
