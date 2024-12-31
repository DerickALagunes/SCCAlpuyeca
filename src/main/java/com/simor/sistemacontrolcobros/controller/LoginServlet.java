package com.simor.sistemacontrolcobros.controller;

import com.simor.sistemacontrolcobros.model.Mensaje;
import com.simor.sistemacontrolcobros.model.Operacion;
import com.simor.sistemacontrolcobros.model.Usuario;
import com.simor.sistemacontrolcobros.model.dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass  = req.getParameter("pass");

        UsuarioDao dao = new UsuarioDao();
        Usuario usr = (Usuario) dao.findOne(email, pass);

        if(usr.getId_usuario()!=0){ //Que si existe un usuario en la BD
            req.getSession().setAttribute("sesion",usr);
            //Definir el tipo de sesión de acuerdo al tipo del usuario
            req.getSession().setAttribute("tipoSesion", usr.getTipo_usuario());

            List<Operacion> operaciones = new ArrayList<>();
            switch (usr.getTipo_usuario()){
                case "admin":
                case "user":
                    operaciones.add(
                            new Operacion(
                                    "REGISTRO DE INFORMACÍÓN",
                                    "registroInfo.jsp",
                                    "assets/img/registro_informacion.png",
                                    ""
                            ));
                    operaciones.add(
                            new Operacion(
                                    "REGISTRO DE INFORMACÍÓN CON EXCEL",
                                    "registroMasivo.jsp",
                                    "assets/img/registro_excel.png",
                                    ""
                            ));
                    operaciones.add(
                            new Operacion(
                                    "ADMINISTRAR INFORMACÍÓN",
                                    "transacciones.jsp",
                                    "assets/img/transacciones.png",
                                    ""
                            ));
                    operaciones.add(
                            new Operacion(
                                    "DATOS DEL PEDIDO",
                                    "pedidos.jsp",
                                    "assets/img/pedidos.png",
                                    ""
                            ));
                    operaciones.add(
                            new Operacion(
                                    "CONTROL DE COSTOS",
                                    "costos.jsp",
                                    "assets/img/costos.png",
                                    ""
                            ));
                    break;
                default:
                    break;
            }
            req.getSession().setAttribute("operaciones", operaciones);
        }else{
            req.getSession().setAttribute("mensaje",new Mensaje(
                    new ArrayList<String>(
                            Arrays.asList(
                                    "La combinación de usuario y contraseña no aparece en la base de datos",
                                    "intenta de nuevo o ponte en contacto con soporte técnico"
                            )
                    )
            ));
        }
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("sesion");
        req.getSession().removeAttribute("mensaje");
        resp.sendRedirect("index.jsp");
    }
}
