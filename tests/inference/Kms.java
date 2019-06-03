import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.kms.model.GenerateDataKeyResult;
import javax.crypto.spec.SecretKeySpec;
import com.amazon.checkerframework.compliance.kms.inference.qual.*;

class Kms {
    private final Integer kmsBits = 256;
    private final int kmsBitss = 128;
    private final int random = 1;
    private final String algorithm = "AES";
    private final String test = "random";
    private final String test2 = "AES_256";
    private final String test3 = "AES_128";
    private final String here = "_";
    private final String value = "256";
    private final String aes_ = "AES_";

    String getKeySpec() {
        String keySpec = "AES" + "_" + 256;
        setKeySpec(keySpec);
        return keySpec;
    }

    String getKeySpec2() {
        String keySpec = algorithm + "_" + kmsBits;
        setKeySpec(keySpec);
        //String keySpec = here + algorithm;
        //String keySpec = algorithm + here;
        //String keySpec = test + kmsBitss;
        return keySpec;
    }

    @IntVal256
    Integer getKeyBits() {
        return kmsBits;
    }

    void setKeySpec(@AES_256 String spec) { }
}
