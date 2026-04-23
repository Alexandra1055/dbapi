package org.example.db.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.model.Personaje;
import org.example.db.service.PersonajeServ;
import org.example.db.service.PersonajeServImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "personajeServlet", value = "/personajes")
public class PersonajeServlet extends HttpServlet {
    private PersonajeServ personajeServ;

    @Override
    public void init() {
        this.personajeServ = new PersonajeServImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            if(req.getParameter("id") == null){
                List<Personaje> personajes = personajeServ.findAll();
                resp.getWriter().println("Lista de personajes:");
                for (Personaje p : personajes) {
                    resp.getWriter().println(p);
                }
            }else{
                Long id = Long.parseLong(req.getParameter("id"));
                Personaje personaje = personajeServ.findById(id);
                if (personaje == null) {
                    resp.getWriter().println("Personaje no encontrado");
                    return;
                }
                resp.getWriter().println("Personaje encontrado:");
                resp.getWriter().println(personaje);
            }
        }catch (Exception e){
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String action = req.getParameter("action");
            if (action.equals("eliminar")) {
                eliminarPersonaje(req, resp);
            }else if(action.equals("importar")){
                importarPersonaje(req, resp);
            } else {
                resp.getWriter().println("Acción no válida");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void eliminarPersonaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String personajeID = req.getParameter("id");

        if (personajeID == null) {
            resp.getWriter().println("Falta el id");
            return;
        }

        long id = Long.parseLong(personajeID);
        personajeServ.deletePersonaje(id);
        resp.getWriter().println("Personaje eliminado");
    }

    private void importarPersonaje(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String personajeID = req.getParameter("id");
        if (personajeID != null) {
            Long apiId = Long.parseLong(personajeID);
            personajeServ.savePersonaje(apiId);
            resp.getWriter().println("Personaje importado y guardado correctamente");

        }else{
            resp.getWriter().println("Id del personaje en la API no se ha encontrado");
        }
    }
}

