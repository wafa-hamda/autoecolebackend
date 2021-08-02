package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SeanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seance.class);
        Seance seance1 = new Seance();
        seance1.setId(1L);
        Seance seance2 = new Seance();
        seance2.setId(seance1.getId());
        assertThat(seance1).isEqualTo(seance2);
        seance2.setId(2L);
        assertThat(seance1).isNotEqualTo(seance2);
        seance1.setId(null);
        assertThat(seance1).isNotEqualTo(seance2);
    }
}
