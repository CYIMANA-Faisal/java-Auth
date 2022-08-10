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
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
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
import javax.xml.ws.http.HTTPException;
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
    public Response registerUser(@Valid UserSignupDto user) {
        try{
           User newUser = null;
           if(user.role == null) throw new BadRequestException("Unrecognized role");
           switch (user.role) {
            case PATIENT: {
                if(!validatePassword(user.password, 7)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                newUser = new Patient(user.email, hashPassword(user.password), user.firstName, user.lastName, user.phoneNumber, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                newUser.create();
                break;
            }
            case PHARMACIST: {
                if(!validatePassword(user.password, 5)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                newUser = new Pharmacist(user.email, hashPassword(user.password), user.firstName, user.lastName, user.phoneNumber, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                newUser.create();
                break;
            }
            case PHYSICIAN: {
                if(!validatePassword(user.password, 6)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                newUser = new Physician(user.email, hashPassword(user.password), user.firstName, user.lastName, user.phoneNumber, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                newUser.create();
                break;
            }
            case ADMIN: {
                if(!validatePassword(user.password, 8)) return Response.status(Response.Status.BAD_REQUEST).entity("Weak password").build();
                
                newUser = new Admin(user.email, hashPassword(user.password), user.firstName, user.lastName, user.phoneNumber, user.age, user.gender);
                if(isUserExisting(user.email)) return Response.status(Response.Status.CONFLICT).entity("Email already exist").build();
                newUser.create();
                break;
            }
            default: {
                throw new BadRequestException("Unrecognized role");
            }
        }
          return Response.status(Response.Status.CREATED).entity("User registered successfully please login.").build(); 
        }
        catch (ClientErrorException e){
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
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
            response.setMessage("Invalid credentials");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).type(MediaType.APPLICATION_JSON).build();
        }
        response.setStatus(201);
        response.setMessage("success");
        String token = generateToken(userLogin.email, existingUser.getRole(), existingUser.getFirstName(), existingUser.getLastName());
        response.setBody(token);        
        response.setUser(existingUser);
        return Response.status(Response.Status.CREATED).entity(response).build();

    }

    private String generateToken(String email, Role role, String firstName, String lastName) {

        try {
            Claims claims = Jwts.claims().setSubject(email);
                claims.put("role", role);
                claims.put("email", email);
                claims.put("firstName", firstName);
                claims.put("lastName", lastName);
                Instant now = Instant.now();
                String jwtToken = Jwts.builder()
                .claim("role", role)
                .claim("email", email)
                .claim("firstName", firstName)
                .claim("lastName", lastName)
                .setSubject(email)
                .setId(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.HOURS)))
                .compact();

            return jwtToken;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }
    
    private boolean validatePassword(String password,int min){
        return password.length() >= min;
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
