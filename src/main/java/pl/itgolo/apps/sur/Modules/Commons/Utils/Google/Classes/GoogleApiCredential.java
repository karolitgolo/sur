package pl.itgolo.apps.sur.Modules.Commons.Utils.Google.Classes;

import lombok.Data;

/**
 * The type Google api credential.
 */
@Data
public class GoogleApiCredential {

    private String idClient;

    private String secretKey;

    /**
     * Instantiates a new Google api credential.
     *
     * @param idClient  the id client
     * @param secretKey the secret key
     */
    public GoogleApiCredential(String idClient, String secretKey) {
        this.idClient = idClient;
        this.secretKey = secretKey;
    }
}
