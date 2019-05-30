import com.amazon.checkerframework.compliance.kms.inference.qual.*;

public class Unsat {

    void inferTop(@AES256 String p) {
        String s = p;
        @AES String x = s;
    }
}