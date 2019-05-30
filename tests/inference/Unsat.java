import com.amazon.checkerframework.compliance.kms.inference.qual.*;

public class Unsat {

    void inferTop(@AES_256 String p) {
        String s = p;
        @AES128 String x = s;
    }
}