package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FormationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formation.class);
        Formation formation1 = new Formation();
        formation1.setId(1L);
        Formation formation2 = new Formation();
        formation2.setId(formation1.getId());
        assertThat(formation1).isEqualTo(formation2);
        formation2.setId(2L);
        assertThat(formation1).isNotEqualTo(formation2);
        formation1.setId(null);
        assertThat(formation1).isNotEqualTo(formation2);
    }
}
