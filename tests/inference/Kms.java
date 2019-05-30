import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import javax.crypto.spec.SecretKeySpec;
import com.amazon.checkerframework.compliance.kms.inference.qual.*;

class Kms {
    private final @IntVal256 int kmsBits = 256;
    private final @AES String algorithm = "AES";

    String getKeySpec() {
        @AES_256 String keySpec = algorithm + "_" + kmsBits;
        return keySpec;
    }
}
