package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class VignetteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vignette.class);
        Vignette vignette1 = new Vignette();
        vignette1.setId(1L);
        Vignette vignette2 = new Vignette();
        vignette2.setId(vignette1.getId());
        assertThat(vignette1).isEqualTo(vignette2);
        vignette2.setId(2L);
        assertThat(vignette1).isNotEqualTo(vignette2);
        vignette1.setId(null);
        assertThat(vignette1).isNotEqualTo(vignette2);
    }
}
