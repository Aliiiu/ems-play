import models.Employee;
import models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.jpa.JPAApi;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Query;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Singleton
public class AdminInitializer {
    private final Logger logger = LoggerFactory.getLogger("AdminInitializer");

    private final JPAApi jpaApi;

    @Inject
    public AdminInitializer(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
        createDefaultAdmin();
    }
    private void createDefaultAdmin() {
        jpaApi.withTransaction(em -> {
            // Check if the Admin already exists
            Query query = em.createQuery("SELECT a FROM Employee a WHERE a.email = :email");
            query.setParameter("email", "admin@encentral.com");
            logger.info(query.toString());
            List<Employee> admins = query.getResultList();

            if (admins.isEmpty()) {
                // Create the default Admin
                Employee admin = new Employee();
                admin.setEmail("admin@encentral.com");
                admin.setPassword("admin");
                admin.setRole(Role.fromString("admin"));

                em.merge(admin);
            }
        });
    }
}
