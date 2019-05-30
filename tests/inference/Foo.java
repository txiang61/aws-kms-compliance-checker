// This is test data for the KMS compliance checker. This test checks that the type system is working
// correctly in basic cases.
import com.amazonaws.services.kms.model.DataKeySpec;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazon.checkerframework.compliance.kms.inference.qual.*;

class Foo {
    void goodString() {
        GenerateDataKeyRequest request = new GenerateDataKeyRequest().withKeySpec("AES_256");
        request.setKeySpec("AES_256");
        String test = "AES_128";
        goodEnum(test);
    }

    void goodEnum(String str) {
        GenerateDataKeyRequest request = new GenerateDataKeyRequest().withKeySpec(DataKeySpec.AES_256);
        request.setKeySpec(DataKeySpec.AES_256);
    }
}
