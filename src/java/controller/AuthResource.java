/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.UserLogin;
import dto.UserSignupDto;
import helpers.HttpResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Admin;
import model.Patient;
import model.Pharmacist;
import model.Physician;
import model.Role;
import model.User;
import org.mindrot.bcrypt.BCrypt;
import repository.UserRepository;

/**
 * REST Web Service
 *
 * @author cyimana
 */
@Path("auth")
public class AuthResource {

    /**
     * Creates a new instance of AuthResource
     */
    public AuthResource() {
    }

    @POST
    @Path("signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserSignupDto user) {
        switch (user.role) {
            case PATIENT: {
                if(!validatePassword(user.password, 7)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                Patient patient = new Patient(user.email, hashPassword(user.password), user.firstName, user.lastName, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                patient.createUser(patient);
                return Response.status(Response.Status.CREATED).entity("User registered successfully please login.").build();
            }
            case PHARMACIST: {
                if(!validatePassword(user.password, 5)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                Pharmacist pharmacist = new Pharmacist(user.email, hashPassword(user.password), user.firstName, user.lastName, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                pharmacist.createUser(pharmacist);
                return Response.status(Response.Status.CREATED).entity("User registered successfully please login.").build();
            }
            case PHYSICIAN: {
                if(!validatePassword(user.password, 6)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                Physician physician = new Physician(user.email, hashPassword(user.password), user.firstName, user.lastName, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                physician.createUser(physician);
                return Response.status(Response.Status.CREATED).entity("User registered successfully please login.").build();
            }
            case ADMIN: {
                if(!validatePassword(user.password, 8)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                Admin admin = new Admin(user.email, hashPassword(user.password), user.firstName, user.lastName, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                admin.createUser(admin);
                return Response.status(Response.Status.CREATED).entity("User registered successfully please login.").build();
            }
            default: {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Role").build();
            }
        }
    }
    
    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userSignIn(UserLogin userLogin) {
        HttpResponse response = new HttpResponse();
        User existingUser = UserRepository.getUserByEmail(userLogin.email);
        if (existingUser == null || !BCrypt.checkpw(userLogin.password, existingUser.getPassword())) {
            response.setStatus(401);
            response.setMessage("invalid credentials");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).type(MediaType.APPLICATION_JSON).build();
        }
        response.setStatus(201);
        response.setMessage("success");
        String token = generateToken(userLogin.email, existingUser.getRole());
        response.setBody(token);
        return Response.status(Response.Status.CREATED).entity(response).build();

    }

    private String generateToken(String email, Role role) {

        try {
            String token = Jwts.builder()
                    .setId(email)
                    .setIssuer("faisal")
                    .setSubject("auth")
                    .claim("role", role)
                    .claim("username", email)
                    .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
                    .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
                    .signWith(
                            SignatureAlgorithm.HS256.HS256,
                            TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
                    )
                    .compact();

            return token;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }
    
    private boolean validatePassword(String password,int min){
        return password.length() == min;
    }
    
    private String hashPassword(String password ){
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    private boolean isUserExisting(String email){
        User isExisting = UserRepository.getUserByEmail(email);
        if(isExisting != null) return true;
        return false;
    }
}
