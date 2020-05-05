/*
 * Copyright (C) 2015-2019 UTN-FRRe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.edu.nbch.frre.dacs.jaas;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.edu.nbch.frre.dacs.dao.UsuarioDao;
import ar.com.edu.nbch.frre.dacs.dao.impl.UsuarioDaoImpl;
import ar.com.edu.nbch.frre.dacs.model.Usuario;

/**
 * @author Dr. Jorge E. Villaverde
 *
 */
@SessionScoped
@Named(value="homeBean")
public class HomeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final Logger logger = LoggerFactory.getLogger(HomeBean.class);

	// Constants --------------------------------------------------------------

	protected static final String LOGIN_PAGE = "login.jsf";

	protected static final String LOGIN_ERROR_PAGE = "index.html";

	public static final String JSF_REDIRECT = "?faces-redirect=true&amp;includeViewParams=true";

	public static final String JSF_REDIRECT_ESCAPED = "?faces-redirect=true&includeViewParams=true";

	// Properties -------------------------------------------------------------

	/**
	 * Nombre de usuario para iniciar sesi&oacute;n
	 */
	@NotNull
	private String username;

	/**
	 * Contrase&ntilde;a para iniciar sesi&oacute;n
	 */
	@NotNull
	private String password;

	/**
	 * <code>true</code> si se ha producido un error de autenticaci&oacute;n
	 */
	private boolean authenticationError;

	/**
	 * Roles del usuario
	 */
	private ArrayList<ApplicationRole> userRoles;

	/**
	 * Rol asignado
	 */
	private ApplicationRole activeRole;

	/**
	 * Logged in User
	 */
	private Usuario usuario;

	/**
	 * Usuario DAO
	 */
	private UsuarioDao usuarioDao = new UsuarioDaoImpl();

	// Dependencies -----------------------------------------------------------

	// Actions ----------------------------------------------------------------

	@PostConstruct
	public void init() {
		this.authenticationError = false;
		this.userRoles = new ArrayList<ApplicationRole>();
		this.activeRole = null;
	}

	@PreDestroy
	public void preDestroy() {

	}

	public String login() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		InetAddress ip = null;

		try {
			ip = InetAddress.getByName(getIpFromRequest(request));

			/*
			 * Si se encuentra logeado, realize el logout antes del login
			 */
			if (isAuthenticated()) {
				logout();
			}

			request.login(this.username, this.password);

			this.usuario = usuarioDao.findByUsername(username);
			if (this.usuario != null) {
				request.getSession().setAttribute(username, getUsuario());
			}

			if (logger.isInfoEnabled())
				logger.info(String.format("El usuario %s ha iniciado sesión desde la IP %s.", this.username,
						ip.getHostAddress()));

			this.authenticationError = false;

			/*
			 * Asignar los roles del usuario
			 */
			assignUserRoles();

			if (!this.userRoles.isEmpty()) {
				/*
				 * Tomar el primer rol y asignarlo como activo
				 */
				this.activeRole = this.userRoles.iterator().next();

				/*
				 * Delegar el home en el rol activo.
				 */
				return activeRole.getHome() + JSF_REDIRECT_ESCAPED;
			} else {
				if (logger.isInfoEnabled())
					logger.info(String.format("El usuario %s no posee roles.", this.username));
				request.getSession(false).invalidate();
				this.authenticationError = true;
			}
		} catch (ServletException e) {
			request.getSession(false).invalidate();
			this.authenticationError = true;
			logger.warn("Login or password incorrect: " + username);
		} catch (Exception e) {
			if (request != null)
				request.getSession(false).invalidate();
			this.authenticationError = true;
			logger.warn(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
		return LOGIN_ERROR_PAGE;
	}

	private static String getIpFromRequest(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	public boolean isAuthenticated() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		return (externalContext.getUserPrincipal() != null);
	}

	private void assignUserRoles() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ApplicationRole[] allowedRoles = getAllowedApplicationRoles();

		if (allowedRoles == null || allowedRoles.length == 0)
			return;

		for (ApplicationRole rol : allowedRoles) {
			logger.info("Buscando el rol: " + rol.getRoleName() + " para el usuario: " + username);
			if (externalContext.isUserInRole(rol.getRoleName())) {
				this.userRoles.add(rol);
			} else {
				logger.info("No posee el rol: " + rol.getRoleName() + " para el usuario: " + username);
			}
		}
	}

	private ApplicationRole[] getAllowedApplicationRoles() {
		return new ApplicationRole[] { ApplicationRole.USER, ApplicationRole.ADMIN };
	}

	public void logout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		try {
			if (request != null) {
				//request.getSession(false).invalidate();
				request.logout();
				this.authenticationError = false;
			}
			externalContext.redirect(externalContext.getRequestContextPath() + "/index.html");
		} catch (IOException e) {
			logger.error("Logout error: " + e.getMessage());
		} catch (ServletException e) {
			logger.error("Logout error: " + e.getMessage());
		}
	}

	// Getters/Setters --------------------------------------------------------

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<ApplicationRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(ArrayList<ApplicationRole> userRoles) {
		this.userRoles = userRoles;
	}

	public ApplicationRole getActiveRole() {
		return activeRole;
	}

	public void setActiveRole(ApplicationRole activeRole) {
		this.activeRole = activeRole;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAuthenticationError() {
		return authenticationError;
	}

	public void setAuthenticationError(boolean authenticationError) {
		this.authenticationError = authenticationError;
	}
	
	public String getNombreUsuario() {
		if(this.usuario == null)
			return "NO USER";
		return this.usuario.getUsername();
	}

	public String getHome() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		
		return request.getContextPath() + "/" + getActiveRole().getHome();
	}
	
	public String getApplicationVersion() {
		return "v1.0.1.RC";
	}
}
