package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PayementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payement.class);
        Payement payement1 = new Payement();
        payement1.setId(1L);
        Payement payement2 = new Payement();
        payement2.setId(payement1.getId());
        assertThat(payement1).isEqualTo(payement2);
        payement2.setId(2L);
        assertThat(payement1).isNotEqualTo(payement2);
        payement1.setId(null);
        assertThat(payement1).isNotEqualTo(payement2);
    }
}
