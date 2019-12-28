package fr.eni.eniencheres.dal.jdbc;

import org.junit.Test;
import org.junit.Assert;

public class ConnectionProviderMockTest {

    @Test
    public void getConnectionWithInitialiseDatabase() throws Exception {
        Assert.assertNotNull("La connection ne doit pas etre null",ConnectionProvider.getConnection());
    }

}
