package ar.edu.utn.frre.dacs.loan.scoring;

public class ClientNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long clientId;

	// Constructors -----------------------------------------------------------
	
	public ClientNotFoundException(Long clientId) {
		super();
		this.clientId = clientId;
	}

	// Getters/Setters --------------------------------------------------------

	public Long getClientId() {
		return clientId;
	}

}
