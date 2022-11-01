package com.despair.corp.monokouma.mafuckingreufinal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainApplicationTest {

    @Test
    public void nominal_case() {
        // When
        MainApplication application = new MainApplication();

        // Then
        assertEquals(
            MainApplication.getInstance(),
            application
        );
    }

}