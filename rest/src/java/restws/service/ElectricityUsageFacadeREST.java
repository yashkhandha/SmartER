/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.ElectricityUsage;
import restws.Resident;
import restws.ResidentCredential;

/**
 *
 * @author yashkhandha
 */
@Stateless
@Path("restws.electricityusage")
public class ElectricityUsageFacadeREST extends AbstractFacade<ElectricityUsage> {

    @PersistenceContext(unitName = "SmartERPU")
    private EntityManager em;

    public ElectricityUsageFacadeREST() {
        super(ElectricityUsage.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ElectricityUsage entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, ElectricityUsage entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ElectricityUsage> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ElectricityUsage> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    //GET by usage id
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ElectricityUsage find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    //GET by resid
    @GET
    @Path("findByResid/{resid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByResid(@PathParam("resid") int resid){
        Query query = em.createNamedQuery("ElectricityUsage.findByResid");
        query.setParameter("resid",resid);
        return query.getResultList();
    }
    
    //GET by usagedate
    @GET
    @Path("findByUsagedate/{usagedate}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByUsagedate(@PathParam("usagedate") String usagedate) throws ParseException{
        Query query = em.createNamedQuery("ElectricityUsage.findByUsagedate");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(usagedate);
        query.setParameter("usagedate", date);
        return query.getResultList();
    }
    
    //GET by USAGEHOUR
    @GET
    @Path("findByUsagehour/{usagehour}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByUsagehour(@PathParam("usagehour") int usagehour){
        Query query = em.createNamedQuery("ElectricityUsage.findByUsagehour");
        query.setParameter("usagehour",usagehour);
        return query.getResultList();
    }
    
    //GET by fridgeusage
    @GET
    @Path("findByFridgeusage/{fridgeusage}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByFridgeusage(@PathParam("fridgeusage") float fridgeusage){
        Query query = em.createNamedQuery("ElectricityUsage.findByFridgeusage");
        query.setParameter("fridgeusage",fridgeusage);
        return query.getResultList();
    }
    
    //GET by airconditionerusage
    @GET
    @Path("findByAirconditionerusage/{airconditionerusage}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByAirconditionerusage(@PathParam("airconditionerusage") float airconditionerusage){
        Query query = em.createNamedQuery("ElectricityUsage.findByAirconditionerusage");
        query.setParameter("airconditionerusage",airconditionerusage);
        return query.getResultList();
    }
    
    //GET by washingmachineusage
    @GET
    @Path("findByWashingmachineusage/{washingmachineusage}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByWashingmachineusage(@PathParam("washingmachineusage") float washingmachineusage){
        Query query = em.createNamedQuery("ElectricityUsage.findByWashingmachineusage");
        query.setParameter("washingmachineusage",washingmachineusage);
        return query.getResultList();
    }
    
    //GET by temperature
    @GET
    @Path("findByTemperature/{temperature}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByTemperature(@PathParam("temperature") float temperature){
        Query query = em.createNamedQuery("ElectricityUsage.findByTemperature");
        query.setParameter("temperature",temperature);
        return query.getResultList();
    }
    
    //TASK 3.3 fridge usage less than particular value in suburb 316*
    @GET
    @Path("findFridgeusageWithPostcode/{fridgeusage}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findFridgeusageWithPostcode(@PathParam("fridgeusage") Float fridgeusage){
        Query query = em.createQuery("SELECT e FROM ElectricityUsage e WHERE e.fridgeusage <= :fridgeusage AND e.resid.postcode LIKE '316%'");
        query.setParameter("fridgeusage", fridgeusage);
        return query.getResultList();
    }
    
    //TASK 3.4  residents with Energy provider and usagehour between 14 and 20
    @GET
    @Path("findByEnergyproviderWithUsagehour/{energyprovider}/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ElectricityUsage> findByEnergyproviderWithUsagehour(@PathParam("energyprovider")String energyprovider,
            @PathParam("from") Integer from, @PathParam("to") Integer to){
        Query query = em.createNamedQuery("ElectricityUsage.findByEnergyproviderWithUsagehour");
        query.setParameter("energyprovider", energyprovider);
        query.setParameter("from", from);
        query.setParameter("to", to);
        //query.setMaxResults(0);
        return query.getResultList();
    }
    
    //TASK 4.1
    @GET
    @Path("findHourlyUsageOfAppliance/{resid}/{appliancename}/{usagedate}/{usagehour}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Object findHourlyUsageOfAppliance(@PathParam("resid") Integer resid,
            @PathParam("appliancename") String appliancename,
            @PathParam("usagedate") String date,
            @PathParam("usagehour") Integer usagehour) throws ParseException {
        
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date usagedate = df1.parse(date);
        
        if (appliancename.toUpperCase().startsWith("F")){
        List<Object[]> queryList = em.createQuery("SELECT e.fridgeusage FROM ElectricityUsage"
                + " AS e WHERE e.resid.resid = :resid AND e.usagehour = :usagehour AND e.usagedate = :usagedate",
                Object[].class).setParameter("usagehour", usagehour)
                .setParameter("usagedate", usagedate).setParameter("resid", resid).getResultList();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject usageObject = Json.createObjectBuilder().
                    add("fridgeusage", (String) row[0].toString()).build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
        }
        
        else if (appliancename.toUpperCase().startsWith("A")){
        List<Object[]> queryList = em.createQuery("SELECT e.airconditionerusage FROM ElectricityUsage"
                + " AS e WHERE e.resid.resid = :resid AND e.usagehour = :usagehour AND e.usagedate = :usagedate",
                Object[].class).setParameter("usagehour", usagehour)
                .setParameter("usagedate", usagedate).setParameter("resid", resid).getResultList();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject usageObject = Json.createObjectBuilder().
                    add("airconditionerusage", (String) row[0].toString()).build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
        }
        
        else if (appliancename.toUpperCase().startsWith("W")){
        List<Object[]> queryList = em.createQuery("SELECT e.washingmachineusage FROM ElectricityUsage"
                + " AS e WHERE e.resid.resid = :resid AND e.usagehour = :usagehour AND e.usagedate = :usagedate",
                Object[].class).setParameter("usagehour", usagehour)
                .setParameter("usagedate", usagedate).setParameter("resid", resid).getResultList();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject usageObject = Json.createObjectBuilder().
                    add("washingmachineusage", (String) row[0].toString()).build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
        }
        
        else return null;
    }
    
    //TASK 4.2
    @GET
    @Path("findHourlyusageOfAllAppliance/{resid}/{usagedate}/{usagehour}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findHourlyusageOfAllAppliance(@PathParam("resid") Integer resid,
            @PathParam("appliancename") String appliancename,
            @PathParam("usagedate") String date,
            @PathParam("usagehour") Integer usagehour) throws ParseException {
        
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date usagedate = df1.parse(date);
        
        List<Object[]> queryList = em.createQuery("SELECT e.fridgeusage,e.airconditionerusage, e.washingmachineusage FROM ElectricityUsage"
                + " AS e WHERE e.resid.resid = :resid AND e.usagehour = :usagehour AND e.usagedate = :usagedate",Object[].class).setParameter("resid", resid).setParameter("usagedate", usagedate).setParameter("usagehour", usagehour).getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject usageObject = Json.createObjectBuilder().
                    //add("resid", (String) row[0].toString())
                    add("fridgeusage", (String) row[0].toString())
                    .add("airconditionerusage", (String) row[1].toString())
                    .add("washingmachineusage", (String) row[2].toString()).build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    
    //TASK 4.3
    @GET
    @Path("findHourlyusageOfAllApplianceForAll/{usagedate}/{usagehour}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object findHourlyusageOfAllApplianceForAll(@PathParam("usagedate") String date,
            @PathParam("usagehour") Integer usagehour) throws ParseException{
        
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date usagedate = df1.parse(date);
        
        List<Object[]> queryList = em.createQuery("SELECT e.resid.resid,e.resid.address,e.resid.postcode,"
                + "SUM(e.fridgeusage+e.airconditionerusage+e.washingmachineusage) FROM ElectricityUsage as e "
                + "WHERE e.usagedate = :usagedate AND e.usagehour = :usagehour "
                + "GROUP BY e.resid.resid,e.resid.address,e.resid.postcode",Object[].class)
                .setParameter("usagedate", usagedate)
                .setParameter("usagehour", usagehour)
                .getResultList();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Object[] row : queryList){
            JsonObject usageObject = Json.createObjectBuilder()
                    .add("resid", (String)row[0].toString())
                    .add("address", (String)row[1])
                    .add("postcode", (String)row[2])
                    .add("USAGE",(String)row[3].toString())
                    .build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;    
    }
    
    //TASK 4.4
    @GET
    @Path("findMaxUsageForResident/{resid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object findMaxUsageForResident(@PathParam("resid") Integer resid){
        
        List<Object[]> queryList = em.createQuery("SELECT e.usagehour,e.usagedate,"
                + "(e.fridgeusage+e.airconditionerusage+e.washingmachineusage) as TOTAL_USAGE "
                + "FROM ElectricityUsage e WHERE "
                + "(e.fridgeusage+e.airconditionerusage+e.washingmachineusage) = "
                + "(SELECT MAX(e.fridgeusage+e.airconditionerusage+e.washingmachineusage)"
                + " FROM ElectricityUsage as e WHERE e.resid.resid = :resid)",Object[].class)
                .setParameter("resid", resid)
                .getResultList();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Object[] row : queryList){
            JsonObject usageObject = Json.createObjectBuilder()
                    .add("usage hour", (String)row[0].toString())
                    .add("usage date", (String)row[1].toString())
                    .add("TOTAL_USAGE", (String)row[2].toString())
                    .build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;    
    }
        
    //TASK 5.1
    @GET
    @Path("findDailyUsageForOneResident/{resid}/{usagedate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object findDailyUsageForOneResident(@PathParam("usagedate") String date,
            @PathParam("resid") Integer resid) throws ParseException{
        
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date usagedate = df1.parse(date);
        
        List<Object[]> queryList = em.createQuery("SELECT e.resid.resid,SUM(e.fridgeusage),"
                + "SUM(e.airconditionerusage),SUM(e.washingmachineusage) FROM ElectricityUsage as e "
                + "WHERE e.usagedate = :usagedate AND e.resid.resid = :resid GROUP BY e.resid.resid",Object[].class)
                .setParameter("usagedate", usagedate)
                .setParameter("resid", resid)
                .getResultList();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Object[] row : queryList){
            JsonObject usageObject = Json.createObjectBuilder()
                    .add("resid", (String)row[0].toString())
                    .add("fridge usage", (String)row[1].toString())
                    .add("airconditioner usage", (String)row[2].toString())
                    .add("washingmachine usage", (String)row[3].toString())
                    .build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;    
    }
        
    //TASK 5.2
    @GET
    @Path("findHourlyANDDailyUsage/{resid}/{usagedate}/{view}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object findHourlyANDDailyUsage(@PathParam("resid") Integer resid,
            @PathParam("usagedate")String date,
            @PathParam("view")String view) throws ParseException{
        
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date usagedate = df1.parse(date);
        
        if (view.toUpperCase().equals("HOURLY")){
        List<Object[]> queryList = em.createQuery("SELECT e.resid.resid,(e.fridgeusage+e.airconditionerusage+e.washingmachineusage),e.temperature,e.usagedate,e.usagehour FROM ElectricityUsage e WHERE e.resid.resid = :resid AND e.usagedate = :usagedate",Object[].class)
                .setParameter("resid", resid)
                .setParameter("usagedate",usagedate)
                .getResultList();

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Object[] row : queryList){
            JsonObject usageObject = Json.createObjectBuilder()
                    .add("Resident Id", (String)row[0].toString())
                    .add("Usage", (String)row[1].toString())
                    .add("Temperature", (String)row[2].toString())
                    .add("Date",(String)row[3].toString())
                    .add("Hour",(String)row[4].toString())
                    .build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
        }
        
        else if (view.toUpperCase().equals("DAILY")){
            List<Object[]> queryList = em.createQuery("SELECT e.resid.resid,"
                    + "SUM(e.fridgeusage+e.airconditionerusage+e.washingmachineusage),AVG(e.temperature) "
                    + "FROM ElectricityUsage e WHERE e.resid.resid = :resid AND e.usagedate = :usagedate "
                    + "GROUP BY e.resid.resid",Object[].class)
                .setParameter("resid", resid)
                .setParameter("usagedate",usagedate)
                .getResultList();
            
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for (Object[] row : queryList){
            JsonObject usageObject = Json.createObjectBuilder()
                    .add("Resident Id", (String)row[0].toString())
                    .add("Total Usage", (String)row[1].toString())
                    .add("Average Temperature", (String)row[2].toString())
                    .build();
            arrayBuilder.add(usageObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
        }
        return null;
    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
