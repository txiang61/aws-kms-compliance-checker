import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import javax.crypto.spec.SecretKeySpec;
import com.amazon.checkerframework.compliance.kms.inference.qual.*;

class Kms {
    private final Integer kmsBits = 256;
    private final String algorithm = "AES";

    String getKeySpec() {
        String keySpec = algorithm + "_" + kmsBits;
        return keySpec;
    }
}
