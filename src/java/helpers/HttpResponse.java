/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author cyimana
 */
public class HttpResponse<T> {
    private Integer status;
    private String message;
    String body;
    T user;

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "HttpResponse{" + "status=" + status + ", message=" + message + ", body=" + body + '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    
}
