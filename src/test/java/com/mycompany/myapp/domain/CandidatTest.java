package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CandidatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidat.class);
        Candidat candidat1 = new Candidat();
        candidat1.setId(1L);
        Candidat candidat2 = new Candidat();
        candidat2.setId(candidat1.getId());
        assertThat(candidat1).isEqualTo(candidat2);
        candidat2.setId(2L);
        assertThat(candidat1).isNotEqualTo(candidat2);
        candidat1.setId(null);
        assertThat(candidat1).isNotEqualTo(candidat2);
    }
}
