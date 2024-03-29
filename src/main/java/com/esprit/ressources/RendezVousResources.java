package com.esprit.ressources;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.esprit.busniss.LogementBusiness;
import com.esprit.busniss.RendezVousBusiness;
import com.esprit.entities.Logement;
import com.esprit.entities.RendezVous;




@Path("rendezvous")
public class RendezVousResources {

	private static RendezVousBusiness RB = new RendezVousBusiness();
	public static LogementBusiness logementMetier = new LogementBusiness();
	RendezVous r = new RendezVous(1, "31-10-2015", "15:30",
			logementMetier.getLogementsByReference(4), "55214078");


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListRDV() {
		//when i have multiple return types or multiple returns we use Response ase return type
		if (RB.getListeRendezVous().size() != 0)
			return Response.status(Status.OK).entity(RB.getListeRendezVous()).build();

		return Response.status(Status.NOT_FOUND).entity("La liste est vide").build();

	}
	@PUT
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ajoutRdv(RendezVous R){
		if(RB.addRendezVous(R)){
			return  Response.status(Status.CREATED).entity("add success").build();
		}
		return Response.status(Status.NOT_FOUND).entity(RB.getListeRendezVous()).build();
	}

	@GET
	@Path("interoperabilite/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getdetails(@PathParam(value="id") int id){
		RendezVous r = RB.getRendezVousById(id);
		return Response.status(Status.OK).entity(r).header("Access-Control-Allow-Origin","*").build();
	}
	@Path("delete/{id}")
	@DELETE
	public  Response rmRDV(@PathParam(value = "id") int id){
		if(RB.deleteRendezVous(id)){
			return Response.status(Status.FOUND).entity("rdv supprimé").build();
		}
		return Response.status(Status.NOT_FOUND).entity("echec not found").build();
	}
	@Path("update/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateRdv(@PathParam(value = "id") int id , RendezVous R){
		if(RB.updateRendezVous(id,R)){
			return Response.status(Status.OK).entity("rdv modifier").build();
		}
		return Response.status(Status.OK).entity("error update").build();
	}
}


